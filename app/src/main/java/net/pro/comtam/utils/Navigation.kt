package net.pro.comtam.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.pro.comtam.presentation.cart.Cart
import net.pro.comtam.presentation.home.Home
import net.pro.comtam.presentation.home.components.FoodikeBottomNavigation
import net.pro.comtam.presentation.login.LoginScreen
import net.pro.comtam.presentation.onboarding.OnBoarding
import net.pro.comtam.presentation.profile.Profile
import com.google.accompanist.pager.ExperimentalPagerApi
import net.pro.comtam.presentation.history.History
import net.pro.comtam.presentation.profile.EditAvatar
import net.pro.comtam.presentation.profile.EditProfile
import net.pro.comtam.presentation.signup.SignupScreen
import net.pro.comtam.ui.theme.backgroundLight
import net.pro.comtam.ui.theme.isSelectItemNav


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    scrollState: LazyListState
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Screen.Onboarding.route,
        ) {
            OnBoarding(navController = navController)
        }
        composable(
            route = Screen.LoginScreen.route,
        ) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.HomeScreen.route
        ) {
            Home(navController = navController, scrollState = scrollState)
        }
        composable(
            route = Screen.History.route
        ) {
            History(navHostController = navController)
        }
        composable(
            route = Screen.Cart.route
        ) {
            Cart(navController = navController)
        }
        composable(
            route = Screen.Profile.route
        ) {
            Profile(navController = navController)
        }

        composable(
            route = Screen.EditProfile.route,
        ) {
            EditProfile(navController = navController)
        }

        composable(
            route = Screen.EditAvatarProfile.route,
        ) {
            EditAvatar(navController = navController)
        }

        composable(
            route = Screen.SignupScreen.route,
        ) {
            SignupScreen(navController = navController)
        }


//        composable(
//            route = Screen.RestaurantDetails.route,
//        ) {
//            RestaurantDetail(
//                navController = navController
//            )
//        }
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SetupNavigation(startDestination: String) {

    val navController = rememberNavController()


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val scrollState = rememberLazyListState()
    val state by remember { derivedStateOf { scrollState.firstVisibleItemIndex == 0 } }


    Scaffold(
        bottomBar = {
            if ((currentRoute == Screen.HomeScreen.route
                        || currentRoute == Screen.History.route
                        || currentRoute == Screen.Cart.route
                        || currentRoute == Screen.Profile.route) && state
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        BottomBar(navController = navController)

                    }
                }
            }
        }
    ) {
        NavigationGraph(
            navController = navController,
            scrollState = scrollState,
            startDestination = startDestination
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    FoodikeBottomNavigation(
        backgroundColor = backgroundLight
    ) {
        BottomNavigationItem(
            label = {
                Text(text = stringResource(net.pro.comtam.R.string.home))
            },

            icon = {
                Icon(
                    painter = painterResource(net.pro.comtam.R.drawable.home),
                    contentDescription = stringResource(net.pro.comtam.R.string.home),
                    modifier = Modifier.size(width = 25.dp, height = 25.dp)

                )
            },
            selectedContentColor = isSelectItemNav,
            unselectedContentColor = Color.White,
            alwaysShowLabel = true,
            selected = currentRoute == Screen.HomeScreen.route,
            onClick = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        BottomNavigationItem(
            label = {
                Text(text = stringResource(net.pro.comtam.R.string.history))
            },
            icon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(net.pro.comtam.R.drawable.history),
                    contentDescription = stringResource(net.pro.comtam.R.string.history),
                )
            },
            selectedContentColor = isSelectItemNav,
            unselectedContentColor = Color.White,
            alwaysShowLabel = true,
            selected = currentRoute == Screen.History.route,
            onClick = {
                navController.navigate(Screen.History.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        BottomNavigationItem(
            label = {
                Text(text = stringResource(net.pro.comtam.R.string.cart))
            },
            icon = {
                Icon(
                    painter = painterResource(net.pro.comtam.R.drawable.cart),
                    contentDescription = stringResource(net.pro.comtam.R.string.cart),
                    modifier = Modifier.size(width = 25.dp, height = 25.dp)
                )
            },
            selectedContentColor = isSelectItemNav,
            unselectedContentColor = Color.White,
            alwaysShowLabel = true,
            selected = currentRoute == Screen.Cart.route,
            onClick = {
                navController.navigate(Screen.Cart.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        BottomNavigationItem(
            label = {
                Text(text = stringResource(net.pro.comtam.R.string.profile))
            },
            icon = {
                Icon(
                    painter = painterResource(net.pro.comtam.R.drawable.user),
                    contentDescription = stringResource(net.pro.comtam.R.string.cart),
                    modifier = Modifier.size(width = 25.dp, height = 25.dp)
                )
            },
            selectedContentColor = isSelectItemNav,
            unselectedContentColor = Color.White,
            alwaysShowLabel = true,
            selected = currentRoute == Screen.Profile.route,
            onClick = {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
