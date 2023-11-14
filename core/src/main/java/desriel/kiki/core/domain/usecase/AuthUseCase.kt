package desriel.kiki.core.domain.usecase

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.domain.model.User
import kotlinx.coroutines.flow.Flow


interface AuthUseCase {

    suspend fun login(email: String, password: String): Flow<Resource<User>>
    suspend fun register(user: User): Flow<Resource<User>>

}