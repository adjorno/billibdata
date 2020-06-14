package com.m14n.billib.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BBJournalMetadata(@SerialName("name") var name: String? = null,
                             @SerialName("url") var url: String? = null,
                             @SerialName("base_rss") var baseRss: String? = null,
                             @SerialName("charts") var charts: List<BBChartMetadata>? = null)
