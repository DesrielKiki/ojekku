package desriel.kiki.core.data.repository

import android.database.sqlite.SQLiteConstraintException
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.dao.UserDao
import desriel.kiki.core.domain.model.User
import desriel.kiki.core.domain.model.toEntity
import desriel.kiki.core.domain.repository.AuthRepository
import desriel.kiki.core.data.source.local.room.entity.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class AuthRepositoryImpl constructor(
  private val dao: UserDao
): AuthRepository {
  override suspend fun login(email: String, password: String): Flow<Resource<User>> {
    return flow<Resource<User>> {
      val users = dao.getUserByEmailAndPassword(email, password).first()
      if (users.isNotEmpty()) {
        emit(Resource.Success(users.first().toDomain()))
      } else {
        emit(Resource.Error(-1, "Your credential not valid, please check your email and password!"))
      }
    }.catch { e ->
      emit(Resource.Error(-1, e.message ?: "Something wrong."))
    }.flowOn(Dispatchers.IO)
  }

  override suspend fun register(user: User): Flow<Resource<User>> {
    return flow<Resource<User>> {
      dao.insertUser(user.toEntity())
      emit(Resource.Success(user))
    }.catch { e ->
      when (e) {
        is SQLiteConstraintException -> {
          emit(Resource.Error(-1, "Email already registered in apps, use another email!"))
        }
        else -> {
          emit(Resource.Error(-1, e.message ?: "Something wrong"))
        }
      }
    }.flowOn(Dispatchers.IO)
  }
}