package com.m14n.billib.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BBChart(@SerialName("name") var name: String?,
                   @SerialName("chart_date") var date: String?,
                   @SerialName("tracks") var tracks: List<BBTrack>?) {

    override fun toString(): String {
        return "$name $date"
    }
}
