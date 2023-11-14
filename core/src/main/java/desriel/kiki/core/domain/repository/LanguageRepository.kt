package desriel.kiki.core.domain.repository

import desriel.kiki.core.data.source.Resource
import kotlinx.coroutines.flow.Flow


interface LanguageRepository {

    suspend fun getSavedLanguage(): Flow<Resource<String>>
    suspend fun saveSelectedLanguage(language: String)
}
