package com.tzel.movieflix.domain.auth

abstract class AuthException : Exception()

class InvalidRequestToken : AuthException()

class InvalidWebToken : AuthException()

class InvalidAccessToken : AuthException()

class NoSessionIdFound : AuthException()

class SessionIdIsNotValid : AuthException()

class UserIdNotFound : AuthException()

class NoUserIdFound : AuthException()

class PermissionDeniedForUserId : AuthException()

class UnknownError : AuthException()