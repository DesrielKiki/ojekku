package desriel.kiki.ojekku.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.local.room.dao.UserDao
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.core.data.source.local.room.repository.HistoryRepository
import desriel.kiki.ojekku.OjekkuApplication
import desriel.kiki.ojekku.presentation.screen.car.CarViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoryItemViewModel(private val repository: HistoryRepository) : ViewModel() {
    val historyItems: StateFlow<HistoryUiState> = repository.allHistoryItems
        .map { historyItems ->
            if (historyItems.isNotEmpty()) {
                HistoryUiState.ShowHistory(historyItems)
            } else {
                HistoryUiState.Error("Tidak ada riwayat.")
            }
        }
        .stateIn(viewModelScope, started = Eagerly, initialValue = HistoryUiState.Loading)


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication
                val appDatabase =
                    application.appDatabase // Sesuaikan dengan cara Anda menginisialisasi AppDatabase

                val userDao = appDatabase.userDao()
                val historyRepository = HistoryRepository(userDao)
                HistoryItemViewModel(historyRepository)
            }
        }
    }
}
