package desriel.kiki.core.data.interactors

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.domain.repository.LanguageRepository
import desriel.kiki.core.domain.usecase.LanguageUseCase
import kotlinx.coroutines.flow.Flow

class LanguageInteractor(
    private val languageRepository: LanguageRepository
) : LanguageUseCase {
    override suspend fun getSavedLanguage(): Flow<Resource<String>> {
        return languageRepository.getSavedLanguage()
    }

    override suspend fun saveSelectedLanguage(language: String) {
        languageRepository.saveSelectedLanguage(language)
    }

}
