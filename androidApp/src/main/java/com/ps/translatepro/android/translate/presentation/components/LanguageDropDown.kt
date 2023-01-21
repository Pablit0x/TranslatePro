package com.ps.translatepro.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ps.translatepro.core.presentation.UiLanguage

@Composable
fun LanguageDropDown(
    language: UiLanguage,
    isOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectLanguage: (UiLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .width(160.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(MaterialTheme.colors.secondary)
        .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = CenterVertically) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = language.language.langName,
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSecondary,
                textAlign = TextAlign.Center
            )
        }
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = onDismiss,
            modifier = Modifier.fillMaxHeight(0.6f),
            offset = DpOffset(x = 0.dp, y = 20.dp)
        ) {
            UiLanguage.allLanguages.forEach { language ->
                LanguageDropDownItem(
                    language = language,
                    onClick = {
                        onSelectLanguage(language)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}