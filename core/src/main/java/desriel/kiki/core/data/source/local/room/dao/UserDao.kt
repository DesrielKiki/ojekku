package desriel.kiki.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.core.data.source.local.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {


  /**
  insert function
   */

  @Insert
  suspend fun insertUser(userEntity: UserEntity)
  @Insert
  suspend fun insertHistory(historyEntity: HistoryEntity)

  /**
   get function
   */
  @Query("SELECT * FROM user WHERE email = :email AND password = :password")
  fun getUserByEmailAndPassword(email: String, password: String): Flow<List<UserEntity>>
  @Query("SELECT * FROM history_table WHERE userEmail = :userEmail")
  fun getUserHistory(userEmail: String): Flow<List<HistoryEntity>>


}