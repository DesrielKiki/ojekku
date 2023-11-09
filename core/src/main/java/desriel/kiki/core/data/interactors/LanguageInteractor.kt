package desriel.kiki.core.data.interactors

import desriel.kiki.core.domain.repository.LanguageRepository
import desriel.kiki.core.domain.usecase.LanguageUseCase

class LanguageInteractor(
    private val languageRepository: LanguageRepository
) : LanguageUseCase {
    override suspend fun getSelectedLanguage(): String {
        return languageRepository.getSelectedLanguage()
    }

    override suspend fun setSelectedLanguage(language: String) {
        languageRepository.setSelectedLanguage(language)
    }

}
