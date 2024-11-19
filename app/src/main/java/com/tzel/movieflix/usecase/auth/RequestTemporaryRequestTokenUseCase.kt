package com.tzel.movieflix.usecase.auth

import com.tzel.movieflix.domain.auth.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class RequestTemporaryRequestTokenUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(): String? {
        return try {
            repo.getTemporaryToken()
        } catch (e: Exception) {
            Timber.tag(RequestTemporaryRequestTokenUseCase::class.java.simpleName).e(e)
            return null
        }
    }
}