package com.movies.cinemix.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun FullScreenScreen(videoId: String) {
    val lifecycleOwner = LocalLifecycleOwner.current
    Log.v("TTTOO","Movie Id: $videoId")
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    )
    {

        AndroidView(
            modifier = Modifier.padding(8.dp),
            factory = { ctx ->
                val youTubePlayerView = YouTubePlayerView(ctx).apply {
                    enableAutomaticInitialization = false // Set to false if using custom listener
                    val options = IFramePlayerOptions.Builder()
                        .controls(0) // Hide default controls
                        .build()

                    initialize(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            val customUiController =
                                DefaultPlayerUiController(this@apply, youTubePlayer)
                            customUiController.showFullscreenButton(true) // Optional
                            setCustomPlayerUi(customUiController.rootView)
                            youTubePlayer.loadVideo(videoId, 0f)
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