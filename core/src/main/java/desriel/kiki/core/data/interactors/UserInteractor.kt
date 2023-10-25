package desriel.kiki.core.data.interactors

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.dao.UserDao
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.core.domain.repository.UserRepository
import desriel.kiki.core.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.Flow


class UserInteractor constructor(
    private val userRepository: UserRepository,
    private val userDao: UserDao
) : UserUseCase {

    override suspend fun isUserLoggedIn(): Flow<Resource<Boolean>> {
        return userRepository.isUserLoggedIn()
    }

    override suspend fun logout() {
        userRepository.logout()
    }



    /**
     * get function
     **/
    override suspend fun getUserName(): Flow<Resource<String>> {
        return userRepository.getUserName()
    }

    override suspend fun getFullName(): Flow<Resource<String>> {
        return userRepository.getFullName()
    }

    override suspend fun getUserEmail(): Flow<Resource<String>> {
        return userRepository.getUserEmail()
    }

    override fun getUserHistory(userEmail: String): Flow<Resource<HistoryEntity>> {
        return userRepository.getUserHistory(userEmail)
    }

    /**
     * store function
     **/

    override suspend fun storeEmail(email: String) {
        userRepository.storeEmail(email)
    }

    override suspend fun storeUserName(userName: String) {
        userRepository.storeUserName(userName)
    }

    override suspend fun storeFullName(fullName: String) {
        userRepository.storeFullName(fullName)
    }

    override suspend fun storeHistory(data: HistoryEntity) {
        userDao.insertHistory(data)
    }



}