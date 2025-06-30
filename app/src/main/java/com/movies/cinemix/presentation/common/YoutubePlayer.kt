package com.movies.cinemix.presentation.common

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun MovieYouTubePlayer(
    videoId: String,
    currentSecond: Float,
    isFullscreen: Boolean,
    onDismiss: () -> Unit,
    onBackPress: () -> Unit,
    updateSecond: (Float) -> Unit,
    toggleFullscreen: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val youTubePlayerView = remember {
        val options = IFramePlayerOptions.Builder()
            .controls(1)
            .rel(0)
            .build()

        YouTubePlayerView(context).apply {
            lifecycleOwner.lifecycle.addObserver(this)
            enableAutomaticInitialization = false
            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val customUiController =
                        DefaultPlayerUiController(this@apply, youTubePlayer)
                    customUiController.showBufferingProgress(false)
                    customUiController.showFullscreenButton(true)
                    customUiController.setFullscreenButtonClickListener {
                        toggleFullscreen()
                    }
                    setCustomPlayerUi(customUiController.rootView)
                    youTubePlayer.loadVideo(videoId, currentSecond)
                }

                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                    updateSecond(second)
                }
            }, options)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(youTubePlayerView)
        }
    }

    if (isFullscreen) {
        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        BackHandler(onBack = onBackPress)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(if(!isFullscreen) Color.Black.copy(alpha = 0.5f) else Color.Black)
            .clickable { if (!isFullscreen) onDismiss()},
        contentAlignment = Alignment.Center
    ) {

        AndroidView(factory = {  youTubePlayerView},
            modifier = Modifier
                .then(if (isFullscreen) Modifier.fillMaxHeight() else Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).padding(18.dp))
                .aspectRatio(16f / 9f)
                .align(Alignment.Center)

        )

    }
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