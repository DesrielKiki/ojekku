package desriel.kiki.core.domain.usecase

import desriel.kiki.core.data.source.local.room.entity.Language
import desriel.kiki.core.domain.repository.LanguageRepository

interface LanguageUseCase {
    suspend fun getSelectedLanguage(): String
    suspend fun setSelectedLanguage(language: String)
}
