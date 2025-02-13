package com.movies.cinemix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.movies.cinemix.presentation.news_navigator.MoviesBottomNav
import com.movies.cinemix.ui.theme.CinemixTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinemixTheme {
//                Surface {
////                    ViewPagerSlider2()
//                    //YouTubeDialog("WTs49l7IGg0", LocalLifecycleOwner.current)
//                    HomeScreen("WTs49l7IGg0", LocalLifecycleOwner.current)
//                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    Scaffold(bottomBar = {
//                        BottomBar()
                    }) { paddings ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = paddings.calculateTopPadding()
                                ), contentAlignment = Alignment.Center
                        ) {
                            MoviesBottomNav()
                        }
                    }

                }
            }
        }
    }
}



