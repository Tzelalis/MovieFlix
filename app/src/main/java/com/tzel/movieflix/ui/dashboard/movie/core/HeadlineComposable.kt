package com.tzel.movieflix.ui.dashboard.movie.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_2dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.modifier.clickableWithLifecycle

@Composable
fun Headline(
    isBackEnabled: Boolean = true,
    title: TextBuilder? = null,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.surface,
    onBackClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(vertical = Spacing_4dp, horizontal = Spacing_8dp)
            .height(IntrinsicSize.Max),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing_16dp)
    ) {
        if (isBackEnabled) {
            Icon(
                modifier = Modifier
                    .height(Sizes.Icons.medium)
                    .clip(CircleShape)
                    .clickableWithLifecycle { onBackClick() }
                    .padding(Spacing_2dp),
                painter = painterResource(R.drawable.ic_baseline_arrow_back),
                tint = contentColor,
                contentDescription = stringResource(R.string.back_button_content_description)
            )
        }

        title?.let {
            Text(
                text = it.build(),
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}