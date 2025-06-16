package com.movies.cinemix.presentation.common

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoutubePlayer(
    videoId: String,
    currentSecond: Float,
    onDismiss: () -> Unit,
    updateSecond: (Float) -> Unit,
    navigateToFull: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    BasicAlertDialog(
        onDismissRequest = { onDismiss() },
        modifier = Modifier.height(250.dp)
    ) {
        Box {
            AndroidView(
                modifier = Modifier.padding(8.dp),
                factory = { ctx ->
                    val youTubePlayerView = YouTubePlayerView(ctx).apply {
                        enableAutomaticInitialization = false // Set to false if using custom listener
                        val options = IFramePlayerOptions.Builder()
                            .controls(0) // Hide default controls
                            .rel(0)
                            .build()

                        initialize(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                val customUiController =
                                    DefaultPlayerUiController(this@apply, youTubePlayer)
                                customUiController.showFullscreenButton(true) // Optional
                                customUiController.setFullscreenButtonClickListener{
                                    navigateToFull()
                                }
                                setCustomPlayerUi(customUiController.rootView)
                                youTubePlayer.loadVideo(videoId, currentSecond)
                            }


                            override fun onCurrentSecond(
                                youTubePlayer: YouTubePlayer,
                                second: Float
                            ) {
                                super.onCurrentSecond(youTubePlayer, second)
                                updateSecond(second)

                            }


                        }, options)
                    }

                    // Add lifecycle observer after view is created
                    lifecycleOwner.lifecycle.addObserver(youTubePlayerView)

                    youTubePlayerView
                }
            )
        }
    }
}

@Composable
fun FullscreenYoutubePlayer(
    videoId: String,
    currentSecond: Float,
    onBackPress: () -> Unit
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    BackHandler { onBackPress() }
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = { ctx ->
            val youTubePlayerView = YouTubePlayerView(ctx).apply {
                val options = IFramePlayerOptions.Builder().controls(1).build()
                enableAutomaticInitialization = false

                lifecycleOwner.lifecycle.addObserver(this)

                initialize(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, currentSecond)
                    }
                }, options)

            }
            youTubePlayerView
        },
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(16f/9f)
    )
}


@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    val activity = context as? Activity

    DisposableEffect(Unit) {
        val originalOrientation = activity?.requestedOrientation
        activity?.requestedOrientation = orientation

        onDispose {
            // Restore original orientation when composable is removed
            originalOrientation?.let {
                activity.requestedOrientation = it
            }
        }
    }
}