package com.m14n.billib.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BBChart(
    var name: String,

    @SerialName("chart_date")
    var date: String,

    var tracks: List<BBTrack>
) {
    override fun toString(): String {
        return "$name $date"
    }
}
