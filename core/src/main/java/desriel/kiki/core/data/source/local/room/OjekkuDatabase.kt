package desriel.kiki.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import desriel.kiki.core.data.source.local.room.dao.UserDao
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.core.data.source.local.room.entity.UserEntity



@Database(
  entities = [UserEntity::class, HistoryEntity::class],
  version = 4,
  exportSchema = false
)
abstract class OjekkuDatabase: RoomDatabase() {

  abstract fun userDao(): UserDao

  companion object {
    @Volatile
    private var INSTANCE: OjekkuDatabase? = null
    fun getInstance(context: Context): OjekkuDatabase {
      synchronized(this) {
        var instance = INSTANCE
        if (instance == null) {
          val dbBuilder = Room.databaseBuilder(
            context.applicationContext,
            OjekkuDatabase::class.java,
            "jeky.database"
          )
            .fallbackToDestructiveMigration()

          instance = dbBuilder.build()
          INSTANCE = instance
        }

        return instance
      }
    }
  }
}