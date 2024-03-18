package com.tzel.movieflix.ui.core.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_8dp

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(bottom = Spacing_32dp)
                .fillMaxWidth(0.4f)
                .aspectRatio(1f),
            painter = rememberAsyncImagePainter(model = R.drawable.ic_face_sad_tear),
            contentDescription = stringResource(id = R.string.generic_error_try_again),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(bottom = Spacing_8dp),
            text = stringResource(id = R.string.generic_error),
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .border(width = 1.dp, color = Color.White, shape = MaterialTheme.shapes.medium)
                .clickable { onRetry() }
                .padding(horizontal = Spacing_16dp, vertical = Spacing_8dp),
            text = stringResource(id = R.string.generic_error_try_again),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}