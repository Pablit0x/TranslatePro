package com.ps.translatepro.android

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ps.translatepro.android.core.presentation.Routes
import com.ps.translatepro.android.translate.presentation.AndroidTranslateViewModel
import com.ps.translatepro.android.translate.presentation.TranslateScreen
import com.ps.translatepro.translate.presentation.TranslateEvent
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun TranslateProTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        com.ps.translatepro.android.core.theme.darkColors
    } else {
        com.ps.translatepro.android.core.theme.lightColors
    }

    val sfProText = FontFamily(
        Font(
            resId = R.font.sf_pro_text_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.sf_pro_text_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resId = R.font.sf_pro_text_bold,
            weight = FontWeight.Bold
        )
    )
    val typography = Typography(
        h1 = TextStyle(
            fontFamily = sfProText,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        h2 = TextStyle(
            fontFamily = sfProText,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        h3 = TextStyle(
            fontFamily = sfProText,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        body1 = TextStyle(
            fontFamily = sfProText,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        body2 = TextStyle(
            fontFamily = sfProText,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslateProTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TranslateRoot()
                }
            }
        }
    }
}

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.TRANSLATE) {
        composable(route = Routes.TRANSLATE) {
            val viewModel = hiltViewModel<AndroidTranslateViewModel>()
            val state by viewModel.state.collectAsState()
            TranslateScreen(state = state, onEvent = { event ->
                when (event) {
                    is TranslateEvent.RecordAudio -> {
                        navController.navigate(
                            Routes.VOICE_TO_TEXT + "/${state.fromLanguage.language.langCode}"
                        )
                    }
                    else -> viewModel.onEvent(event)
                }
            })
        }
        composable(
            route = Routes.VOICE_TO_TEXT + "/{languageCode}",
            arguments = listOf(
                navArgument("languageCode") {
                    type = NavType.StringType
                    defaultValue = "en"
                }
            )
        ) {
            Text(text = "Voice-to-text")
        }
    }
}
