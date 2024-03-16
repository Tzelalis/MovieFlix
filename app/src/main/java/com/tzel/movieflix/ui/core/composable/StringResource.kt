package com.tzel.movieflix.ui.core.composable

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import com.tzel.movieflix.R

@Stable
open class StringResource(
    @StringRes val res: Int,
    vararg val params: Any = emptyArray()
){
    object Empty: StringResource(R.string.empty, arrayOf<String>())

    class Text(text: String): StringResource(R.string.single_param, text)

    @Composable
    fun build(): String{
        return stringResource(id = res, *params)
    }

    fun build(context: Context) = context.getString(res, *params)
}