package net.pro.comtam.utils

sealed class Screen(val route: String) {
    object Onboarding : Screen(route = "onboarding_screen")
    object LoginScreen : Screen("login_screen?email={email}&password={password}") {
        fun createRoute(email: String, password: String): String {
            return "login_screen?email=$email&password=$password"
        }
    }
    object SignupScreen : Screen(route = "signup_screen")
    object CompleteInfo: Screen(route = "complete_info_screen")
    object ConfirmOtp: Screen(route = "confirm_otp_screen")
    object HomeScreen : Screen(route = "home_screen")
    object History : Screen(route = "history_screen")
    object Cart : Screen(route = "cart_screen")
    object Profile : Screen(route = "profile_screen")
    object EditProfile : Screen(route = "edit_screen")
    object EditAvatarProfile : Screen(route = "edit_avatar_screen")
    object RestaurantDetails:Screen(route = "restaurant_details_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}