package desriel.kiki.ojekku.presentation.screen.ride

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.core.data.source.local.room.repository.HistoryRepository
import desriel.kiki.core.domain.usecase.PlacesUseCase
import desriel.kiki.core.domain.usecase.UserUseCase
import desriel.kiki.ojekku.OjekkuApplication
import desriel.kiki.ojekku.presentation.screen.home.HistoryUiState
import desriel.kiki.ojekku.presentation.screen.register.RegisterUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class RideViewModel constructor(
    private val placesUseCase: PlacesUseCase,
    private val userUseCase : UserUseCase
    ) : ViewModel() {


    private val _uiState = MutableStateFlow<RideUiState>(RideUiState.Idle)
    val uiState: StateFlow<RideUiState> get() = _uiState.asStateFlow()

    private val _historyUiState = MutableSharedFlow<HistoryUiState>()

    var rideTariff: String? = null
    var distance: String? = null
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OjekkuApplication)
                RideViewModel(
                    application.ojekkuContainer.placesUseCase,
                    application.ojekkuContainer.userUseCase
                )
            }
        }
    }

    fun getPlaceRoutes(origin: Pair<Double, Double>, destination: Pair<Double, Double>) {
        viewModelScope.launch {
            _uiState.emit(RideUiState.Loading)
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

                                    val tariff = distanceInKilometers * 5000
                                    val formattedTariff = formatToRupiah(tariff)
                                    val decimalFormat = DecimalFormat("0.00")
                                    val formattedDistance =
                                        decimalFormat.format(distanceInKilometers) + " Km"

                                    rideTariff = formattedTariff
                                    distance = formattedDistance
                                    Log.d("ride view model", "tarif perjalanna = $formattedTariff")
                                    // Sekarang Anda memiliki jarak perjalanan dalam kilometer (distance) dan dalam teks (distanceText).
                                    // Anda dapat menggunakannya sesuai kebutuhan aplikasi Anda.
                                }
                            }
                            _uiState.emit(RideUiState.Success(routeData))
                        }

                        is Resource.Error -> {
                            _uiState.emit(RideUiState.Error(it.message))
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

    fun saveHistory(
        userEmail : String,
        orderTime : String,
        finishTime  : String,
        orderType : String,
        pickLocation : String,
        destinationLocation : String,
        description: String,
        tariff : String
    ) {
        viewModelScope.launch {
            _historyUiState.emit(HistoryUiState.Loading)
            // Lakukan operasi penyimpanan data riwayat di sini, misalnya memanggil repository atau DAO
            try {
                val historyItem = HistoryEntity(
                    0L,
                    userEmail,
                    orderTime,
                    finishTime,
                    orderType,
                    pickLocation,
                    destinationLocation,
                    description,
                    tariff
                )
                userUseCase.storeHistory(historyItem)
            } catch (e: Exception) {
                _historyUiState.emit(HistoryUiState.Error(e.message ?: "Gagal menyimpan riwayat."))
            }
        }
    }
}
