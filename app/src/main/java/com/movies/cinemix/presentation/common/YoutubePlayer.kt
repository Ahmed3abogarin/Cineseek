package com.movies.cinemix.presentation.common

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
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

    // Animate overlay alpha
    val backgroundAlpha by animateFloatAsState(
        targetValue = if (isFullscreen) 1f else 0.5f,
        animationSpec = tween(400),
        label = "backgroundAlpha"
    )

    val youTubePlayerView = remember(videoId) {
        val options = IFramePlayerOptions.Builder(context)
            .controls(0)
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
            youTubePlayerView.release()
        }
    }

    if (isFullscreen) {
        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        BackHandler(onBack = onBackPress)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = backgroundAlpha))
            .clickable { if (!isFullscreen) onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { youTubePlayerView },
            modifier =  Modifier
                .then(if (isFullscreen) Modifier.fillMaxHeight() else Modifier.clip(RoundedCornerShape(14.dp)).fillMaxWidth().padding(18.dp))
                .aspectRatio(16f / 9f)
                .align(Alignment.Center),
            update = { view ->
                view.layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        )
    }
}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    val activity = context as? Activity

    DisposableEffect(orientation) {
        val originalOrientation = activity?.requestedOrientation
        activity?.requestedOrientation = orientation

        onDispose {
            originalOrientation?.let {
                activity.requestedOrientation = it
            }
        }
    }
}