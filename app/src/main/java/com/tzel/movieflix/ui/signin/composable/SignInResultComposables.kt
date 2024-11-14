package com.tzel.movieflix.ui.signin.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.MVButton
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp


@Composable
fun SignInResultContent(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    subtitle: String,
    buttonText: String,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(Spacing_32dp),
        verticalArrangement = Arrangement.spacedBy(Spacing_16dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.sign_in_title),
            style = MaterialTheme.typography.titleLarge
        )
        Icon(
            modifier = Modifier.size(Sizes.Icons.extraLarge),
            painter = painterResource(id = iconRes),
            contentDescription = stringResource(id = R.string.generic_error),
            tint = iconTint
        )
        Text(
            modifier = Modifier,
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        MVButton(
            modifier = Modifier.fillMaxWidth(),
            text = buttonText,
            onClick = onButtonClick
        )
    }
}