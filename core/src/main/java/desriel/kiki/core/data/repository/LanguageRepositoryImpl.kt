package desriel.kiki.core.data.repository

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.datastore.OjekkuDataStore
import desriel.kiki.core.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LanguageRepositoryImpl(private val dataStore: OjekkuDataStore) : LanguageRepository {


    override suspend fun getSavedLanguage(): Flow<Resource<String>> {
        return dataStore.savedLanguage.map { Resource.Success(it) }
    }


    override suspend fun saveSelectedLanguage(language: String) {
        dataStore.storeData(OjekkuDataStore.SELECTED_LANGUAGE, language)
    }

}
