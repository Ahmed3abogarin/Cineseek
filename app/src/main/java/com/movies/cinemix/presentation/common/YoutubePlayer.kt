package com.movies.cinemix.presentation.common

import android.content.res.Configuration
import android.view.View
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoutubePlayerO(
    videoId: String,
    lifecycleOwner: LifecycleOwner,
    onDismiss: () -> Unit,
    navigateToFull: () -> Unit
) {
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
                    youTubePlayerView.addFullscreenListener(object : FullscreenListener{
                        override fun onEnterFullscreen(
                            fullscreenView: View,
                            exitFullscreen: () -> Unit,
                        ) {

                        }

                        override fun onExitFullscreen() {

                        }

                    })

                    // Add lifecycle observer after view is created
                    lifecycleOwner.lifecycle.addObserver(youTubePlayerView)

                    youTubePlayerView
                }
            )
            IconButton(modifier = Modifier.align(Alignment.BottomEnd), onClick = {navigateToFull()}) {
                Icon(Icons.Default.Star, contentDescription = "", tint = Color.White)
            }
        }
    }
}
@Composable
fun YoutubePlayer(videoId: String, onBack: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var playbackPosition by rememberSaveable { mutableFloatStateOf(0f) }

    val configuration = LocalConfiguration.current

    val orientation = remember { mutableIntStateOf(configuration.orientation) }

    LaunchedEffect(configuration) {
        orientation.intValue = configuration.orientation
    }

    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val window = (LocalView.current.context as ComponentActivity).window
    SideEffect {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            WindowCompat.getInsetsController(window, window.decorView).apply {
                show(WindowInsetsCompat.Type.statusBars())
                show(WindowInsetsCompat.Type.navigationBars())
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = Modifier
                .then(if (isLandscape) Modifier.fillMaxHeight() else Modifier.fillMaxWidth())
                .aspectRatio(16f / 9f)
                .align(Alignment.Center),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)

                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoId, playbackPosition)
                        }

                        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                            playbackPosition = second
                        }
                    })
                }
            }
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
                .background(Color.White.copy(alpha = 0.1f), shape = CircleShape)
                .clickable { onBack() }
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}