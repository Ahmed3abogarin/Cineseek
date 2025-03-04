package com.movies.cinemix.presentation.mainactivity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.movies.cinemix.presentation.onboarding.LogoScreen
import com.movies.cinemix.ui.theme.CinemixTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//        )
        setContent {
            CinemixTheme {
//                Box(modifier = Modifier.background(MyColor)) {
//                    NavGraph()
//                }

//                OnBoardingScreen()
                LogoScreen()


            }
        }
    }
}



