package com.m14n.billib.data

import com.m14n.billib.data.model.BBChart
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.stringify
import java.io.File

@UseExperimental(UnstableDefault::class, ImplicitReflectionSerializer::class)
fun main() {
    JsonConfiguration.Stable.copy(prettyPrint = true)
    val chart = Json.parse(BBChart.serializer(), File(BB.DATA_ROOT, "hot-100").listFiles().last().readText())
    print(Json.stringify(chart))
    chart.tracks?.forEach { println(it.toString()) }
}
