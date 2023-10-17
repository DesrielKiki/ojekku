package desriel.kiki.ojekku.presentation.screen.car.pick_location

import android.text.Editable.Factory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.remote.dto.response.toDomain
import desriel.kiki.core.domain.usecase.PlacesUseCase
import desriel.kiki.ojekku.OjekkuApplication
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PickLocationViewModel constructor(
    private val placesUseCase: PlacesUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<PickLocationUiState>(PickLocationUiState.Idle)
    val uiState : StateFlow<PickLocationUiState> get() = _uiState.asStateFlow()

    private var getPlacesJob : Job? = null

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication)
                PickLocationViewModel(
                    application.ojekkuContainer.placesUseCase
                )
            }
        }
    }
    fun getPlaces(keyword: String) {
        getPlacesJob?.cancel()
        getPlacesJob = viewModelScope.launch {
            _uiState.emit(PickLocationUiState.Loading)
            placesUseCase.getPlaces(keyword)
                .collect {
                    when(it) {
                        is Resource.Success -> {
                            it.data?.let {
                                _uiState.emit(PickLocationUiState.Success(it.toDomain()))
                            }
                        }
                        is Resource.Error -> {
                            _uiState.emit(PickLocationUiState.Error(it.message))
                        }
                        else -> Unit
                    }
                }
        }
    }
}