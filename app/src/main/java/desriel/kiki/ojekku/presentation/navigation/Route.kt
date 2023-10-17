package desriel.kiki.ojekku.presentation.navigation

sealed class Route(val route : String){
    object Login : Route("Login")
    object Register : Route("Register")
    object Home : Route("Home")
    object Ride : Route("Ride")
    object Car : Route("Car")
    object Permission : Route ("Permission")
    object PickLocation : Route ("pick-location")
    object Error: Route("error")
}