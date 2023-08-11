package com.krubo.apmplugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class KApmPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val app = target.extensions.getByType(AndroidComponentsExtension::class.java)
        app.onVariants {
            it.instrumentation.transformClassesWith(KApmTransform::class.java, InstrumentationScope.ALL){}
            it.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
    }

}