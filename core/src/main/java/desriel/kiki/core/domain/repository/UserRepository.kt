package desriel.kiki.core.domain.repository

import desriel.kiki.core.data.source.Resource
import kotlinx.coroutines.flow.Flow


interface UserRepository {
  suspend fun isUserLoggedIn(): Flow<Resource<Boolean>>
  suspend fun storeEmail(email: String)
  suspend fun logout()
}