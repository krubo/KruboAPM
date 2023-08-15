package com.krubo.apmplugin.data

data class TimeCost(
    val pkgList: MutableList<String> = mutableListOf(),
    val methodClassList: MutableList<String> = mutableListOf(),
    val blackClassList: MutableList<String> = mutableListOf(),
    val blackMethodClassList: MutableList<String> = mutableListOf()
)

