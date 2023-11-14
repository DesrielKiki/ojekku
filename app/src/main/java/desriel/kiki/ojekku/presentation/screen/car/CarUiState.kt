package desriel.kiki.ojekku.presentation.screen.car

import desriel.kiki.core.data.source.remote.dto.response.GetPlacesRoutesResponse

sealed class CarUiState {
    class Success(val data: GetPlacesRoutesResponse) : CarUiState()

    class Error(val message: String) : CarUiState()

    object Idle : CarUiState()

    object Loading : CarUiState()

}