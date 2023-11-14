package desriel.kiki.ojekku.presentation.screen.ride

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.domain.model.PlacesModel
import desriel.kiki.ojekku.presentation.component.OrderButton
import desriel.kiki.ojekku.presentation.component.PointField
import desriel.kiki.ojekku.presentation.screen.car.CarUiState
import desriel.kiki.ojekku.presentation.screen.car.CarViewModel
import desriel.kiki.ojekku.presentation.screen.home.HistoryViewModel
import desriel.kiki.ojekku.presentation.screen.ride.pick_location.PLACES_BUNDLE
import desriel.kiki.ojekku.presentation.theme.Primary
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CarScreen(
    saveStateHandle: SavedStateHandle?,
    carViewModel: CarViewModel,
    historyViewModel: HistoryViewModel,
    onPickupClick: () -> Unit,
    onDestinationClick: () -> Unit,
    onOrderClick: () -> Unit
) {

    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val placesResult by saveStateHandle?.getStateFlow<PlacesModel>(PLACES_BUNDLE, PlacesModel())!!
        .collectAsState()

    var pickup by remember {
        mutableStateOf(PlacesModel())
    }
    var destination by remember {
        mutableStateOf(PlacesModel())
    }
    var tracks = remember {
        mutableStateListOf<LatLng>()
    }

    val uiState by carViewModel.uiState.collectAsState()

    LaunchedEffect(placesResult) {
        if (placesResult.locationName.isNotEmpty()) {
            if (placesResult.isPickupLocation) {
                pickup = placesResult
            } else {
                destination = placesResult
            }
        }
    }

    LaunchedEffect(pickup, destination) {
        if (pickup.locationName.isNotEmpty() && destination.locationName.isNotEmpty()) {
            carViewModel.getPlaceRoutes(
                Pair(pickup.latitude, pickup.longitude),
                Pair(destination.latitude, destination.longitude)
            )

        }

    }

    LaunchedEffect(uiState) {
        (uiState as? CarUiState.Success)?.data?.routes?.forEach {
            it?.legs?.forEach {
                it?.steps?.forEach {
                    tracks.add(LatLng(it?.startLocation?.lat ?: 0.0, it?.startLocation?.lng ?: 0.0))
                    tracks.add(LatLng(it?.endLocation?.lat ?: 0.0, it?.endLocation?.lng ?: 0.0))
                }
            }
        }
    }


    if (locationPermissionState.allPermissionsGranted) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                GoogleMap(

                    modifier = Modifier.fillMaxSize(),
                    uiSettings = MapUiSettings(zoomControlsEnabled = false, compassEnabled = false)
                ) {
                    if (tracks.isNotEmpty()) {
                        Polyline(points = tracks, color = Primary)
                    }
                }
                PointField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                        .padding(horizontal = 24.dp),
                    readOnly = true,
                    pickupValue = pickup.locationName,
                    destinationValue = destination.locationName,
                    pickupPlaceholder = stringResource(id = R.string.pickup_location_txt),
                    destinationPlaceholder = stringResource(R.string.destination_location_txt),
                    onPickupFocus = onPickupClick,
                    onDestinationFocus = onDestinationClick,
                    onClickedEditButton = {}
                )
            }
            carViewModel.carTariff?.let { tariff ->
                carViewModel.distance?.let { distance ->
                    var formattedDateTime by remember { mutableStateOf("") }

                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.US)

                    formattedDateTime = currentDateTime.format(formatter)

                    OrderButton(
                        onClick = {
                            onOrderClick()
                            carViewModel.saveHistory(
                                historyViewModel.getUserEmail(),
                                formattedDateTime,
                                formattedDateTime,
                                "Car",
                                pickup.displayName,
                                destination.displayName,
                                "",
                                carViewModel.carTariff!!
                            )
                        },
                        distance = distance,
                        tariff = tariff,
                        title = "Pesan Ojekku Ride"
                    )
                }
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(R.string.location_permission_message),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 16.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Primary
                ),
                onClick = {
                    locationPermissionState.launchMultiplePermissionRequest()
                },
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.allow_permission),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

