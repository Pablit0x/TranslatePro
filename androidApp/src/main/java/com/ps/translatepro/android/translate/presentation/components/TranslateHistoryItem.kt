package com.ps.translatepro.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ps.translatepro.android.core.theme.LightBlue
import com.ps.translatepro.translate.presentation.UiHistoryItem

@Composable
fun TranslateHistoryItem(
    historyItem: UiHistoryItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            SmallLanguageIcon(language = historyItem.fromLanguage)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = historyItem.fromText,
                color = LightBlue,
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            SmallLanguageIcon(language = historyItem.toLanguage)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = historyItem.toText,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )
        }
    }
}