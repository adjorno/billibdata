package com.m14n.billib.data

import com.m14n.billib.data.model.BBJournalMetadata
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.json
import kotlinx.serialization.json.jsonArray
import java.io.File
import java.io.FileWriter


@UnstableDefault
fun main() {
    val theOutFile = File("metadata_billboard.json")
    FileWriter(BB.DATA_ROOT + File.separator + theOutFile).use {
        it.write(
            Json(
                JsonConfiguration(
                    prettyPrint = true,
                    encodeDefaults = false
                )
            ).stringify(
                BBJournalMetadata.serializer(),
                billboardJournal
            )
        )
    }
}

@UnstableDefault
val billboardJournal = Json.fromJson(BBJournalMetadata.serializer(),
    json {
        "name" to "Billboard"
        "url" to "https://www.billboard.com/charts/"
        "base_rss" to "https://www.billboard.com/rss/charts/"
        "charts" to jsonArray {
            +json {
                "name" to "Hot 100"
                "folder" to "hot-100"
                "size" to 100
                "start_date" to "1958-08-09"
                "prefix" to "hot100"
            }
            +json {
                "name" to "Country"
                "folder" to "country-songs"
                "size" to 50
                "start_date" to "1962-01-06"
                "prefix" to "country"
            }
            +json {
                "name" to "Hip-Hop"
                "folder" to "r-b-hip-hop-songs"
                "size" to 50
                "start_date" to "1962-01-06"
                "prefix" to "hiphop"
            }
            +json {
                "name" to "Club"
                "folder" to "dance-club-play-songs"
                "size" to 50
                "start_date" to "1985-01-05"
                "end_date" to "2020-03-28"
                "prefix" to "club"
            }
            +json {
                "name" to "Latin"
                "folder" to "latin-songs"
                "size" to 50
                "start_date" to "1986-09-06"
                "prefix" to "latin"
            }
            +json {
                "name" to "Alternative"
                "folder" to "alternative-airplay"
                "size" to 40
                "start_date" to "1988-09-10"
                "prefix" to "alternative"
            }
            +json {
                "name" to "Rap"
                "folder" to "rap-song"
                "size" to 25
                "start_date" to "1989-03-11"
                "prefix" to "rap"
            }
            +json {
                "name" to "Pop"
                "folder" to "pop-songs"
                "size" to 40
                "start_date" to "1992-10-03"
                "prefix" to "pop"
            }
            +json {
                "name" to "Rhytmic"
                "folder" to "rhythmic-40"
                "size" to 40
                "start_date" to "1992-10-03"
                "prefix" to "rhytmic"
            }
            +json {
                "name" to "Christian"
                "folder" to "christian-songs"
                "size" to 50
                "start_date" to "2003-06-21"
                "prefix" to "christian"
            }
            +json {
                "name" to "Gospel"
                "folder" to "gospel-songs"
                "size" to 25
                "start_date" to "2005-03-19"
                "prefix" to "gospel"
            }
            +json {
                "name" to "Canada"
                "folder" to "canadian-hot-100"
                "size" to 100
                "start_date" to "2007-03-31"
                "prefix" to "canada"
            }
            +json {
                "name" to "France"
                "folder" to "france-digital-song-sales"
                "size" to 10
                "start_date" to "2007-09-08"
                "prefix" to "france"
            }
            +json {
                "name" to "Rock"
                "folder" to "rock-songs"
                "size" to 50
                "start_date" to "2009-06-20"
                "prefix" to "rock"
            }
            +json {
                "name" to "UK"
                "folder" to "official-uk-albums"
                "size" to 20
                "start_date" to "2011-01-29"
                "prefix" to "uk"
            }
            +json {
                "name" to "Japan"
                "folder" to "japan-hot-100"
                "size" to 100
                "start_date" to "2011-04-09"
                "prefix" to "japan"
            }
            +json {
                "name" to "Germany"
                "folder" to "germany-songs"
                "size" to 10
                "start_date" to "2011-05-21"
                "prefix" to "germany-songs"
            }
            +json {
                "name" to "Youtube"
                "folder" to "youtube"
                "size" to 25
                "start_date" to "2011-08-13"
                "end_date" to "2018-12-22"
                "prefix" to "youtube"
            }
            +json {
                "name" to "R&B"
                "folder" to "r-and-b-songs"
                "size" to 25
                "start_date" to "2012-10-20"
                "prefix" to "rnb"
            }
            +json {
                "name" to "Electronic"
                "folder" to "dance-electronic-songs"
                "size" to 50
                "start_date" to "2013-01-26"
                "prefix" to "electro"
            }
            +json {
                "name" to "Soundtracks"
                "folder" to "soundtracks"
                "size" to 25
                "start_date" to "2001-06-30"
                "prefix" to "soundtracks"
            }
            +json {
                "name" to "Euro"
                "folder" to "euro-digital-song-sales"
                "size" to 20
                "start_date" to "2007-12-01"
                "prefix" to "euro"
            }
        }
    }
)
