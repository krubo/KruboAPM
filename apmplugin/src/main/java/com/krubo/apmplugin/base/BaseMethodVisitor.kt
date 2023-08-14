package com.krubo.apmplugin.base

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

abstract class BaseMethodVisitor(
    private val className: String,
    private val methodName: String?,
    methodVisitor: MethodVisitor?,
    access: Int,
    descriptor: String?
) : AdviceAdapter(Opcodes.ASM5, methodVisitor, access, methodName, descriptor) {

    override fun visitCode() {
        super.visitCode()
        //表示 ASM 开始扫描这个方法
        visitCode(className, methodName)
    }

    override fun onMethodEnter() {
        super.onMethodEnter()
        //进入这个方法
        onMethodEnter(className, methodName)
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        //即将从这个方法出去
        onMethodExit(className, methodName, opcode)
    }

    override fun visitEnd() {
        super.visitEnd()
        //表示方法扫码完毕
        visitEnd(className, methodName)
    }

    abstract fun visitCode(className: String, methodName: String?)

    abstract fun onMethodEnter(className: String, methodName: String?)

    abstract fun onMethodExit(className: String, methodName: String?, opcode: Int)

    abstract fun visitEnd(className: String, methodName: String?)
}