package com.tzel.movieflix.utils.composable.modifier

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed

@Composable
fun Modifier.clickableWithLifecycle(
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = LocalIndication.current,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onClick: () -> Unit,
): Modifier = this then Modifier.clickable(
    interactionSource = interactionSource,
    indication = indication,
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = dropUnlessResumed(lifecycleOwner = lifecycleOwner) { onClick() }
)

@Composable
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = this then Modifier.clickable(
    enabled = enabled,
    indication = null,
    interactionSource = null,
    onClick = { onClick() }
)

@Composable
fun Modifier.noRippleClickableWithLifecycle(
    enabled: Boolean = true,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onClick: () -> Unit,
): Modifier = this then Modifier.clickable(
    enabled = enabled,
    indication = null,
    interactionSource = null,
    onClick = dropUnlessResumed(lifecycleOwner) { onClick() }
)