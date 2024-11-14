package com.tzel.movieflix.usecase.configuration

import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.domain.configuration.entity.FailToCreateNewTemporaryRequestToken
import com.tzel.movieflix.domain.configuration.entity.TemporaryRequestToken
import timber.log.Timber
import javax.inject.Inject

class RequestTemporaryRequestTokenUseCase @Inject constructor(private val repo: ConfigurationRepository) {
    suspend operator fun invoke(): TemporaryRequestToken? {
        return try {
            repo.getTemporaryToken()
        } catch (e: FailToCreateNewTemporaryRequestToken) {
            Timber.tag(RequestTemporaryRequestTokenUseCase::class.java.simpleName).e(e)
            return null
        } catch (e: Exception) {
            Timber.tag(RequestTemporaryRequestTokenUseCase::class.java.simpleName).e(e)
            return null
        }
    }
}