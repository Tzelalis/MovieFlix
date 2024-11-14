package com.tzel.movieflix.ui.core.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_8dp

@Composable
fun MFTextField(
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    @DrawableRes iconRes: Int? = null,
    textFieldState: TextFieldState,
) {
    val isTextEmpty = remember(textFieldState.text) { derivedStateOf { textFieldState.text.isEmpty() } }

    BasicTextField(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(horizontal = Spacing_16dp, vertical = Spacing_8dp),
        state = textFieldState,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(Color.White),
        decorator = { innerText ->
            Row(
                modifier = Modifier
                    .heightIn(min = Sizes.Icons.medium)
                    .height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing_16dp)
            ) {
                iconRes?.let {
                    Icon(
                        modifier = Modifier.size(Sizes.Icons.medium),
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiary,
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    placeholder?.let {
                        if (isTextEmpty.value) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onTertiary,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    innerText()
                }
                SearchClearIcon(
                    modifier = Modifier.size(Sizes.Icons.medium),
                    isVisible = { isTextEmpty.value.not() },
                    onClick = {
                        textFieldState.clearText()
                    }
                )
            }
        }
    )
}

@Composable
private fun SearchClearIcon(
    isVisible: () -> Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    if (isVisible()) {
        Icon(
            modifier = modifier
                .clip(CircleShape)
                .clickable { onClick() },
            painter = painterResource(R.drawable.ic_round_close),
            tint = MaterialTheme.colorScheme.onTertiary,
            contentDescription = stringResource(R.string.home_clear_content_description),
        )
    }
}