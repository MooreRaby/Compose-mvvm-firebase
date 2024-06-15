package net.pro.comtam.presentation.profile

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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
fun EditAvatar(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val context = LocalContext.current as Activity
    context.window.statusBarColor = Color.Black.toArgb()
    context.window.navigationBarColor = backgroundLight.toArgb()


    val isLoading by viewModel.isLoading

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
                    .absoluteOffset(y = 35.dp)
                    .zIndex(2f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = stringResource(R.string.display_picture),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(80.dp)
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
                    .absoluteOffset(y = 220.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(backgroundLight)
                    .zIndex(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(180.dp))

                    Button(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(44.dp, 5.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .height(50.dp)
                            .width(200.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Chọn ảnh từ thư viện")
                    }


                    Button(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(44.dp, 15.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .height(50.dp)
                            .width(150.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = stringResource(R.string.edit))
                    }

                    Button(
                        onClick = {
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(44.dp, 30.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = isSelectedColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Lưu")
                    }
                }
            }
        }
    }
}
