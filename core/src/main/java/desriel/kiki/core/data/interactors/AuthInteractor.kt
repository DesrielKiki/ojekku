package desriel.kiki.core.data.interactors

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.domain.model.User
import desriel.kiki.core.domain.repository.AuthRepository
import desriel.kiki.core.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.Flow


class AuthInteractor constructor(
  private val authRepository: AuthRepository
): AuthUseCase {
  override suspend fun login(email: String, password: String): Flow<Resource<User>> {
    return authRepository.login(email, password)
  }

  override suspend fun register(user: User): Flow<Resource<User>> {
    return authRepository.register(user)
  }
}