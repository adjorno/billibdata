package com.m14n.billib.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BBPositionInfo(@SerialName("Last Week") var lastWeek: String? = null,
                          @SerialName("Peak Position") var peekPosition: Int = 0,
                          @SerialName("Wks on Chart") var wksOnChart: Int = 0) {

    override fun toString(): String {
        return "Last = $lastWeek, Peak = $peekPosition, Charts = $wksOnChart"
    }
}
