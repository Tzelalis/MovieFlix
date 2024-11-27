package com.tzel.movieflix.usecase.auth

import com.tzel.movieflix.domain.auth.AuthRepository
import com.tzel.movieflix.usecase.core.UseCase
import timber.log.Timber
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(private val repo: AuthRepository) : UseCase {
    suspend operator fun invoke(token: String): Boolean {
        return try {
            repo.createAccessToken(token)
            true
        } catch (e: Exception) {
            Timber.tag(CreateSessionUseCase::class.java.simpleName).e(e)
            false
        }
    }
}