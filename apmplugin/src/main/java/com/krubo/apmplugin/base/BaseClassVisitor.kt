package com.krubo.apmplugin.base

import com.krubo.apmplugin.KApmPlugin
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

abstract class BaseClassVisitor(classVisitor: ClassVisitor) :
    ClassVisitor(Opcodes.ASM5, classVisitor) {

    private var className = ""

    override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        interfaces: Array<String?>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name.replace("/", ".")
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor? {
        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (!KApmPlugin.hasInPkgList(className)
            && !KApmPlugin.methodClassList.contains("$className/$name")) {
            return visitor
        }
        if ("<clinit>" == name || "<init>" == name) {
            return visitor
        }
        return visitMethod(className, name, visitor, access, descriptor, signature, exceptions)
    }

    override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?
    ): FieldVisitor? {
        val visitor = super.visitField(access, name, descriptor, signature, value)
        return visitField(className, name, visitor, access, descriptor, signature, value)
    }

    abstract fun visitMethod(
        className: String, methodName: String?, visitor: MethodVisitor?,
        access: Int, descriptor: String?, signature: String?, exceptions: Array<out String>?
    ): MethodVisitor?

    abstract fun visitField(
        className: String, fieldName: String?, visitor: FieldVisitor?,
        access: Int, descriptor: String?, signature: String?, value: Any?
    ): FieldVisitor?
}