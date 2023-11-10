package desriel.kiki.core.domain.usecase

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.entity.Language
import desriel.kiki.core.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow

interface LanguageUseCase {
    suspend fun getSavedLanguage(): Flow<Resource<String>>
    suspend fun saveSelectedLanguage(language : String)

}
