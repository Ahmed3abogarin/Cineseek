package com.movies.cinemix.presentation.random

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.presentation.common.EmptyContent
import com.movies.cinemix.presentation.random.components.CircularRating
import com.movies.cinemix.presentation.random.components.RandomMoviesLoading
import com.movies.cinemix.ui.theme.BorderColor
import com.movies.cinemix.ui.theme.MyColor
import com.movies.cinemix.ui.theme.MyRed
import kotlinx.coroutines.delay

@Composable
fun MoviePickerScreen(
    state: RandomMovieState,
    viewModel: PickerViewModel,
    navigateToDetails: (Int) -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    var rotated by rememberSaveable { mutableStateOf(false) }

    var flipTxt by remember { mutableStateOf("flip the card") }

    val roater by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = 12.dp)
            .background(MyColor),
        contentAlignment = Alignment.Center
    ) {

        val angle = if (state.isLoading) {
            val infiniteTransition = rememberInfiniteTransition(label = "rotation")
            infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "rotationAngle"
            ).value
        } else {
            0f
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 14.dp, top = 44.dp),
            onClick = { navigateUp() }) {
            Icon(modifier = Modifier.size(50.dp), imageVector = Icons.Default.Close, contentDescription = null,tint = Color.LightGray)
        }

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


        state.error?.let {
            var startAnimation by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true) {
                startAnimation = true
            }

            val alphaAnimation by animateFloatAsState(
                targetValue = if (startAnimation) 0.3f else 0f,
                animationSpec = tween(durationMillis = 1500)
            )
            EmptyContent(alphaAnimation,it,R.drawable.ic_network_error)
        }

        AnimatedVisibility(
            visible = state.isLoading ,
            enter = fadeIn(tween(300)) + scaleIn(tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + scaleOut(tween(300))
        ) {
            RandomMoviesLoading()
        }
        state.movie?.let {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + scaleIn(tween(300)),
                exit = fadeOut(animationSpec = tween(300)) + scaleOut(tween(300))
            ) {
                val imageRequest = remember {
                    ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/w500/${it.poster_path}")
                        .build()
                }
                val imagePainter = rememberAsyncImagePainter(model = imageRequest)

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = flipTxt, color = Color.White, fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .graphicsLayer {
                                rotationY = roater
                            }
                            .clickable(enabled = !rotated, onClick = { rotated = !rotated }),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    ) {
                        if (!rotated) {
                            flipTxt = "flip the card"
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
                            flipTxt = ""
                            MoviePoster(
                                imagePainter,
                                it,
                                roater,
                                onClick = { navigateToDetails(it.id) }
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
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 105.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.padding(start = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MyRed),
                enabled = !state.isLoading,
                border = BorderStroke(width = 1.dp, color = BorderColor),
                onClick = {
                    rotated = false
                    viewModel.getRandomMovie()
                }
            ) {
                Icon(
                    modifier = Modifier.rotate(angle),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Pick another",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 4.dp)
                )
            }
        }

    }
}

@Composable
fun MoviePoster(
    image: AsyncImagePainter,
    movie: Movie,
    roater: Float,
    onClick: () -> Unit,
) {
    var showOverview by rememberSaveable { mutableStateOf(false) }


    val targetWidth = if (showOverview) 171.dp else 229.dp
    val targetHeight = if (showOverview) 242.dp else 300.dp

    val scaleW by animateDpAsState(targetValue = targetWidth, animationSpec = tween(500))
    val scaleH by animateDpAsState(targetValue = targetHeight, animationSpec = tween(500))


    LaunchedEffect(roater) {
        if (roater == 180f) {
            showOverview = false
            delay(1500)
            showOverview = true
        }
    }

    Column(
        modifier = Modifier
            .graphicsLayer {
                rotationY = roater
            }
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = tween(
                        500,
                        easing = LinearOutSlowInEasing
                    )
                )
                .padding(horizontal = 8.dp)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(scaleW, scaleH)
                    .clip(RoundedCornerShape(16.dp))
            )
            if (showOverview) {
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.animateContentSize()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = movie.title,
                        color = MyRed,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = movie.release_date, fontSize = 13.sp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(6.dp))
                    CircularRating(initialValue = movie.vote_average.toFloat())
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Language: ${movie.original_language}",
                        fontSize = 13.sp,
                        color = Color.LightGray
                    )
                }
            }
        }

        if (showOverview) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = movie.overview,
                color = Color.White,
                fontSize = 11.sp,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

