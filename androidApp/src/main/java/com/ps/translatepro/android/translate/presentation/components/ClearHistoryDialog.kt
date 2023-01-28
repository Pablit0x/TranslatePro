package com.ps.translatepro.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ps.translatepro.android.R

@Composable
fun ClearHistoryDialog(
    closeDialog: () -> Unit,
    clearHistory: () -> Unit
) {
    AlertDialog(
        onDismissRequest = closeDialog,
        title = {
            Text(
                text = stringResource(id = R.string.clear_history_title)
            )
        },
        text = {
            Column {
                Text(
                    text = stringResource(id = R.string.clear_history_description),
                    fontSize = 16.sp
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    clearHistory()
                    closeDialog()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.clear),
                    color = MaterialTheme.colors.onSurface
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = closeDialog
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colors.onSurface
                )
            }
        },

        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()

    )
}