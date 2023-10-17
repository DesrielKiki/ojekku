package desriel.kiki.ojekku.presentation.screen.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.domain.model.User
import desriel.kiki.core.domain.repository.AuthRepository
import desriel.kiki.core.domain.repository.UserRepository
import desriel.kiki.core.domain.usecase.AuthUseCase
import desriel.kiki.ojekku.OjekkuApplication
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel constructor(
    private val useCase: AuthUseCase
): ViewModel() {

    private val _registerUiState = MutableSharedFlow<RegisterUiState>()
    val registerUiState get() = _registerUiState.asSharedFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication)
                RegisterViewModel(application.ojekkuContainer.authUseCase)
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            _registerUiState.emit(RegisterUiState.Loading)
            useCase.register(user)
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _registerUiState.emit(RegisterUiState.Success(it.data))
                        }
                        is Resource.Error -> {
                            _registerUiState.emit(RegisterUiState.Error(it.message))
                        }
                        else -> Unit
                    }
                }
        }
    }
}