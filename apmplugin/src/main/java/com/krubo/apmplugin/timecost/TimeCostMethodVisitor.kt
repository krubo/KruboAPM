package com.krubo.apmplugin.timecost

import com.krubo.apmplugin.KApmConstants
import com.krubo.apmplugin.base.BaseMethodVisitor

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type

class TimeCostMethodVisitor(
    className: String,
    methodName: String?,
    methodVisitor: MethodVisitor?,
    access: Int,
    descriptor: String?
) : BaseMethodVisitor(className, methodName, methodVisitor, access, descriptor)  {

    private var startTime = -1

    override fun visitCode(className: String, methodName: String?) {
    }

    override fun onMethodEnter(className: String, methodName: String?) {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        startTime = newLocal(Type.LONG_TYPE)
        mv.visitVarInsn(LSTORE, startTime)
    }

    override fun onMethodExit(className: String, methodName: String?, opcode: Int) {
        val duration = newLocal(Type.LONG_TYPE)
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitVarInsn(LLOAD, startTime)
        mv.visitInsn(LSUB)
        mv.visitVarInsn(LSTORE, duration)
        mv.visitLdcInsn(KApmConstants.TAG)
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
        mv.visitLdcInsn("ClassName: $className; Method: $methodName; duration: ")
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "append",
            "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
            false
        )
        mv.visitVarInsn(LLOAD, duration)
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "append",
            "(J)Ljava/lang/StringBuilder;",
            false
        )
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "toString",
            "()Ljava/lang/String;",
            false
        )
        mv.visitMethodInsn(
            INVOKESTATIC,
            "android/util/Log",
            "e",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            false
        )
//        mv.visitInsn(POP)
    }

    override fun visitEnd(className: String, methodName: String?) {
    }
}
