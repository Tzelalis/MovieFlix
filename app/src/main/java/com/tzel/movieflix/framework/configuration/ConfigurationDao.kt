package com.tzel.movieflix.framework.configuration

import androidx.room.Dao
import androidx.room.Query
import com.tzel.movieflix.data.configuration.model.LocalConfiguration

@Dao
interface ConfigurationDao {

    @Query("INSERT OR IGNORE INTO configuration (id) VALUES ($CONFIGURATION_ID)")
    suspend fun insertOrIgnoreConfiguration()

    @Query("UPDATE configuration SET lang_code = :code, lang_name = :name, lang_name_en = :nameEn WHERE id = $CONFIGURATION_ID")
    suspend fun saveLanguage(code: String?, name: String?, nameEn: String?)

    @Query("SELECT * FROM configuration LIMIT 1")
    suspend fun getConfiguration(): LocalConfiguration?

    @Query("UPDATE configuration SET account_access_token = :accessToken, account_account_id = :accoundId WHERE id = $CONFIGURATION_ID")
    suspend fun saveAccessTokenAndAccountId(accessToken: String, accoundId: String)

    companion object {
        private const val CONFIGURATION_ID = 1
    }
}