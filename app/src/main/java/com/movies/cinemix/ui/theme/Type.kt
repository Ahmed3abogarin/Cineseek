package com.movies.cinemix.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.movies.cinemix.R


val Nuntio = FontFamily(
    Font(R.font.nunito, FontWeight.Normal),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
)

val Poppins = FontFamily(
    Font(R.font.poppins, FontWeight.Normal),
    Font(R.font.poppins_light, FontWeight.Light),
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleSmall = TextStyle(
        fontFamily = Nuntio,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        color = Color.White,
    ) ,
    titleMedium = TextStyle(
        fontFamily = Nuntio,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = Color.White
    ) ,
    headlineSmall = TextStyle(
        fontFamily = Nuntio,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Color.White,
    ),
    headlineMedium = TextStyle(
        fontFamily = Nuntio,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = Color.White
    ),
    headlineLarge = TextStyle(
        fontFamily = Nuntio,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        color = Color.White
    ),
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        color = Color.White,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Light,
        color = Color.White,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins,
        fontSize = 12.sp,
        color = Color.White,
        fontWeight = FontWeight.Light
    )
)