package com.movies.cinemix.presentation.random

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movies.cinemix.R
import com.movies.cinemix.ui.theme.CinemixTheme
import com.movies.cinemix.ui.theme.MyColor

@Composable
fun RandomMovieScreen(navigateToMoviePicker: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MyColor),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Don’t know what to watch?",
                fontSize = 22.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))

            Image(
                modifier = Modifier.size(300.dp),
                painter = painterResource(R.drawable.random_ill),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(56.dp))

            Button(
                onClick = { navigateToMoviePicker() },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFE09B2D))
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 4.dp),
                    text = "Pick a Movie",
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun MyRandomPreview() {
    CinemixTheme {
        RandomMovieScreen(navigateToMoviePicker = {})
    }
}