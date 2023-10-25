@file:Suppress("NAME_SHADOWING")

package desriel.kiki.ojekku.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.google.gson.Gson
import desriel.kiki.ojekku.domain.model.EmptyStateModel
import desriel.kiki.ojekku.domain.model.PlacesModel
import desriel.kiki.ojekku.presentation.navigation.Route
import desriel.kiki.ojekku.presentation.screen.car.CarViewModel
import desriel.kiki.ojekku.presentation.screen.error.ErrorScreen
import desriel.kiki.ojekku.presentation.screen.home.HistoryItemViewModel
import desriel.kiki.ojekku.presentation.screen.home.HomeScreen
import desriel.kiki.ojekku.presentation.screen.home.history.HistoryScreen
import desriel.kiki.ojekku.presentation.screen.ride.RideScreen
import desriel.kiki.ojekku.presentation.screen.login.LoginScreen
import desriel.kiki.ojekku.presentation.screen.login.LoginViewModel
import desriel.kiki.ojekku.presentation.screen.notification.NotificationScreen
import desriel.kiki.ojekku.presentation.screen.profile.ProfileScreen
import desriel.kiki.ojekku.presentation.screen.profile.ProfileViewModel
import desriel.kiki.ojekku.presentation.screen.register.RegisterScreen
import desriel.kiki.ojekku.presentation.screen.register.RegisterViewModel
import desriel.kiki.ojekku.presentation.screen.ride.CarScreen
import desriel.kiki.ojekku.presentation.screen.ride.RideViewModel
import desriel.kiki.ojekku.presentation.screen.ride.pick_location.PLACES_BUNDLE
import desriel.kiki.ojekku.presentation.screen.ride.pick_location.PickLocationBottomSheet
import desriel.kiki.ojekku.presentation.screen.ride.pick_location.PickLocationViewModel
import desriel.kiki.ojekku.presentation.theme.OjekkuTheme

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterialNavigationApi::class,
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

    val navigationItem = listOf(
        BottomNavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavigationItem(
            "Notification",
            Icons.Filled.Notifications,
            Icons.Outlined.Notifications
        ),
        BottomNavigationItem("Profile", Icons.Filled.Person, Icons.Outlined.Person),

        )

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(bottomBar = {
            NavigationBar {
                navigationItem.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            when (item.title) {
                                "Home" -> navController.navigate(Route.Home.route)
                                "Profile" -> navController.navigate(Route.Profile.route)
                                "Notification" -> navController.navigate(Route.Notification.route)
                            }
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = false,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        })
                }
            }
        }) {

        }
    }

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
                    route = Route.Profile.route
                ) {
                    val profileViewModel: ProfileViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = ProfileViewModel.Factory)

                    ProfileScreen(
                        profileViewModel = profileViewModel,
                        onLogoutClick = {
                            navController.navigate(Route.Login.route)
                        },
                    )
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
                    val rideViewModel: RideViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = RideViewModel.Factory)
                    val historyVIewmodel: HistoryItemViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = HistoryItemViewModel.Factory)
                    val saveStateHandle = navController.currentBackStackEntry?.savedStateHandle

                    RideScreen(
                        rideViewModel = rideViewModel,
                        saveStateHandle = saveStateHandle,
                        onPickupClick = {
                            navController.navigate("${Route.PickLocation.route}/true")
                        },
                        onOrderButtonClick = {
                            navController.navigate(Route.Home.route)
                        },
                        onDestinationClick = {
                            navController.navigate("${Route.PickLocation.route}/false")
                        },
                        historyViewmodel = historyVIewmodel
                        )
                }
                composable(
                    route = Route.Notification.route
                ) {
                    NotificationScreen()
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
                        onOrderClick = { navController.navigate(Route.Home.route) },
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
                    val historyItemViewModel: HistoryItemViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = HistoryItemViewModel.Factory)
                    val loginViewModel: LoginViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = LoginViewModel.Factory)

                    HomeScreen(
                        onCarButtonClicked = { navController.navigate(Route.Car.route) },
                        onRideButtonClicked = { navController.navigate(Route.Ride.route) },
                        historyItemViewModel = historyItemViewModel,
                        onClickHistory = {
                            navController.navigate(Route.HistoryScreen.route)
                        },
                        loginViewModel = loginViewModel

                    )
                }
                bottomSheet(
                    route = Route.HistoryScreen.route
                ) {
                    val historyItemViewModel: HistoryItemViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = HistoryItemViewModel.Factory)

                    HistoryScreen(
                        viewModel = historyItemViewModel, onClose = { navController.popBackStack() }
                    )
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
                        isToGetPickupLocation = isToGetPickupLocation,
                        viewModel = viewModel,
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
