package com.m14n.billib.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BBTrack(@SerialName("rank") var rank: Int,
                   @SerialName("title") var title: String,
                   @SerialName("artist") var artist: String? = null,
                   @SerialName("cover") var coverUrl: String? = null,
                   @SerialName("spotify") var spotifyUrl: String? = null,
                   @SerialName("position") var positionInfo: BBPositionInfo? = null) {

    override fun toString(): String {
        return "$rank. $artist - $title ($positionInfo)"
    }
}
