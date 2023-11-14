package desriel.kiki.core.domain.usecase

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface UserUseCase {


    suspend fun isUserLoggedIn(): Flow<Resource<Boolean>>
    suspend fun logout()


    /**
     * store function
     **/
    suspend fun storeEmail(email: String)
    suspend fun storeUserName(userName: String)
    suspend fun storeFullName(fullName: String)
    suspend fun storeHistory(data: HistoryEntity)

    /**
     * get function
     **/

    suspend fun getUserName(): Flow<Resource<String>>
    suspend fun getFullName(): Flow<Resource<String>>
    suspend fun getUserEmail(): Flow<Resource<String>>
    fun getUserHistory(userEmail: String): Flow<Resource<List<HistoryEntity>>>


}