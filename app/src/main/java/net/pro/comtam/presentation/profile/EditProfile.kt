package net.pro.comtam.presentation.profile

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import net.pro.comtam.R
import net.pro.comtam.presentation.components.TextFieldWithLabel
import net.pro.comtam.ui.theme.backgroundLight
import net.pro.comtam.ui.theme.isSelectedColor
import net.pro.comtam.utils.Screen

@Composable
fun EditProfile(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val context = LocalContext.current as Activity
    context.window.statusBarColor = Color.Black.toArgb()
    context.window.navigationBarColor = backgroundLight.toArgb()

    val user by viewModel.user
    val isLoggedIn by viewModel.loginState.collectAsState(initial = false)
    val isLoading by viewModel.isLoading

    var hasLaunched by remember { mutableStateOf(false) }

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
            .background(Color.Black)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else {
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
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier
                                .weight(0.15f)
                                .padding(15.dp, 0.dp)
                        ) {
                            Icon(
                                tint = Color.White,
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }

                        Text(
                            text = stringResource(R.string.edit),
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterVertically)
                                .weight(0.75f)
                        )

                        Text(
                            text = stringResource(R.string.edit),
                            modifier = Modifier
                                .align(alignment = Alignment.CenterVertically)
                                .weight(0.15f)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .absoluteOffset(y = 90.dp)
                    .zIndex(2f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = stringResource(R.string.display_picture),
                    modifier = Modifier
                        .size(48.dp)
                        .offset(x = 32.dp, y = 20.dp)
                        .clip(CircleShape)
                        .zIndex(3f)

                )
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = stringResource(R.string.display_picture),
                    modifier = Modifier
                        .size(82.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .zIndex(2f)
                        .clickable {
                            navController.navigate(Screen.EditAvatarProfile.route)
                        }
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
                    Spacer(modifier = Modifier.height(65.dp))
                    TextFieldWithLabel(
                        value = user.userName,
                        onValueChange = { viewModel.onEvent(ProfileEvent.EnterUsername(it))},
                        label = stringResource(R.string.user_name),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        singleLine = true,
                        enabled = false // Disable editing for email field
                    )

                    TextFieldWithLabel(
                        value = user.email,
                        onValueChange = { },
                        label = "Email",
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        singleLine = true,
                        enabled = false // Disable editing for email field
                    )

                    TextFieldWithLabel(
                        value = user.phoneNumber,
                        onValueChange = { viewModel.onEvent(ProfileEvent.EnteredPhoneNumber(it)) },
                        label = "Số điện thoại",
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        singleLine = true
                    )

                    user.address?.let {
                        TextFieldWithLabel(
                            value = it.ward,
                            onValueChange = { viewModel.onEvent(ProfileEvent.EnteredWard(it)) },
                            label = "Phường",
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                            singleLine = true
                        )

                        TextFieldWithLabel(
                            value = it.street,
                            onValueChange = { viewModel.onEvent(ProfileEvent.EnteredStreet(it)) },
                            label = "Đường",
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                            singleLine = true
                        )

                        TextFieldWithLabel(
                            value = it.houseNumber,
                            onValueChange = { viewModel.onEvent(ProfileEvent.EnteredHouseNumber(it)) },
                            label = "Số nhà",
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            singleLine = true
                        )
                    }

                    Divider(
                        color = Color.Black,
                        thickness = 2.dp,
                        modifier = Modifier.padding(0.dp, 5.dp)
                    )

                    Button(
                        onClick = {
                            viewModel.onEvent(ProfileEvent.SaveProfile {
                                navController.navigateUp()
                            })
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(44.dp, 15.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = isSelectedColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = stringResource(R.string.edit))
                    }
                }
            }
        }
    }
}
