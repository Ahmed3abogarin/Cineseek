package com.movies.cinemix.presentation.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.ui.theme.MyRed
import com.movies.cinemix.ui.theme.Purple40
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.abs


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerSlider2(images: List<Int>) {
    val pagerState = rememberPagerState(
        pageCount = { images.size },
        initialPage = 2
    )

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(color = Purple40),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "View pager slide",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(0.dp, 40.dp, 0.dp, 40.dp)
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

            Card(
                modifier = Modifier
                    .graphicsLayer {
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                    }
                    .fillMaxWidth()
                    .padding(25.dp, 0.dp, 25.dp, 0.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                val newImage = images[page]
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter =
                        painterResource(id = newImage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(15.dp)
                    ) {
                        Text(text = "test text")
                    }

                }

            }

        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerSlider(pagesCount: Int, list: LazyPagingItems<Movies>) {
    val pagerState = rememberPagerState(
        pageCount = { pagesCount },
        initialPage = 0
    )
    val context = LocalContext.current
    val colors = listOf(
        Color.Black,
        Color.Black.copy(alpha = .7f),
        Color.Transparent
    ).reversed()



    if (list.itemCount > 0){
        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(2000)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                    animationSpec = tween(600)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(end = 40.dp, start = 40.dp)
            ) { page ->
                val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                val fraction = 1f - abs(pageOffset).coerceIn(0f, 1f)


                    Card(modifier = Modifier.graphicsLayer {
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = fraction
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = fraction
                        )

                    }) {
                        Box{
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data("https://image.tmdb.org/t/p/w500/" + list[page]!!.backdrop_path)
                                    .build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(356.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                contentScale = ContentScale.Crop
                            )
                            Row(
                                modifier = Modifier
                                    .background(brush = Brush.verticalGradient(colors))
                                    .align(Alignment.BottomStart)
                                    .fillMaxWidth()
                                    .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f) // Allows Text to take up available space
                                ) {
                                    Text(
                                        text = list[page]!!.title,
                                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MyRed
                                    )
                                ) {
                                    Text(text = "Watch Now")
                                }
                            }

                        }

                    }






            }


        }
    }

}