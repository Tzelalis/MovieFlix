package com.tzel.movieflix.data.configuration.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuration")
data class LocalConfiguration(
    @PrimaryKey val id: Int = 1,
    @Embedded(prefix = "temp_request_") val temporaryToken: LocalTemporaryRequestToken = LocalTemporaryRequestToken(),
    @ColumnInfo(name = "session_id") val sessionId: String? = null,
    @Embedded(prefix = "lang_") val language: LocalLanguage,
)

data class LocalTemporaryRequestToken(
    @ColumnInfo(name = "token") val requestToken: String? = null,
    @ColumnInfo(name = "expiration") val expiresAt: String? = null,
)


data class LocalLanguage(
    val code: String?,
    val name: String?,
    @ColumnInfo("name_en") val englishName: String?,
)