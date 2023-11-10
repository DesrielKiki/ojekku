package desriel.kiki.core.domain.repository

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.entity.Language
import kotlinx.coroutines.flow.Flow


interface LanguageRepository {

    suspend fun getSavedLanguage(): Flow<Resource<String>>
    suspend fun saveSelectedLanguage(language: String)
}
