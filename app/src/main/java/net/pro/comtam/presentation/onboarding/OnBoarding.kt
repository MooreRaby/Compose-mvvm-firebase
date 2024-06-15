package net.pro.comtam.presentation.onboarding

import android.app.Activity
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import net.pro.comtam.presentation.onboarding.util.OnBoardingItem
import net.pro.comtam.utils.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pro.comtam.ui.theme.backgroundLight
import net.pro.comtam.ui.theme.isSelectedColor


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoarding(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundLight
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current as Activity
            context.window.statusBarColor = backgroundLight.toArgb()
            context.window.navigationBarColor = backgroundLight.toArgb()

            val scope = rememberCoroutineScope()

            val items = OnBoardingItem.get()

            val pagerState = rememberPagerState()

            HorizontalPager(
                count = items.size,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.8f),
                state = pagerState,
            ) { page ->
                OnboardingPage(item = items[page])
            }
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 16.dp)
                    .fillMaxSize()
                    .weight(0.2f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSection(size = items.size, index = pagerState.currentPage) {
                    if (pagerState.currentPage + 1 < items.size) {
                        scope.launch {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }

                    } else {
                        viewModel.onEvent(OnBoardingEvent.CompleteOnboarding {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                }
            }

        }
    }

}


@Composable
fun OnboardingPage(item: OnBoardingItem) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(

            painter = painterResource(id = item.image),
            contentDescription = stringResource(id = item.title),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = item.title),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center

        )
        Text(
            text = stringResource(id = item.text),
            color = Color.White,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center

        )
    }

}

@Composable
fun BottomSection(
    size: Int,
    index: Int,
    onNextClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // indicators
        Indicators(size = size, index = index)

        // next button
        FloatingActionButton(
            onClick = onNextClicked,
            modifier = Modifier.align(CenterEnd),
            contentColor = Color.White,
            backgroundColor = isSelectedColor
        ) {
            Icon(Icons.Outlined.KeyboardArrowRight, null)
        }
    }
}


@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }

    }

}

@Composable
fun Indicator(isSelected: Boolean) {

    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )


    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(if (isSelected) isSelectedColor else Color.White)
    ) {

    }

}