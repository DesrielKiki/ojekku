package desriel.kiki.core.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OjekkuDataStore constructor(
  private val context: Context
) {

  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "jeky_datastore.pb")

  suspend fun <T> storeData(key: Preferences.Key<T>, value: T) {
    context.dataStore.edit { pref ->
      pref[key] = value
    }
  }

  suspend fun clear() {
    context.dataStore.edit { pref ->
      pref.clear()
    }
  }

  val email: Flow<String>
    get() = context.dataStore.data.map { pref ->
      pref[EMAIL] ?: ""
    }
  val userName: Flow<String>
    get() = context.dataStore.data.map { pref ->
      pref[UNAME] ?: ""
    }
  val fullName: Flow<String>
    get() = context.dataStore.data.map { pref ->
      pref[FULLNAME] ?: ""
    }

  companion object {
    val EMAIL = stringPreferencesKey("EMAIL")
    val UNAME = stringPreferencesKey("UNAME")
    val FULLNAME = stringPreferencesKey("FULLNAME")
  }
}