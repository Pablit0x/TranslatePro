package com.ps.translatepro.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ps.translatepro.android.R

const val SWAP_LANGUAGES_BUTTON_TAG = "SwapLanguagesButton"


@Composable
fun SwapLanguagesButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.testTag(SWAP_LANGUAGES_BUTTON_TAG)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.swap_languages),
            contentDescription = stringResource(id = R.string.swap_languages),
            Modifier
                .size(16.dp)
        )
    }
}