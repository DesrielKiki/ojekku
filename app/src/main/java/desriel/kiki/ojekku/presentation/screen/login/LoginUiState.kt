package desriel.kiki.ojekku.presentation.screen.login

import desriel.kiki.core.domain.model.User

sealed class LoginUiState {

    class Success(val data: User) : LoginUiState()

    class Error(val message: String) : LoginUiState()

    object Idle : LoginUiState()

    object Loading : LoginUiState()

}
