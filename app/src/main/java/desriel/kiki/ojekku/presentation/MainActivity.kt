package desriel.kiki.ojekku.presentation

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.gson.Gson
import desriel.kiki.ojekku.domain.model.EmptyStateModel
import desriel.kiki.ojekku.domain.model.PlacesModel
import desriel.kiki.ojekku.presentation.navigation.Route
import desriel.kiki.ojekku.presentation.screen.car.CarScreen
import desriel.kiki.ojekku.presentation.screen.car.CarViewModel
import desriel.kiki.ojekku.presentation.screen.error.ErrorScreen
import desriel.kiki.ojekku.presentation.screen.home.HistoryItemViewModel
import desriel.kiki.ojekku.presentation.screen.home.HomeScreen
import desriel.kiki.ojekku.presentation.screen.ride.RideScreen
import desriel.kiki.ojekku.presentation.screen.login.LoginScreen
import desriel.kiki.ojekku.presentation.screen.login.LoginViewModel
import desriel.kiki.ojekku.presentation.screen.register.RegisterScreen
import desriel.kiki.ojekku.presentation.screen.register.RegisterViewModel
import desriel.kiki.ojekku.presentation.screen.ride.RideViewModel
import desriel.kiki.ojekku.presentation.screen.ride.pick_location.PLACES_BUNDLE
import desriel.kiki.ojekku.presentation.screen.ride.pick_location.PickLocationBottomSheet
import desriel.kiki.ojekku.presentation.screen.ride.pick_location.PickLocationViewModel
import desriel.kiki.ojekku.presentation.theme.OjekkuTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isSplashFinished.value
            }
        }
        setContent {
            OjekkuTheme {
                OjekkuApps(viewModel)
            }
        }
    }
}

@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalPermissionsApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun OjekkuApps(
    viewModel: MainViewModel
) {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val bottomSheetNavigator = remember { BottomSheetNavigator(sheetState) }
    val navController = rememberNavController(bottomSheetNavigator)
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator
    ) {
        isUserLoggedIn?.let { isLoggedIn ->

            NavHost(
                navController = navController,
                startDestination = if (isLoggedIn) Route.Home.route else Route.Login.route
            ) {
                composable(
                    route = Route.Login.route
                ) {
                    val viewModel: LoginViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = LoginViewModel.Factory)

                    LoginScreen(viewModel = viewModel,
                        onNavigateToHome = { navController.navigate(Route.Home.route) },
                        onNavigateToRegister = { navController.navigate(Route.Register.route) },
                        onLoginError = {
                            val json = Uri.encode(Gson().toJson(it))
                            navController.navigate("${Route.Error.route}/$json")
                        })
                }
                composable(
                    route = Route.Register.route
                ) {
                    val viewModel: RegisterViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = RegisterViewModel.Factory)
                    RegisterScreen(viewModel = viewModel,
                        onNavigateBack = { navController.popBackStack() },
                        onRegisterError = {
                            val json = Uri.encode(Gson().toJson(it))
                            navController.navigate("${Route.Error.route}/$json")
                        },
                        onNavigateToHome = { navController.navigate(Route.Home.route) })
                }
                composable(
                    route = Route.Ride.route
                ) {
                    val viewModel: RideViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = RideViewModel.Factory)
                    val saveStateHandle = navController.currentBackStackEntry?.savedStateHandle

                    RideScreen(
                        viewModel = viewModel,
                        saveStateHandle = saveStateHandle,
                        onPickupClick = {
                            navController.navigate("${Route.PickLocation.route}/true")
                        },
                        onDestinationClick = {
                            navController.navigate("${Route.PickLocation.route}/false")
                        })
                }
                composable(
                    route = Route.Car.route
                ) {
                    val viewModel: CarViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = CarViewModel.Factory)
                    val saveStateHandle = navController.currentBackStackEntry?.savedStateHandle

                    CarScreen(
                        viewModel = viewModel,
                        saveStateHandle = saveStateHandle,
                        locationPermissionState = locationPermissionState,
                        onPickupClick = {
                            navController.navigate("${Route.PickLocation.route}/true")
                        },
                        onDestinationClick = {
                            navController.navigate("${Route.PickLocation.route}/false")
                        })
                }
                composable(
                    route = Route.Home.route
                ) {
                    val viewModel: HistoryItemViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = HistoryItemViewModel.Factory)

                    HomeScreen(
                        onCarButtonClicked = { navController.navigate(Route.Car.route) },
                        onRideButtonClicked = { navController.navigate(Route.Ride.route) },
                        historyItemViewModel = viewModel)
                }
                bottomSheet(
                    route = "${Route.PickLocation.route}/{isToGetPickupLocation}",
                    arguments = listOf(
                        navArgument("isToGetPickupLocation") { type = NavType.BoolType }
                    )
                ) { backStackEntry ->
                    val isToGetPickupLocation =
                        backStackEntry.arguments?.getBoolean("isToGetPickupLocation") ?: true
                    val viewModel: PickLocationViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = PickLocationViewModel.Factory)
                    PickLocationBottomSheet(
                        isToGetPickupLocation,
                        viewModel,
                        onPlaceClick = { place, isPickupPlace ->
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set(
                                    PLACES_BUNDLE, PlacesModel(
                                        locationName = place.formattedAddress,
                                        displayName = place.displayName.text,
                                        latitude = place.location.latitude,
                                        longitude = place.location.longitude,
                                        isPickupLocation = isPickupPlace
                                    )
                                )
                            navController.popBackStack()
                        },
                        onClose = {
                            navController.popBackStack()
                        }
                    )

                }
                bottomSheet(
                    route = "${Route.Error.route}/{empty-params}",
                    arguments = listOf(
                        navArgument("empty-params") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val emptyParams = backStackEntry.arguments?.getString("empty-params")
                    ErrorScreen(
                        Gson().fromJson(emptyParams, EmptyStateModel::class.java)
                    ) {
                        navController.popBackStack()
                    }
                }

            }

        }
    }
}
