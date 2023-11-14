package desriel.kiki.ojekku.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.domain.usecase.UserUseCase
import desriel.kiki.ojekku.OjekkuApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn: StateFlow<Boolean?> get() = _isUserLoggedIn

    private val _isSplashFinished = MutableStateFlow(false)
    val isSplashFinished: StateFlow<Boolean> get() = _isSplashFinished

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication)
                MainViewModel(application.ojekkuContainer.userUseCase)
            }
        }
    }

    init {
        getIsUserLoggedIn()
    }

    private fun getIsUserLoggedIn() {
        viewModelScope.launch {
            when (val result = userUseCase.isUserLoggedIn().first()) {
                is Resource.Success -> {
                    _isUserLoggedIn.update {
                        result.data
                    }
                    _isSplashFinished.update {
                        true
                    }
                }

                is Resource.Error -> {
                    Log.d("Get Is User Logged In", "Error: ${result.message}")
                }

                else -> Unit
            }
        }
    }

    fun setIsUserLoggedIn(isLoggedIn: Boolean) {
        _isUserLoggedIn.value = isLoggedIn
        Log.d("main viewmodel", "current userlogin value = $_isUserLoggedIn")
    }
}