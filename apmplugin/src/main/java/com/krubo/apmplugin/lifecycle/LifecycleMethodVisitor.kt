package com.krubo.apmplugin.lifecycle

import com.krubo.apmplugin.base.BaseMethodVisitor
import org.objectweb.asm.MethodVisitor

class LifecycleMethodVisitor(
    className: String,
    methodName: String?,
    methodVisitor: MethodVisitor?,
    access: Int,
    descriptor: String?
) : BaseMethodVisitor(className, methodName, methodVisitor, access, descriptor) {
    override fun visitCode(className: String, methodName: String?) {
    }

    override fun onMethodEnter(className: String, methodName: String?) {
    }

    override fun onMethodExit(className: String, methodName: String?, opcode: Int) {
    }

    override fun visitEnd(className: String, methodName: String?) {
    }
}
