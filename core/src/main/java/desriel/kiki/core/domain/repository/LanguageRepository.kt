package desriel.kiki.core.domain.repository

import desriel.kiki.core.data.source.local.room.entity.Language


interface LanguageRepository {
    suspend fun getSelectedLanguage(): String
    suspend fun setSelectedLanguage(language: String)
}
