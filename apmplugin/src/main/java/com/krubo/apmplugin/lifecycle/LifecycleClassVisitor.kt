package com.krubo.apmplugin.lifecycle

import com.krubo.apmplugin.base.BaseClassVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor

class LifecycleClassVisitor(classVisitor: ClassVisitor) : BaseClassVisitor(classVisitor) {

    override fun checkMethod(className: String, methodName: String?): Boolean {
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
        return LifecycleMethodVisitor(className, methodName, visitor, access, descriptor)
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