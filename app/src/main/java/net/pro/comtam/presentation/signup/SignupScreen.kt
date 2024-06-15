package net.pro.comtam.presentation.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.rememberScaffoldState
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
import kotlinx.coroutines.flow.collectLatest
import net.pro.comtam.R
import net.pro.comtam.ui.theme.backgroundLight
import net.pro.comtam.utils.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val email by viewModel.email
    val password by viewModel.password

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
                text = stringResource(R.string.create_an_account),
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                TextField(
                    value = email.text,
                    onValueChange = { viewModel.onEvent(SignupEvent.EnteredEmail(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    placeholder = {
                        Text(
                            text = email.hint,
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
                    value = password.text,
                    onValueChange = { viewModel.onEvent(SignupEvent.EnteredPassword(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    placeholder = {
                        Text(
                            text = password.hint,
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
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                onClick = {
                    viewModel.onEvent(SignupEvent.PerformSignUp {
                        val emailText = email.text
                        val passwordText = password.text
                        navController.navigate(
                            Screen.LoginScreen.createRoute(
                                emailText,
                                passwordText
                            )
                        )
                    })
                }
            ) {
                Text(
                    text = stringResource(R.string.signup),
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
                    text = stringResource(R.string.or_sign_up_with),
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
                text = stringResource(R.string.already_an_account),
                color = Color.White,
                modifier = Modifier
                    .clickable {
                        navController.navigateUp()
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
