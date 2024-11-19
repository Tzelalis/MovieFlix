package com.tzel.movieflix.framework.auth

import androidx.room.Dao
import androidx.room.Query
import com.tzel.movieflix.data.configuration.model.LocalConfiguration

@Dao
interface AuthDao {
    @Query("SELECT * FROM configuration LIMIT 1")
    suspend fun getConfiguration(): LocalConfiguration?

    @Query("UPDATE configuration SET account_access_token = :accessToken, account_account_id = :accoundId WHERE id = $CONFIGURATION_ID")
    suspend fun saveAccessTokenAndAccountId(accessToken: String, accoundId: String)

    @Query("UPDATE configuration SET account_session_id = :sessionId WHERE id = $CONFIGURATION_ID")
    suspend fun saveSessionId(sessionId: String)

    companion object {
        private const val CONFIGURATION_ID = 1
    }
}
