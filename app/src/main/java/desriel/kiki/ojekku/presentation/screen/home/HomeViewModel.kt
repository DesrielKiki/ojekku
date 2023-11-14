package desriel.kiki.ojekku.presentation.screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.core.domain.usecase.UserUseCase
import desriel.kiki.ojekku.OjekkuApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    private val userUseCase: UserUseCase

) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication)
                HomeViewModel(
                    application.ojekkuContainer.userUseCase
                )
            }
        }
    }

}

class HistoryViewModel constructor(
    private val userUseCase: UserUseCase

) : ViewModel() {
    private var userEmail: String = ""


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication
                HistoryViewModel(
                    application.ojekkuContainer.userUseCase
                )
            }
        }
    }

    private val userEmailState = mutableStateOf("")
    private val userHistoryState = mutableStateOf<List<HistoryEntity>>(emptyList())

    init {
        loadUserEmail()
    }

    private fun loadUserEmail() {
        viewModelScope.launch {
            userUseCase.getUserEmail()
                .collect { resource ->
                    if (resource is Resource.Success) {
                        userEmailState.value = resource.data
                        userEmail = userEmailState.value
                    } else if (resource is Resource.Error) {
                        {}
                    }
                }
        }
    }

    fun getUserHistoryForCurrentUser(): Flow<Resource<List<HistoryEntity>>> {
        return userUseCase.getUserHistory(userEmail)
    }

    fun getUserEmail(): String {
        return userEmail
    }
//    fun getHistoryForCurrentUser(): List<HistoryEntity> {
//        return repository.getHistoryForUser(userEmail)
//    }

}
