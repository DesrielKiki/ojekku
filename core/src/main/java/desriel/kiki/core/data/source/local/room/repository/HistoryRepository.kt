package desriel.kiki.core.data.source.local.room.repository

import desriel.kiki.core.data.source.local.room.dao.UserDao
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

class HistoryRepository(private val dao: UserDao) {
    suspend fun insertHistory(historyEntity: HistoryEntity) {
        dao.insertHistory(historyEntity)
    }

    val allHistoryItems: Flow<List<HistoryEntity>> = dao.getAllHistoryItems()
}
