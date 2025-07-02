package com.movies.cinemix.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.movies.cinemix.R
import com.movies.cinemix.ui.theme.CinemixTheme
import com.movies.cinemix.ui.theme.MyRed

@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_5))
    val progress by animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.BottomCenter
    ) {

        LottieAnimation(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            composition = composition.value,
            progress = { progress }

        )

        Column(
            modifier = Modifier
                .padding(bottom = 80.dp)
                .padding(horizontal = 40.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.cineseek2),
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .height(62.dp)
                    .align(Alignment.CenterHorizontally)
            )


            Text(
                text = "Dive into the world of movies, save your favorites, Uncover cast details, behind-the-scenes stories, and everything you love, all in one place",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp), onClick = {
                        event(OnBoardingEvent.SaveAppEntry)
                },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MyRed)
            ) {
                Text(text = "Get Started", fontSize = 18.sp)
            }

        }


    }
}

@Preview
@Composable
fun OnPReview(){
    CinemixTheme {
        OnBoardingScreen(event = {})
    }
}

