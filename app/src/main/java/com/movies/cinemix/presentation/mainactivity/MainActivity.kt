package com.movies.cinemix.presentation.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.movies.cinemix.presentation.navGraph.NavGraph
import com.movies.cinemix.ui.theme.CinemixTheme
import com.movies.cinemix.ui.theme.MyColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            viewmodel.splashCondition
        }


        enableEdgeToEdge()
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//        )
        setContent {
            CinemixTheme {
                val startDestination = viewmodel.startDestination
                Box(modifier = Modifier.background(MyColor)) {
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}



