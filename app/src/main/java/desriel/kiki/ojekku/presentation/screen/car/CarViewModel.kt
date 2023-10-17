package desriel.kiki.ojekku.presentation.screen.car

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.domain.usecase.PlacesUseCase
import desriel.kiki.ojekku.OjekkuApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class CarViewModel constructor(
    private val placesUseCase: PlacesUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow<CarUiState>(CarUiState.Idle)
    val uiState: StateFlow<CarUiState> get() = _uiState.asStateFlow()
    var carTariff: String? = null
    var distance: String? = null

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication)
                CarViewModel(
                    application.ojekkuContainer.placesUseCase

                )
            }
        }
    }

    fun getPlaceRoutes(origin: Pair<Double, Double>, destination: Pair<Double, Double>) {
        viewModelScope.launch {
            _uiState.emit(CarUiState.Loading)
            Log.d("ride view model", "Loading . . .")

            placesUseCase.getPlaceRoutes(
                "${origin.first},${origin.second}",
                "${destination.first},${destination.second}"
            )
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            val routeData = it.data

                            // Akses informasi rute
                            val routes = routeData.routes
                            if (routes != null) {
                                if (routes.isNotEmpty()) {
                                    val route =
                                        routes[0] // Ambil rute pertama (biasanya satu-satunya rute)

                                    // Akses informasi jarak
                                    val distanceInMeters =
                                        route?.legs?.sumBy { leg -> leg?.distance?.value ?: 0 } ?: 0
                                    val distanceInKilometers = distanceInMeters / 1000.0

                                    val tariff = distanceInKilometers * 7500
                                    val formattedTariff = formatToRupiah(tariff)

                                    val decimalFormat = DecimalFormat("0.00")
                                    val formattedDistance =
                                        decimalFormat.format(distanceInKilometers) + " Km"
                                    distance = formattedDistance
                                    carTariff = formattedTariff
                                    Log.d("ride view model", "tarif perjalanna = $formattedTariff")
                                    // Sekarang Anda memiliki jarak perjalanan dalam kilometer (distance) dan dalam teks (distanceText).
                                    // Anda dapat menggunakannya sesuai kebutuhan aplikasi Anda.
                                }
                            }
                            _uiState.emit(CarUiState.Success(routeData))
                        }

                        is Resource.Error -> {
                            _uiState.emit(CarUiState.Error(it.message))
                        }

                        else -> Unit
                    }

                }
        }
    }

    private fun formatToRupiah(amount: Double): String {
        val locale = Locale("id", "ID")
        val format = NumberFormat.getCurrencyInstance(locale)

        return format.format(amount).replace("IDR", "Rp.").replace(",00", ",-")
    }
}
