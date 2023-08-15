package com.krubo.apmplugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.krubo.apmplugin.data.Config
import com.krubo.apmplugin.data.TimeCost
import org.gradle.api.Plugin
import org.gradle.api.Project

class KApmPlugin : Plugin<Project> {

    companion object {
        val timeCost = TimeCost()

        fun hasInPkgList(className: String): Boolean {
            timeCost.pkgList.forEach {
                if (className.startsWith(it)) {
                    return true
                }
            }
            return false
        }

        fun hasInBlackClassList(className: String):Boolean{
            timeCost.blackClassList.forEach {
                if (className == it) {
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
        val config = target.extensions.create(Constants.KAPM_CONFIG, Config::class.java)
        target.afterEvaluate {
            if (timeCost.pkgList.isEmpty()) {
                timeCost.pkgList.addAll(config.timecost_pkg)
            }
            if (timeCost.methodClassList.isEmpty()) {
                timeCost.methodClassList.addAll(config.timecost_methodClass)
            }
            if (timeCost.blackClassList.isEmpty()) {
                timeCost.blackClassList.addAll(config.timecost_blackClass)
            }
            if (timeCost.blackMethodClassList.isEmpty()) {
                timeCost.blackMethodClassList.addAll(config.timecost_blackMethodClass)
            }
            println(timeCost)
        }
    }
}