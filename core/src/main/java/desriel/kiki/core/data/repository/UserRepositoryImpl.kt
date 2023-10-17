package desriel.kiki.core.data.repository

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.datastore.OjekkuDataStore
import desriel.kiki.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class UserRepositoryImpl constructor(
  private val dataStore: OjekkuDataStore
): UserRepository {
  override suspend fun isUserLoggedIn(): Flow<Resource<Boolean>> {
    return flow<Resource<Boolean>> {
      dataStore.email.collect {
        emit(Resource.Success(it.isNotEmpty()))
      }
    }.catch { e->
      emit(Resource.Error(-1, e.message ?: "Something wrong."))
    }.flowOn(Dispatchers.IO)
  }

  override suspend fun storeEmail(email: String) {
    dataStore.storeData(OjekkuDataStore.EMAIL, email)
  }

  override suspend fun logout() {
    dataStore.clear()
  }
}