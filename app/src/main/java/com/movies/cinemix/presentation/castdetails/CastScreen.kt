package com.movies.cinemix.presentation.castdetails

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.common.BackArrow
import com.movies.cinemix.presentation.common.MovieCard
import com.movies.cinemix.ui.theme.BottomColor
import com.movies.cinemix.ui.theme.MyColor

@Composable
fun CastScreen(
    personId: Int,
    event: (CastEvent) -> Unit,
    state: CastState,
    navigateUp: () -> Unit,
    navigateToDetails: (Movies) -> Unit,
) {

    val context = LocalContext.current

    LaunchedEffect(false) {
        event(CastEvent.UpdatePersonId(personId = personId))
    }


    if (state.personInfo != null) {
        val person = state.personInfo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BottomColor)
                .verticalScroll(
                    rememberScrollState()
                ),
        ) {
            Box(
                modifier = Modifier
                    .height(308.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                    .background(MyColor)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .width(192.dp)
                        .height(220.dp)
                        .align(Alignment.Center),
                    model = ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/w500/" + person.profile_path)
                        .placeholder(R.drawable.place_holder)
                        .error(R.drawable.place_holder)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 2.dp),
                    text = state.personInfo.name,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = Color.White,
                        fontSize = 24.sp
                    )
                )


                BackArrow(navigateUp = navigateUp, modifier = Modifier.align(Alignment.TopStart))


            }
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {

                person.also_known_as?.let {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Also known as:",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = person.also_known_as.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                    )
                }

                person.biography?.let {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "About",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    ExpandableText(text = person.biography)
                }

                person.birthday?.let {
                    Text(
                        text = "Born: ${person.birthday} (${person.place_of_birth})",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                    )
                }



                Spacer(modifier = Modifier.height(10.dp))

                state.personMovies?.let {
                    Text(
                        text = "Movies by ${person.name}",
                        style = MaterialTheme.typography.displaySmall.copy(
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    )

                    val personMovies = state.personMovies.collectAsLazyPagingItems()

                    LazyRow {
                        items(personMovies.itemCount) { page ->
                            personMovies[page]?.let { movie ->
                                Column {
                                    MovieCard(movie, onClick = { navigateToDetails(movie) })

                                }
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }
    }
}

@Composable
fun ExpandableText(
    text: String,
    maxLines: Int = 11, // Limit before expanding
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
            lineHeight = 22.sp,
            modifier = Modifier.animateContentSize() // Smooth expand animation
        )

        Spacer(modifier = Modifier.height(4.dp))


        if (Int.MAX_VALUE > maxLines) {
            Text(
                text = if (isExpanded) "Show Less" else "Show More",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .padding(4.dp)
            )
        }

    }
}