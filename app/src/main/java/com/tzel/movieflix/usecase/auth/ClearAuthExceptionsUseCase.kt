package com.tzel.movieflix.usecase.auth

import com.tzel.movieflix.domain.auth.AuthRepository
import com.tzel.movieflix.usecase.core.UseCase
import javax.inject.Inject

class ClearAuthExceptionsUseCase @Inject constructor(private val repo: AuthRepository) : UseCase {
    suspend operator fun invoke() {
        return repo.clearAuthExceptions()
    }
}