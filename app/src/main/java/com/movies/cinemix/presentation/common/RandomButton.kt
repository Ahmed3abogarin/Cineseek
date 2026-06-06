package com.movies.cinemix.presentation.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RandomButton(modifier: Modifier = Modifier,text: String,icon: ImageVector? = null,enabled: Boolean = true,onclick: () -> Unit){
    Button(
        onClick = {
            onclick()
        },
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFE09B2D))
    ) {

        icon?.let {
            Icon(
                modifier = modifier,
                imageVector = icon,
                contentDescription = "",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 4.dp),
            text = text,
            fontSize = 22.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }

}