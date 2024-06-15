package net.pro.comtam.presentation.profile

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import net.pro.comtam.R
import net.pro.comtam.presentation.components.TextFieldWithLabel
import net.pro.comtam.ui.theme.backgroundDark
import net.pro.comtam.ui.theme.backgroundLight
import net.pro.comtam.utils.Screen


@Composable
fun Profile(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val context = LocalContext.current as Activity
    context.window.statusBarColor = Color.Black.toArgb()
    context.window.navigationBarColor = backgroundLight.toArgb()

    val user by viewModel.user
    val userName = user.userName ?: ""
    val email = user.email ?: ""
    val phoneNumber = user.phoneNumber ?: ""
    val ward = user.address.ward ?: ""
    val street = user.address.street ?: ""
    val houseNumber = user.address.houseNumber ?: ""

    val isLoggedIn by viewModel.loginState.collectAsState(initial = false)

    var hasLaunched by remember { mutableStateOf(false) }

    // LaunchedEffect to observe the logout event
    LaunchedEffect(key1 = isLoggedIn) {
        if (hasLaunched && !isLoggedIn) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
        hasLaunched = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        Box(
            modifier = Modifier
                .padding(0.dp, 20.dp)
                .height(200.dp)
                .zIndex(0f)
                .fillMaxWidth()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Hồ Sơ",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 8.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.7f)
                    ) {
                        if (isLoggedIn) {
                            Text(
                                text = stringResource(R.string.edit),
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier.clickable {
                                    navController.navigate(Screen.EditProfile.route)
                                }
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f)
                    ) {
                        if (isLoggedIn) {
                            IconButton(onClick = {
                                viewModel.logout()
                            }) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        fontSize = 16.sp,
                                        text = stringResource(R.string.log_out),
                                        color = Color.White
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowRight,
                                        contentDescription = stringResource(R.string.log_out),
                                        tint = Color.White
                                    )
                                }
                            }
                        } else {
                            Button(onClick = {
                                navController.navigate(Screen.LoginScreen.route)
                            }) {
                                Text(text = stringResource(R.string.login))
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .absoluteOffset(y = 100.dp)
                .zIndex(2f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = stringResource(R.string.display_picture),
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .zIndex(2f)
                    .clickable { }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .absoluteOffset(y = 130.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundLight)
                .zIndex(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Text(color = Color.White, text = "@$userName")

                TextFieldWithLabel(
                    value = email,
                    onValueChange = { },
                    label = "Email",
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    singleLine = true
                )


                TextFieldWithLabel(
                    value = phoneNumber,
                    onValueChange = { },
                    label = "Số điện thoại",
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    singleLine = true
                )

                TextFieldWithLabel(
                    value = ward,
                    onValueChange = { },
                    label = "Phường",
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    singleLine = true
                )

                TextFieldWithLabel(
                    value = street,
                    onValueChange = { },
                    label = "Đường",
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    singleLine = true
                )

                TextFieldWithLabel(
                    value = houseNumber,
                    onValueChange = { },
                    label = "Số nhà",
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    singleLine = true
                )
            }
        }
    }
}


