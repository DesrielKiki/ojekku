package desriel.kiki.ojekku.presentation.screen.ride.pick_location

import desriel.kiki.core.domain.model.Places

sealed class PickLocationUiState {

    class Success(val data: Places) : PickLocationUiState()
    class Error(val messages: String) : PickLocationUiState()
    object Idle : PickLocationUiState()
    object Loading : PickLocationUiState()
}