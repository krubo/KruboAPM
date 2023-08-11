package com.krubo.apmplugin.lifecycle

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class ActivityLifecycleMethodVisitor(
    visitor: MethodVisitor, access: Int,
    name: String?,
    descriptor: String?
) : AdviceAdapter(Opcodes.ASM5, visitor, access, name, descriptor) {

    override fun visitCode() {
        super.visitCode()
        //表示 ASM 开始扫描这个方法
    }

    override fun onMethodEnter() {
        super.onMethodEnter()
        //进入这个方法
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        //即将从这个方法出去
    }

    override fun visitEnd() {
        super.visitEnd()
        //表示方法扫码完毕
    }
}