package desriel.kiki.ojekku.presentation.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.domain.usecase.AuthUseCase
import desriel.kiki.core.domain.usecase.UserUseCase
import desriel.kiki.ojekku.OjekkuApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val useCase: AuthUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _loginUiState = MutableSharedFlow<LoginUiState>()
    val loginUiState get() = _loginUiState.asSharedFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication)
                LoginViewModel(
                    application.ojekkuContainer.authUseCase,
                    application.ojekkuContainer.userUseCase
                )
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginUiState.emit(LoginUiState.Loading)
            useCase.login(email, password)
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _loginUiState.emit(LoginUiState.Success(it.data))
                        }

                        is Resource.Error -> {
                            _loginUiState.emit(LoginUiState.Error(it.message))
                        }

                        else -> Unit
                    }
                }
        }
    }

    fun storeEmail(email: String) {
        viewModelScope.launch {
            userUseCase.storeEmail(email)
        }
    }

    fun storeUserName(userName: String) {
        viewModelScope.launch {
            userUseCase.storeUserName(userName)
        }
    }

    fun storeFullName(fullName: String) {
        viewModelScope.launch {
            userUseCase.storeFullName(fullName)
        }
    }

    private val userNameState = mutableStateOf("")

    init {
        loadUserName()
    }

    private fun loadUserName() {
        viewModelScope.launch {
            userUseCase.getUserName()
                .collect { resource ->
                    if (resource is Resource.Success) {
                        userNameState.value = resource.data
                    } else if (resource is Resource.Error) {
                        // Handle error if needed
                    }
                }
        }
    }

    // Function to get the username as a State
    fun getUserName(): State<String> {
        return userNameState
    }


}