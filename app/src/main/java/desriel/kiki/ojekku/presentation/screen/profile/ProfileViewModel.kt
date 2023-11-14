package desriel.kiki.ojekku.presentation.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.domain.usecase.LanguageUseCase
import desriel.kiki.core.domain.usecase.UserUseCase
import desriel.kiki.ojekku.OjekkuApplication
import kotlinx.coroutines.launch

class ProfileViewModel constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val userNameState = mutableStateOf("")
    private val fullNameState = mutableStateOf("")

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication)
                ProfileViewModel(
                    application.ojekkuContainer.userUseCase
                )
            }
        }
    }

    init {
        loadUserName()
        loadFullName()
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

    private fun loadFullName() {
        viewModelScope.launch {
            userUseCase.getFullName()
                .collect { resource ->
                    if (resource is Resource.Success) {
                        fullNameState.value = resource.data
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

    fun getFullName(): State<String> {
        return fullNameState
    }

    fun logout() {
        viewModelScope.launch {
            userUseCase.logout()
        }
    }

}

class LanguageViewModel(private val languageUseCase: LanguageUseCase) : ViewModel() {
    val language = mutableStateOf("")


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication)
                LanguageViewModel(
                    application.ojekkuContainer.languageUseCase
                )
            }
        }
    }

    init {
        loadSavedLanguage()
    }

    private fun loadSavedLanguage() {
        viewModelScope.launch {
            languageUseCase.getSavedLanguage()
                .collect { resource ->
                    if (resource is Resource.Success) {
                        language.value = resource.data
                    } else if (resource is Resource.Error) {
                        language.value = "en"
                    }
                }
        }
    }

    fun saveSelectedLanguage(language: String) {
        viewModelScope.launch {
            languageUseCase.saveSelectedLanguage(language)
        }
    }

    fun getLanguage(): State<String> {
        return language
    }


}
