package com.movies.cinemix.presentation

import android.graphics.Paint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.ui.theme.CinemixTheme
import com.movies.cinemix.ui.theme.MyColor
import kotlinx.coroutines.delay

@Composable
fun MoviePickerScreen(movie: Movies?,viewModel: PickerViewModel) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }
    var ss by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
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
                .padding(top = 26.dp),
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

                Row(
                    modifier = Modifier.animateContentSize(
                        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                    )
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("https://image.tmdb.org/t/p/w500/" + it.poster_path).build(),
                        placeholder = painterResource(R.drawable.place_holder),
                        error = painterResource(R.drawable.place_holder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(160.dp, 231.dp)
                            .clip(RoundedCornerShape(16.dp))

                    )

                    LaunchedEffect(movie) {
                        ss = false
                        delay(1500)
                        ss = true
                    }

                    if (true) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            CircularProgress(
                                primaryColor = Color.Red,
                                secondaryColor = Color.Green,
                                initialValue = movie.vote_average.toFloat()
                            )
                            Text(
                                text = "Overview",
                                color = Color.White,
                                fontSize = 22.sp,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.overview, fontSize = 14.sp, maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White
                            )
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
            movie?.let {
                Text(text = "Today's movie is", color = Color.White)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = movie.title, color = Color.White, fontSize = 26.sp)
                Spacer(modifier = Modifier.height(36.dp))
            }

            Button(
                onClick = {
                    viewModel.getRandomMovies()
                }
            ) {
                Text(text = "Picker another")
            }
        }


    }
}

@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    initialValue: Float,
    primaryColor: Color,
    secondaryColor: Color,
) {

    val animateValue by animateFloatAsState(
        targetValue = initialValue,
        animationSpec = tween(5000, easing = LinearOutSlowInEasing)
    )


    Box {
        Canvas(
            modifier = Modifier
                .size(90f.dp)
        ) {
            // background effect
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.45f),
                        secondaryColor.copy(0.15f)
                    )
                ),
                radius = 70f,
                center = center
            )
            val percentage = (animateValue / 10.0f) * 100f

            // the progress
            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = percentage * 3.6f,
                style = Stroke(
                    width = 7f,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width =140f,
                    height = 140f
                ),

                topLeft = Offset( (size.width - 140f) / 2f,
                    (size.height - 140f) / 2f)
            )


            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "$animateValue %",
                        center.x,
                        center.y + 16.dp.toPx() / 3f,
                        Paint().apply {
                            textSize = 16.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = Color.White.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }

        }
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