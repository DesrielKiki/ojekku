package desriel.kiki.core.data.repository

import desriel.kiki.core.data.source.local.datastore.OjekkuDataStore
import desriel.kiki.core.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.first

class LanguageRepositoryImpl(private val dataStore: OjekkuDataStore) : LanguageRepository {

    override suspend fun getSelectedLanguage(): String {
        return dataStore.selectedLanguage.first() // Mengambil bahasa yang dipilih dari DataStore
    }

    override suspend fun setSelectedLanguage(language: String) {
        dataStore.setSelectedLanguage(language) // Menyimpan bahasa yang dipilih ke DataStore
    }
}
