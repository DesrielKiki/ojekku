package desriel.kiki.core.data.interactors

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.domain.repository.UserRepository
import desriel.kiki.core.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.Flow


class UserInteractor constructor(
  private val userRepository: UserRepository
): UserUseCase {
  override suspend fun isUserLoggedIn(): Flow<Resource<Boolean>> {
    return userRepository.isUserLoggedIn()
  }

  override suspend fun storeEmail(email: String) {
    userRepository.storeEmail(email)
  }

  override suspend fun logout() {
    userRepository.logout()
  }
}