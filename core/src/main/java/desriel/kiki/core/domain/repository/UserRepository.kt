package desriel.kiki.core.domain.repository

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    suspend fun isUserLoggedIn(): Flow<Resource<Boolean>>
    suspend fun storeEmail(email: String)
    suspend fun storeUserName(userName: String)
    suspend fun storeHistory(data : HistoryEntity)

    suspend fun storeFullName(fullName: String)


    suspend fun getUserName(): Flow<Resource<String>>
    suspend fun getFullName(): Flow<Resource<String>>
    suspend fun getUserEmail(): Flow<Resource<String>>

    fun getUserHistory(userEmail: String): Flow<Resource<HistoryEntity>>


    suspend fun logout()
    val userName: Flow<String>

}