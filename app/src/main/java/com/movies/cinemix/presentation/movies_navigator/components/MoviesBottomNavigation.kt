package com.movies.cinemix.presentation.movies_navigator.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movies.cinemix.R
import com.movies.cinemix.ui.theme.BottomColor
import com.movies.cinemix.ui.theme.BottomIconColor
import com.movies.cinemix.ui.theme.CinemixTheme
import com.movies.cinemix.ui.theme.MyGray2
import com.movies.cinemix.ui.theme.MyGreen
import com.movies.cinemix.ui.theme.MyPink
import kotlinx.coroutines.delay


@Composable
fun MoviesBottomNav(
    bottomItems: MutableList<BottomItem>,
    selectedIndex: Int,
    onItemClicked: (Int) -> Unit,
) {
    val configuration = LocalConfiguration.current

    val indicatorWidth = (configuration.screenWidthDp / bottomItems.count()) / 2


    val indicatorOffset by animateIntOffsetAsState(
        targetValue = IntOffset(
            bottomItems[selectedIndex].offset.x.toInt() + (bottomItems[selectedIndex].size.width / 4) - (bottomItems.count() * 2) ,
            15
        ),
        animationSpec = tween(400)
    )
    val indicatorColor by animateColorAsState(
        targetValue = bottomItems[selectedIndex].color,
        animationSpec = tween(500)
    )
    val infiniteTransition = rememberInfiniteTransition()
    val indicatorFlashingColor by infiniteTransition.animateFloat(
        initialValue = .7f,
        targetValue = .6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    val switching = remember {
        mutableStateOf(false)
    }

    val bottomIconColor by animateColorAsState(
        targetValue = if (switching.value) Color.White else BottomIconColor,
        animationSpec = tween(durationMillis = 1000) // Customize duration, easing, etc.
    )


    LaunchedEffect(switching.value) {
        if (switching.value) {
            delay(250)
            switching.value = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp)
            .shadow(8.dp, RoundedCornerShape(30.dp))
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(BottomColor)
    ) {

        // The row that contains all the bottom icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            bottomItems.forEachIndexed { index, item ->
                // This the column that been created for each icon
                Column (
                    modifier = Modifier
                        .onGloballyPositioned {
                            val offset = it.positionInParent()
                            bottomItems[index] =
                                bottomItems[index].copy(
                                    offset = offset,
                                    size = it.size
                                )
                        }
                        .weight((1.0 / bottomItems.count()).toFloat())
                        .clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            onClick = {
                                switching.value = true
                                onItemClicked(index)
                            }
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = bottomIconColor
                    )
                    Text(item.title, color = MyGray2, fontSize = 11.sp)
                }
            }
        }
        Column(
            modifier = Modifier
                .offset {
                    indicatorOffset
                },
            verticalArrangement = Arrangement.Center,
        ) {
            // The top line that the light effect inflate from
            Box(
                modifier = Modifier
                    .shadow(
                        2.dp,
                        CircleShape,
                        ambientColor = indicatorColor,
                        spotColor = indicatorColor
                    )
                    .height(3.dp)
                    .width(indicatorWidth.dp)
                    .clip(CircleShape)
                    .background(indicatorColor)
            )
            AnimatedVisibility(
                visible = !switching.value,
                enter = expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(500)),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(500)),
            )  {
                // The light effect
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .drawBehind {
                            val path = Path()

                            val beamTopWidth = size.width * 0.8f   // Narrow top
                            val beamBottomWidth = size.width * 2f // Wide bottom spread
                            val beamHeight = size.height * 2.0f     // Tall beam

                            val centerX = size.width / 2f
                            val topLeftX = centerX - beamTopWidth / 2f
                            val topRightX = centerX + beamTopWidth / 2f
                            val bottomLeftX = centerX - beamBottomWidth / 2f
                            val bottomRightX = centerX + beamBottomWidth / 2f

                            path.moveTo(topLeftX, 0f)
                            path.lineTo(topRightX, 0f)
                            path.lineTo(bottomRightX, beamHeight)
                            path.lineTo(bottomLeftX, beamHeight)
                            path.close()

                            drawPath(
                                path = path,
                                brush = Brush.verticalGradient(
                                    listOf(
                                        indicatorColor.copy(
                                            alpha = indicatorFlashingColor - .2f
                                        ),
                                        indicatorColor.copy(
                                            alpha = indicatorFlashingColor - .4f
                                        ),
                                        Color.Transparent
                                    )
                                )
                            )
                        }
                )
            }
        }
    }
}

@Preview(device = Devices.PIXEL_7_PRO, showBackground = true)
@Composable
fun BottomPreview() {
    val icRandom = ImageVector.vectorResource(R.drawable.ic_random)

    val bottomItems = remember {
        mutableStateListOf(
            BottomItem(
                icon = Icons.Rounded.Home,
                title = "Home",
                color = Color(0xFFFF0000)
            ),
            BottomItem(
                icon = icRandom,
                title = "Random",
                color = Color(0xFFFF0000)
            ),
            BottomItem(
                icon = Icons.Rounded.Search,
                title = "Search",
                color = MyGreen
            ),
            BottomItem(
                icon = Icons.Rounded.Favorite,
                title = "Favorite",
                color = MyPink
            )
        )
    }
    CinemixTheme {
        MoviesBottomNav(onItemClicked = {}, bottomItems = bottomItems, selectedIndex = 0)
    }
}


data class BottomItem(
    val icon: ImageVector,
    val color: Color,
    val title: String,
    val offset: Offset = Offset(0f, 0f),
    val size: IntSize = IntSize(10, 10),
)