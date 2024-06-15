package net.pro.comtam.presentation.login

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import net.pro.comtam.R
import net.pro.comtam.utils.Screen
import kotlinx.coroutines.flow.collectLatest
import net.pro.comtam.ui.theme.backgroundLight
import net.pro.comtam.ui.theme.isSelectItemNav

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val emailState by viewModel.email
    val passwordState by viewModel.password

    val email = navController.currentBackStackEntry?.arguments?.getString("email") ?: ""
    val password = navController.currentBackStackEntry?.arguments?.getString("password") ?: ""

    LaunchedEffect(email, password) {
        viewModel.onEvent(LoginEvent.EnteredEmail(email))
        viewModel.onEvent(LoginEvent.EnteredPassword(password))
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundLight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val context = LocalContext.current as Activity
            context.window.statusBarColor = backgroundLight.toArgb()
            context.window.navigationBarColor = backgroundLight.toArgb()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 20.dp),
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
            Spacer(modifier = Modifier.height(38.dp))
            Text(
                text = stringResource(R.string.welcome_back),
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                TextField(
                    value = emailState.text,
                    onValueChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    placeholder = {
                        Text(
                            text = emailState.hint,
                            modifier = Modifier.alpha(0.5f)
                        )
                    },
                    shape = RoundedCornerShape(9.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = passwordState.text,
                    onValueChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    placeholder = {
                        Text(
                            text = passwordState.hint,
                            Modifier.alpha(0.5f)
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(9.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.forgot_password),
                    color = isSelectItemNav,
                    modifier = Modifier
                        .padding(20.dp, 0.dp)
                        .clickable {
                            Toast
                                .makeText(context, R.string.coming_soon, Toast.LENGTH_SHORT)
                                .show()
                        },
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                onClick = {
                    viewModel.onEvent(LoginEvent.PerformLogin {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                        }
                    })
                }
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 16.sp,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    color = Color.White,
                    modifier = Modifier
                        .weight(0.1f)
                        .width(1.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = stringResource(R.string.or_sign_in_with),
                    color = Color.White,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.width(16.dp))

                Divider(
                    color = Color.White,
                    modifier = Modifier
                        .weight(0.1f)
                        .width(1.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.goog_icon),
                    contentDescription = stringResource(R.string.login_with_google),
                    modifier = Modifier
                        .fillMaxSize(0.1f)
                        .clickable {
                            Toast
                                .makeText(context, R.string.coming_soon, Toast.LENGTH_SHORT)
                                .show()
                        }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.fb_icon),
                    contentDescription = stringResource(R.string.login_with_facebook),
                    modifier = Modifier
                        .fillMaxSize(0.1f)
                        .clickable {
                            Toast
                                .makeText(context, R.string.coming_soon, Toast.LENGTH_SHORT)
                                .show()
                        }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.new_to_account),
                color = Color.White,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.SignupScreen.route)
                    },
            )
            if (viewModel.isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(12.dp)
                )
            }
        }
    }
}
