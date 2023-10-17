package desriel.kiki.ojekku.presentation.screen.home

import desriel.kiki.core.data.source.local.room.entity.HistoryEntity

sealed class HistoryUiState {
    data class ShowHistory(val historyItems: List<HistoryEntity>) : HistoryUiState()
    data class Success(val historyItems: HistoryEntity) : HistoryUiState()
    data class Error(val message: String) : HistoryUiState()
    object Loading : HistoryUiState()
}
