package com.ps.translatepro.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import com.ps.translatepro.android.core.theme.DarkSurfaceEnd
import com.ps.translatepro.android.core.theme.DarkSurfaceStart

fun Modifier.gradientSurface(): Modifier = composed {
    if(isSystemInDarkTheme()){
        Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    DarkSurfaceStart,
                    DarkSurfaceEnd
                )
            )
        )
    } else {
        Modifier.background(MaterialTheme.colors.surface)
    }
}