package com.tzel.movieflix.ui.movie.home.model

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_8dp

sealed class FilterUiItem(
    val id: Int,
    val clearSelection: () -> Unit
) {
    @Composable
    abstract fun Build(
        modifier: Modifier = Modifier,
        onFilterClick: () -> Unit
    )

    abstract val isSelected: Boolean

    data class Category(
        val items: List<FilterCategoryItem>,
        val selectedItem: MutableState<FilterCategoryItem?> = mutableStateOf(null)
    ) : FilterUiItem(
        id = 0,
        clearSelection = { selectedItem.value = null },
    ) {
        override val isSelected: Boolean
            get() = selectedItem.value != null

        @Composable
        override fun Build(
            modifier: Modifier,
            onFilterClick: () -> Unit
        ) {
            val color = MaterialTheme.colorScheme.surface

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(width = 1.dp, color = color, shape = CircleShape)
                    .clickable { onFilterClick() }
                    .padding(horizontal = Spacing_16dp, vertical = Spacing_8dp)
                    .drawBehind {
                        if (isSelected) {
                            drawRect(color = color.copy(0.5f))
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing_8dp),
            ) {
                AnimatedContent(
                    targetState = selectedItem.value?.title?.build() ?: TextBuilder.StringResource(R.string.home_filter_category_title).build(),
                    label = "Filter Category Title"
                ) { title ->
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Icon(
                    modifier = Modifier
                        .size(Sizes.Icons.extraSmall)
                        .rotate(-90f),
                    painter = painterResource(id = R.drawable.ic_arrow_back_24),
                    contentDescription = null,
                    tint = color
                )
            }
        }
    }
}

data class FilterCategoryItem(
    val id: Int,
    val title: TextBuilder
)