package com.tzel.movieflix.data.configuration.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuration")
data class LocalConfiguration(
    @PrimaryKey val id: Int = 1,
    @Embedded(prefix = "lang_") val language: LocalLanguage,
    @Embedded(prefix = "account_") val user: LocalAccount,
)

data class LocalAccount(
    @ColumnInfo(name = "access_token") val accessToken: String? = null,
    @ColumnInfo(name = "account_id") val accountId: String? = null,
)


data class LocalLanguage(
    val code: String?,
    val name: String?,
    @ColumnInfo("name_en") val englishName: String?,
)