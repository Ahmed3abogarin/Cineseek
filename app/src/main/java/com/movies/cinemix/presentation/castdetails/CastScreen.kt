package com.movies.cinemix.presentation.castdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.R
import com.movies.cinemix.presentation.common.BackArrow
import com.movies.cinemix.presentation.common.MovieCard
import com.movies.cinemix.presentation.common.parallaxLayoutModifier
import com.movies.cinemix.ui.theme.BorderColor
import com.movies.cinemix.ui.theme.BoxColor
import com.movies.cinemix.ui.theme.MyPurple
import kotlinx.coroutines.delay

@Composable
fun CastScreen(
    personId: Int,
    event: (CastEvent) -> Unit,
    state: CastState,
    navigateUp: () -> Unit,
    navigateToDetails: (Int) -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(false) {
        event(CastEvent.UpdatePersonId(personId = personId))
        delay(300)
        visible = true
    }

    if (state.personInfo != null) {
        val person = state.personInfo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFF0C1428))
                .statusBarsPadding()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = visible,
                enter =
                    slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                        // Offsets the content by 1/3 of its width to the left, and slide towards right
                        // Overwrites the default animation with tween for this slide animation.
                        -fullWidth / 3
                    } + fadeIn(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 200)
                    ),
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    BackArrow (Modifier.padding(top = 16.dp)){ navigateUp() }
                    Column(
                        modifier = Modifier
                            .padding(top = 38.dp)
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data("https://image.tmdb.org/t/p/w500/" + person.profile_path)
                                .placeholder(R.drawable.place_holder)
                                .error(R.drawable.place_holder)
                                .build(),
                            modifier = Modifier
                                .size(150.dp, 200.dp)
                                .shadow(
                                    elevation = 15.dp,
                                    spotColor = Color.White,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clip(RoundedCornerShape(14.dp))
                                .parallaxLayoutModifier(scrollState = scrollState, rate = 2),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = person.name,
                            style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                        )
                    }


                }
            }





            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(
                visible = visible,
                enter =
                    slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                        // Offsets the content by 1/3 of its width to the left, and slide towards right
                        // Overwrites the default animation with tween for this slide animation.
                        -fullWidth / 3
                    } + fadeIn(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 200)
                    ),
            ) {
                person.also_known_as?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .border(
                                width = 0.5.dp,
                                color = BorderColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(BoxColor, shape = RoundedCornerShape(12.dp))
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp, start = 12.dp),
                            text = "Also known as",
                            color = MyPurple,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            modifier = Modifier.padding(
                                top = 3.dp,
                                start = 12.dp,
                                end = 12.dp,
                                bottom = 10.dp
                            ),
                            text = person.also_known_as.joinToString(", "),
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }

            }



            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = visible,
                enter =
                    slideInHorizontally(animationSpec = tween(durationMillis = 400)) { fullWidth ->
                        // Offsets the content by 1/3 of its width to the left, and slide towards right
                        // Overwrites the default animation with tween for this slide animation.
                        -fullWidth / 3
                    } + fadeIn(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 400)
                    ),
            ) {
                person.biography?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .border(
                                width = 0.5.dp,
                                color = BorderColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(BoxColor, shape = RoundedCornerShape(12.dp))
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp, start = 12.dp),
                            text = "About",
                            color = MyPurple,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        ExpandableText(it)
                    }

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = visible,
                enter =
                    slideInHorizontally(animationSpec = tween(durationMillis = 600)) { fullWidth ->
                        // Offsets the content by 1/3 of its width to the left, and slide towards right
                        // Overwrites the default animation with tween for this slide animation.
                        -fullWidth / 3
                    } + fadeIn(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 600)
                    ),
            ) {
                person.birthday?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .border(
                                width = 0.5.dp,
                                color = BorderColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(BoxColor, shape = RoundedCornerShape(12.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(top = 10.dp, start = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Born: ",
                                color = MyPurple,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(text = it, color = Color.White, fontSize = 14.sp)
                        }
                        Text(
                            modifier = Modifier.padding(
                                top = 3.dp,
                                start = 12.dp,
                                end = 12.dp,
                                bottom = 10.dp
                            ),
                            text = person.place_of_birth,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            AnimatedVisibility(
                visible = visible,
                enter =
                    slideInHorizontally(animationSpec = tween(durationMillis = 800)) { fullWidth ->
                        // Offsets the content by 1/3 of its width to the left, and slide towards right
                        // Overwrites the default animation with tween for this slide animation.
                        -fullWidth / 3
                    } + fadeIn(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis =800)
                    ),
            ) {
                state.personMovies?.let {
                    val personMovies = state.personMovies.collectAsLazyPagingItems()

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            color = MyPurple,
                            text = "Movies",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow {
                            items(personMovies.itemCount) { index ->
                                personMovies[index]?.let { movie ->
                                    MovieCard(movie) { navigateToDetails(movie.id) }
                                }
                            }
                        }

                    }


                }
            }
            Spacer(modifier = Modifier.navigationBarsPadding().height(8.dp))
        }
    }
}


@Composable
fun ExpandableText(
    text: String,
    maxLines: Int = 11, // Limit before expanding
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(top = 3.dp, start = 12.dp, end = 12.dp, bottom = 10.dp)) {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
            fontSize = 12.sp,
            lineHeight = 22.sp,
            modifier = Modifier.animateContentSize()
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (Int.MAX_VALUE > maxLines) {
            Text(
                text = if (isExpanded) "Show Less" else "Show More",
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .padding(4.dp)
            )
        }
    }
}