package com.tzel.movieflix.usecase.auth

import com.tzel.movieflix.domain.auth.AuthException
import com.tzel.movieflix.domain.auth.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthExceptionsUseCase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke(): StateFlow<AuthException?> {
        return repo.getAuthExceptions()
    }
}