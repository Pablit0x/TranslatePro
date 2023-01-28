package com.ps.translatepro.android.translate.presentation.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ps.translatepro.android.R
import com.ps.translatepro.android.core.theme.LightBlue
import com.ps.translatepro.translate.presentation.UiHistoryItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TranslateHistoryItem(
    historyItem: UiHistoryItem,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showOptions by remember{ mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(visible = showOptions) {
            Row() {
                IconButton(onClick = {
                    onDelete()
                    showOptions = false
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = stringResource(id = R.string.delete),
                        tint = LightBlue,
                    )
                }

                IconButton(onClick = { showOptions = false }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.delete),
                        tint = LightBlue,
                    )
                }
            }
        }
        Row(modifier = modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .gradientSurface()
                    .combinedClickable(
                        onLongClick = {showOptions = true} ,
                        onClick = onClick
                    )
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallLanguageIcon(language = historyItem.fromLanguage)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = historyItem.fromText,
                        color = LightBlue,
                        style = MaterialTheme.typography.body2
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallLanguageIcon(language = historyItem.toLanguage)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = historyItem.toText,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}