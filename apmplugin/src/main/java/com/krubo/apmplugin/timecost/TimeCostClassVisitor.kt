package com.krubo.apmplugin.timecost

import com.krubo.apmplugin.KApmPlugin
import com.krubo.apmplugin.base.BaseClassVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor

class TimeCostClassVisitor(classVisitor: ClassVisitor) : BaseClassVisitor(classVisitor) {

    override fun checkMethod(className: String, methodName: String?): Boolean {
        println("checkMethod  $className")
        if (KApmPlugin.timeCost.blackMethodClassList.contains("$className/$methodName")){
            println("checkMethod  $className  false  1")
            return false
        }
        if(KApmPlugin.hasInPkgList(className)){
            println("checkMethod  $className  true  1")
            return true
        }
        if (KApmPlugin.timeCost.methodClassList.contains("$className/$methodName")){
            println("checkMethod  $className  true  2")
            return true
        }
        return false
    }

    override fun visitMethod(
        className: String,
        methodName: String?,
        visitor: MethodVisitor?,
        access: Int,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        return TimeCostMethodVisitor(className, methodName, visitor, access, descriptor)
    }

    override fun visitField(
        className: String,
        fieldName: String?,
        visitor: FieldVisitor?,
        access: Int,
        descriptor: String?,
        signature: String?,
        value: Any?
    ): FieldVisitor? {
        return null
    }
}