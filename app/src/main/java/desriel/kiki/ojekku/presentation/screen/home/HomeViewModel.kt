package desriel.kiki.ojekku.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.core.data.source.local.room.repository.HistoryRepository
import desriel.kiki.core.domain.usecase.UserUseCase
import desriel.kiki.ojekku.OjekkuApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

class HistoryItemViewModel constructor(
    private val userUseCase: UserUseCase

) : ViewModel() {
    private var userEmail: String = ""



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication
                HistoryItemViewModel(
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
                        Log.d("home view model", "curren user email = $userEmail")
                    } else if (resource is Resource.Error) {
                        // Handle error if needed
                    }
                }
        }
    }
    fun getUserHistoryForCurrentUser(): Flow<Resource<HistoryEntity>> {
        return userUseCase.getUserHistory(userEmail)
    }

    fun getUserEmail(): String {
        return userEmail
    }

    fun getUserHistory(){
        return
    }
//    fun getHistoryForCurrentUser(): List<HistoryEntity> {
//        return repository.getHistoryForUser(userEmail)
//    }

}
