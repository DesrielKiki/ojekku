package desriel.kiki.core.data.repository

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.datastore.OjekkuDataStore
import desriel.kiki.core.data.source.local.room.dao.UserDao
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


class UserRepositoryImpl constructor(
    private val dataStore: OjekkuDataStore,
    private val dao: UserDao
) : UserRepository {
    override suspend fun isUserLoggedIn(): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {
            dataStore.email.collect {
                emit(Resource.Success(it.isNotEmpty()))
            }
        }.catch { e ->
            emit(Resource.Error(-1, e.message ?: "Something wrong."))
        }.flowOn(Dispatchers.IO)
    }

    override val userName: Flow<String> = dataStore.userName
    override suspend fun logout() {
        dataStore.clear()
    }

    /**
     * store function
     */
    override suspend fun storeFullName(fullName: String) {
        dataStore.storeData(OjekkuDataStore.FULLNAME, fullName)
    }

    override suspend fun storeEmail(email: String) {
        dataStore.storeData(OjekkuDataStore.EMAIL, email)
    }

    override suspend fun storeUserName(userName: String) {
        dataStore.storeData(OjekkuDataStore.UNAME, userName)
    }

    override suspend fun storeHistory(data: HistoryEntity) {
        dao.insertHistory(data)
    }

    /**
     * get function
     */


    override suspend fun getUserEmail(): Flow<Resource<String>> {
        return dataStore.email.map { Resource.Success(it) }
    }

    override suspend fun getFullName(): Flow<Resource<String>> {
        return dataStore.fullName.map { Resource.Success(it) }
    }

    override suspend fun getUserName(): Flow<Resource<String>> {
        // Mengambil nama pengguna dari DataStore
        return dataStore.userName.map { Resource.Success(it) }
    }

    override fun getUserHistory(userEmail: String): Flow<Resource<List<HistoryEntity>>> {
        return dao.getUserHistory(userEmail)
            .map { historyList ->
                if (historyList.isNotEmpty()) {
                    Resource.Success(historyList)
                } else {
                    Resource.Error(-1, "Data history tidak tersedia")
                }
            }
            .catch { e ->
                emit(Resource.Error(-1, e.message ?: "Something wrong."))
            }
            .flowOn(Dispatchers.IO)
    }
}
