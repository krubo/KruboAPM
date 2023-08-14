package com.krubo.apmplugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class KApmPlugin : Plugin<Project> {

    companion object {
        val pkgList = mutableListOf<String>()
        val methodClassList = mutableListOf<String>()

        fun hasInPkgList(className:String):Boolean{
            pkgList.forEach {
                if (className.startsWith(it)) {
                    return true
                }
            }
            return false
        }
    }

    override fun apply(target: Project) {
        loadConfig(target)
        val app = target.extensions.getByType(AndroidComponentsExtension::class.java)
        app.onVariants { variant ->
            variant.instrumentation.transformClassesWith(
                KApmTransform::class.java, InstrumentationScope.ALL
            ) {}
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
    }

    private fun loadConfig(target: Project) {
        val config = target.extensions.create(KApmConstants.KAPM_CONFIG, KApmConfig::class.java)
        target.afterEvaluate {
            if (pkgList.isEmpty()) {
                pkgList.addAll(config.pkg.split(";"))
            }
            if (methodClassList.isEmpty()) {
                methodClassList.addAll(config.methodClass.split(";"))
            }
        }
    }
}