package com.tzel.movieflix.usecase.auth

import com.tzel.movieflix.domain.auth.AuthRepository
import javax.inject.Inject

class ClearAuthExceptionsUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke() {
        return repo.clearAuthExceptions()
    }
}