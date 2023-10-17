package desriel.kiki.core.domain.usecase

import desriel.kiki.core.data.source.Resource
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    suspend fun isUserLoggedIn(): Flow<Resource<Boolean>>
    suspend fun storeEmail(email: String)
    suspend fun logout()

}