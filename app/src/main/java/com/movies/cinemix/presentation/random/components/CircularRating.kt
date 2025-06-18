package com.movies.cinemix.presentation.random.components

import android.graphics.Paint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movies.cinemix.ui.theme.Gold
import com.movies.cinemix.ui.theme.MyGreen
import java.util.Locale

@Composable
fun CircularRating(
    initialValue: Float,
    primaryColor: Color = Gold,
    secondaryColor: Color = MyGreen,
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
                    width = 140f,
                    height = 140f
                ),

                topLeft = Offset(
                    (size.width - 140f) / 2f,
                    (size.height - 140f) / 2f
                )
            )


            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "${"%.1f".format(Locale.US, animateValue)} %",
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
