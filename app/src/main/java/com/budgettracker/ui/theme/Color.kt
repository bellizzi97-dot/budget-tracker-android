package com.budgettracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BudgetGreen = Color(0xFF0E7C67)
val BudgetGreenDark = Color(0xFF0A5D50)
val BudgetGreenLight = Color(0xFF41B69B)
val BudgetYellow = Color(0xFFE5B936)
val BudgetPink = Color(0xFFE573A1)
val BudgetBlue = Color(0xFF4AA8D8)
val BudgetPurple = Color(0xFF7E57C2)
val BackgroundSoft = Color(0xFFF2F5F3)
val CardSoft = Color(0xFFF7F7F7)
val TextPrimary = Color(0xFF1A2E2B)
val TextSecondary = Color(0xFF627270)

private val LightColors = lightColorScheme(
    primary = BudgetGreen,
    onPrimary = Color.White,
    secondary = BudgetGreenLight,
    tertiary = BudgetYellow,
    background = BackgroundSoft,
    surface = Color.White,
    onSurface = TextPrimary,
    onBackground = TextPrimary,
    outline = Color(0xFFE0E5E2)
)

private val DarkColors = darkColorScheme(
    primary = BudgetGreenLight,
    onPrimary = Color.White,
    secondary = BudgetGreen,
    background = Color(0xFF101B1A),
    surface = Color(0xFF1C2D2A),
    onSurface = Color.White,
    onBackground = Color.White
)

@Composable
fun BudgetTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
