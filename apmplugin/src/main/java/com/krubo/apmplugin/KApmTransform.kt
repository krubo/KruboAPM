package com.krubo.apmplugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import com.krubo.apmplugin.lifecycle.LifecycleClassVisitor
import com.krubo.apmplugin.timecost.TimeCostClassVisitor
import org.objectweb.asm.ClassVisitor

abstract class KApmTransform : AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        //指定真正的ASM转换器
        //责任链模式
        return TimeCostClassVisitor(
            LifecycleClassVisitor(
                nextClassVisitor
            )
        )
    }

    //通过classData中的当前类的信息，用来过滤哪些类需要执行字节码转换，这里支持通过类名，包名，注解，接口，父类等属性来组合判断
    override fun isInstrumentable(classData: ClassData): Boolean {
        //指定包名执行
        val className = classData.className
        if (className.isEmpty()) {
            return false
        }
        if (KApmPlugin.hasInBlackClassList(className)) {
            println("isInstrumentable   $className  false")
            return false
        }
        if (KApmPlugin.hasInPkgList(className)) {
            return true
        }
        KApmPlugin.timeCost.methodClassList.forEach {
            if (className == it.split("/")[0]) {
                return true
            }
        }
        return false
    }
}