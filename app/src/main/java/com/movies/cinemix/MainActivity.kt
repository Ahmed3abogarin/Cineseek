package com.movies.cinemix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.movies.cinemix.presentation.home.HomeScreen
import com.movies.cinemix.ui.theme.CinemixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinemixTheme {
                Box(contentAlignment = Alignment.Center) {
//                    ViewPagerSlider2()
                    //YouTubeDialog("WTs49l7IGg0", LocalLifecycleOwner.current)
                    HomeScreen("WTs49l7IGg0", LocalLifecycleOwner.current)
                }
            }
        }
    }
}



