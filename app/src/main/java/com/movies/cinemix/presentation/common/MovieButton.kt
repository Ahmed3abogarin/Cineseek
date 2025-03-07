package com.movies.cinemix.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.movies.cinemix.R
import com.movies.cinemix.ui.theme.MyRed

@Composable
fun MovieButton(onClick: () -> Unit){
    Button(
        modifier = Modifier.padding(start = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MyRed),
        onClick = onClick
    ) {
        Text(
            text = "Watch Trailer",
            modifier = Modifier.padding( start = 8.dp,end = 8.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_p),
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )

    }
}

//Button(
//onClick = {
//    showDialog = true
//},
//colors = ButtonDefaults.buttonColors(
//containerColor = MyRed
//)
//) {
//    Text(
//        text = "Watch Trailer",
//        modifier = Modifier.padding(
//            start = 12.dp,
//            end = 12.dp,
//            bottom = 4.dp,
//            top = 4.dp
//        )
//    )
//}

@Preview
@Composable
fun ButtonPreview(){
    MovieButton(onClick = {})
}