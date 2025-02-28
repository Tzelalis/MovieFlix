package com.tzel.movieflix.ui.moviedetail.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.moviedetail.model.ReviewUi
import com.tzel.movieflix.ui.theme.BlueMedium
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.SpacingCustom_12dp
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.ext.openUrlInBrowser


@Composable
fun ReviewHeader(
    onSeeMoreClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MovieDetailsHeader(
            modifier = Modifier.weight(1f),
            header = stringResource(id = R.string.home_details_reviews_title)
        )
        Text(
            modifier = Modifier
                .padding(start = SpacingCustom_12dp, end = SpacingCustom_12dp, top = Spacing_32dp, bottom = Spacing_8dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable { onSeeMoreClick() }
                .padding(horizontal = Spacing_4dp, vertical = Spacing_8dp),
            text = stringResource(id = R.string.home_details_reviews_see_more),
            style = MaterialTheme.typography.labelMedium,
            color = BlueMedium,
        )
    }
}

@Composable
fun ReviewsLazyRow(reviews: List<ReviewUi>) {
    val state = rememberPagerState(pageCount = { reviews.size })
    HorizontalPager(
        state = state,
        contentPadding = PaddingValues(horizontal = Spacing_16dp),
        pageSpacing = Spacing_16dp
    ) { index ->
        ReviewItem(
            modifier = Modifier.fillMaxWidth(),
            review = reviews[index]
        )
    }
}

@Composable
private fun ReviewItem(
    review: ReviewUi,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .aspectRatio(2f)
            .clip(MaterialTheme.shapes.medium)
            .border(1.dp, MaterialTheme.colorScheme.onSurface, MaterialTheme.shapes.medium)
            .clickable(review.isClickable) { review.url?.let { context.openUrlInBrowser(url = review.url, review.author) } }
            .padding(Spacing_8dp),
        verticalArrangement = Arrangement.spacedBy(Spacing_4dp),
    ) {
        Text(
            text = review.author,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = review.content,
            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colorScheme.onTertiary,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewHeaderPreview() {
    MovieFlixTheme {
        ReviewHeader(onSeeMoreClick = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun ReviewLazyRowPreview() {
    val dummyParagraph =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    val reviews = listOf(
        ReviewUi(
            id = "1",
            key = "1",
            author = "Author 1",
            content = dummyParagraph,
            url = "https://www.google.com"
        ),
        ReviewUi(
            id = "2",
            key = "2",
            author = "Author 2",
            content = dummyParagraph,
            url = "https://www.google.com"
        ),
    )

    MovieFlixTheme {
        ReviewsLazyRow(reviews = reviews)
    }
}