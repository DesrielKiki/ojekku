package desriel.kiki.ojekku.presentation.screen.register

import desriel.kiki.core.domain.model.User

sealed class RegisterUiState {
    class Success(val data: User) : RegisterUiState()

    class Error(val message: String) : RegisterUiState()

    object Idle : RegisterUiState()

    object Loading : RegisterUiState()


}