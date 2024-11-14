package com.tzel.movieflix.framework.configuration

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.tzel.movieflix.data.configuration.model.LocalConfiguration
import com.tzel.movieflix.data.configuration.model.LocalLanguage

@Dao
interface ConfigurationDao {

    @Transaction
    suspend fun safeSaveLanguage(lang: LocalLanguage) {
        insertOrIgnoreConfiguration()
        saveLanguage(code = lang.code, name = lang.name, nameEn = lang.englishName)
    }

    @Query("INSERT OR IGNORE INTO configuration (id) VALUES (1)")
    fun insertOrIgnoreConfiguration()

    @Query("UPDATE configuration SET lang_code = :code, lang_name = :name, lang_name_en = :nameEn WHERE id = 1")
    suspend fun saveLanguage(code: String?, name: String?, nameEn: String?)

    @Query("SELECT * FROM configuration LIMIT 1")
    suspend fun getConfiguration(): LocalConfiguration?
}