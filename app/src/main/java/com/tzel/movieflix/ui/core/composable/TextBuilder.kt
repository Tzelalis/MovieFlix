package com.tzel.movieflix.ui.core.composable

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource

interface TextBuilder {
    @Composable
    fun build(): String

    fun build(context: Context): String

    data object Empty : TextBuilder {
        @Composable
        override fun build() = ""

        override fun build(context: Context): String = ""
    }

    class Text(val text: String) : TextBuilder {
        @Composable
        override fun build() = text

        override fun build(context: Context): String = text

    }

    class StringResource(@StringRes val res: Int, private vararg val params: Any = emptyArray()) : TextBuilder {
        @Composable
        override fun build(): String {
            return stringResource(res, *params)
        }

        override fun build(context: Context): String {
            return context.getString(res, *params)
        }
    }

    class PluralResource(@PluralsRes val res: Int, val count: Int, private vararg val params: Any = emptyArray()) : TextBuilder {
        @Composable
        override fun build(): String {
            return pluralStringResource(res, count, *params)
        }

        override fun build(context: Context): String {
            return context.resources.getQuantityString(res, count, *params)
        }
    }
}