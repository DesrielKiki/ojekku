package desriel.kiki.ojekku.presentation.screen.ride

import desriel.kiki.core.data.source.remote.dto.response.GetPlacesRoutesResponse

sealed class RideUiState {
    class Success(val data: GetPlacesRoutesResponse): RideUiState()

    class Error(val message: String): RideUiState()

    object Idle: RideUiState()

    object Loading: RideUiState()

}