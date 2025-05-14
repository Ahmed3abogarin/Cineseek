package com.movies.cinemix.presentation.movies_navigator.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.movies.cinemix.ui.theme.BottomColor
import com.movies.cinemix.ui.theme.CinemixTheme
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
            bottomItems[selectedIndex].offset.x.toInt() + (bottomItems[selectedIndex].size.width / 4) - (bottomItems.count() * 2) + (-2),
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomItems.forEachIndexed { index, item ->
                Box(
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
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = Color.White
                    )
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
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .drawBehind {
                            val path = Path()
                            val triangleWidth = size.width * 0.8f //relative triangle width
                            val triangleHeight = size.height * 1.5f //relative triangle height

                            path.moveTo(size.width - 2f, 0f) //relative x move
                            path.lineTo(size.width - triangleWidth, 0f)
                            path.lineTo(size.height - triangleHeight * 0.8f, triangleHeight)
                            path.lineTo(size.height + triangleWidth * 0.5f, triangleHeight)

//                            path.moveTo(size.width + 10, 0f)
//                            path.lineTo(size.width - (size.width - 60), 0f)
//                            path.lineTo(size.height - 150,  195f)
//                            path.lineTo(size.height + 60, 195f)
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
                                ),
                            )
                        }
                )
            }
        }
    }
}

@Preview(device = Devices.PIXEL_7_PRO, showBackground = true)
@Composable
fun BottomPreview(){
    val bottomItems = remember {
        mutableStateListOf(
            BottomItem(
                icon = Icons.Rounded.Home,
                color = Color(0xFFFF0000)
            ),
            BottomItem(
                icon = Icons.Rounded.Search,
                color = MyGreen
            ),
            BottomItem(
                icon = Icons.Rounded.Favorite,
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
    val offset: Offset = Offset(0f, 0f),
    val size: IntSize = IntSize(0, 0),
)