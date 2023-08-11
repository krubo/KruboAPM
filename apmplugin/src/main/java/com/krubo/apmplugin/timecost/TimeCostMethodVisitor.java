package com.krubo.apmplugin.timecost;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class TimeCostMethodVisitor extends AdviceAdapter {

    protected TimeCostMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM5, methodVisitor, access, name, descriptor);
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
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        //即将从这个方法出去
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        //表示方法扫码完毕
    }
}
