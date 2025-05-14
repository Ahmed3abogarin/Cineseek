package com.movies.cinemix.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.ui.theme.BorderColor
import com.movies.cinemix.ui.theme.BoxColor
import com.movies.cinemix.ui.theme.MyColor
import com.movies.cinemix.ui.theme.MyRed

@Composable
fun MoviePickerScreen(movie: Movies?, viewModel: PickerViewModel) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }

    var rotated by remember { mutableStateOf(false) }

    val roater by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    LaunchedEffect(false) {
        viewModel.getRandomMovies()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = 12.dp)
            .background(MyColor),
        contentAlignment = Alignment.Center
    ) {
        val showLoading = movie == null

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 56.dp),
            visible = true,
            enter = fadeIn(tween(300)) + scaleIn(tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + scaleOut(tween(300))
        ) {
            Text(
                text = "Random\nMovie Picker",
                fontSize = 38.sp,
                color = Color.White,
                letterSpacing = TextUnit(0.1f, TextUnitType.Em),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                lineHeight = TextUnit(1.3f, TextUnitType.Em)
            )
        }

        AnimatedVisibility(
            visible = showLoading,
            enter = fadeIn(tween(300)) + scaleIn(tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + scaleOut(tween(300))
        ) {

            RandomMoviesLoading()
        }
        isPlaying = false

        AnimatedVisibility(
            visible = !showLoading,
            enter = fadeIn() + scaleIn(tween(2000)),
            exit = fadeOut(animationSpec = tween(300)) + scaleOut(tween(300))
        ) {
            movie?.let {
                val imageRequest = remember(it.poster_path) {
                    ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/w500/${it.poster_path}")
                        .build()
                }
                val imagePainter = rememberAsyncImagePainter(model = imageRequest)


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "flip the card", color = Color.White, fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .height(300.dp)
                            .width(229.dp)
                            .graphicsLayer {
                                rotationY = roater
                                cameraDistance = 8 * density
                            }
                            .clickable { rotated = !rotated },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        if (!rotated) {
                            Image(
                                painter = painterResource(R.drawable.place_holder),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(229.dp, 300.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .graphicsLayer {
                                        rotationY = roater
                                    }
                            )


                        } else {
                            MoviePoster(imagePainter, it.title, roater)
                        }

                    }
                }

            }

        }

        Column(
            modifier =
                Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 105.dp)
                    .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                enabled = !showLoading,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BoxColor),
                border = BorderStroke(width = 1.dp, color = BorderColor),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                onClick = {
                    rotated = false
                    viewModel.getRandomMovies()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Picker another")
            }
        }
    }
}

@Composable
fun MoviePoster(image: AsyncImagePainter, title: String, roater: Float) {
    Column {

        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(229.dp, 300.dp)
                .clip(RoundedCornerShape(16.dp))
                .graphicsLayer {
                    rotationY = roater
                }

        )

        Text(text = "Today's movie is", color = Color.White, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = title, color = MyRed, fontSize = 26.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))
    }

}

@Composable
fun RandomMoviesLoading() {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.random_loading))
    val progress by animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition.value,
        progress = { progress }
    )
}


@Preview
@Composable
fun MyPreview() {
//    CinemixTheme {
//        MoviePickerScreen(
//            movie = Movies(
//                2,
//                true,
//                "",
//                listOf(1, 4),
//                3,
//                "",
//                "",
//                "Earl Eugene Scruggs was an American musician noted for popularizing a three-finger banjo picking style, now called .......",
//                4.4,
//                "",
//                "",
//                "Bad boys 3",
//                false,
//                5.0,
//                4
//            )
//        )
//    }
}