package com.krubo.apmplugin.timecost;

import com.krubo.apmplugin.KApmConstants;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class TimeCostMethodVisitor extends AdviceAdapter {

    private String className = "";
    private int startTime = -1;
    private String methodName = "";

    protected TimeCostMethodVisitor(String className, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM5, methodVisitor, access, name, descriptor);
        this.className = className;
        methodName = name;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        //表示 ASM 开始扫描这个方法
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        //进入这个方法
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        startTime = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, startTime);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        //即将从这个方法出去
        int duration = newLocal(Type.LONG_TYPE);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LLOAD, startTime);
        mv.visitInsn(LSUB);
        mv.visitVarInsn(LSTORE, duration);
        mv.visitLdcInsn(KApmConstants.TAG);
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("ClassName: " + className + "; Method: " + methodName + "; duration: ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(LLOAD, duration);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//        mv.visitInsn(POP);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        //表示方法扫码完毕
    }
}
