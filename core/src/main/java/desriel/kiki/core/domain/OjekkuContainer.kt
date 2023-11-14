package desriel.kiki.core.domain

import desriel.kiki.core.domain.repository.AuthRepository
import desriel.kiki.core.domain.repository.LanguageRepository
import desriel.kiki.core.domain.repository.PlacesRepository
import desriel.kiki.core.domain.repository.UserRepository
import desriel.kiki.core.domain.usecase.AuthUseCase
import desriel.kiki.core.domain.usecase.LanguageUseCase
import desriel.kiki.core.domain.usecase.PlacesUseCase
import desriel.kiki.core.domain.usecase.UserUseCase

interface OjekkuContainer {
    val authRepository: AuthRepository
    val userRepository: UserRepository
    val placesRepository: PlacesRepository
    val languageRepository: LanguageRepository

    val authUseCase: AuthUseCase
    val userUseCase: UserUseCase
    val placesUseCase: PlacesUseCase
    val languageUseCase: LanguageUseCase
}