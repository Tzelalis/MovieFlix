package com.tzel.movieflix.usecase.auth

import com.tzel.movieflix.domain.auth.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(): String? {
        return try {
            repo.getUserId()
        } catch (e: Exception) {
            Timber.tag(GetUserIdUseCase::class.java.simpleName)
            null
        }
    }
}