package com.m14n.billib.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BBTrack(
    var rank: Int,
    var title: String,
    var artist: String,
    @SerialName("position")
    var positionInfo: BBPositionInfo? = null
) {

    override fun toString(): String {
        return "$rank. $artist - $title ($positionInfo)"
    }
}
