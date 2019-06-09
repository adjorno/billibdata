package com.m14n.billib.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BBChartMetadata(@SerialName("name") var name: String? = null,
                           @SerialName("folder") var folder: String? = null,
                           @SerialName("size") var size: Int = 0,
                           @SerialName("start_date") var startDate: String? = null,
                           @SerialName("prefix") var prefix: String? = null)
