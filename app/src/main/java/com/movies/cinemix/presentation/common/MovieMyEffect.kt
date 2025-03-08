package com.movies.cinemix.presentation.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.movies.cinemix.ui.theme.CinemixTheme


fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition()
    val alpha = transition.animateFloat(
        initialValue = 0.2f, targetValue = 0.9f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600),
            repeatMode = RepeatMode.Reverse
        )
    ).value
    this.background(color = Color.LightGray.copy(alpha = alpha)) // shimmer color
}





@Composable
fun MovieMyEffect(
    modifier: Modifier = Modifier
){

        Box(
            modifier = modifier
                .height(231.dp)
                .width(160.dp)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        )



}
@Preview
@Composable
fun EffectPreview(){
    CinemixTheme {
        MovieMyEffect()
    }
}