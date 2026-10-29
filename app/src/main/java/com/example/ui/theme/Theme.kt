package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MusizEarnColorScheme = darkColorScheme(
    primary = AcidLime,
    onPrimary = ObsidianBlack,
    primaryContainer = CardOliveElevated,
    onPrimaryContainer = AcidLimeBright,
    secondary = AcidLime,
    onSecondary = ObsidianBlack,
    secondaryContainer = CardOlive,
    onSecondaryContainer = AcidLimeBright,
    tertiary = RewardGold,
    onTertiary = ObsidianBlack,
    background = ObsidianBlack,
    onBackground = TextPrimary,
    surface = DeepOliveNavy,
    onSurface = TextPrimary,
    surfaceVariant = CardOlive,
    onSurfaceVariant = TextSecondary,
    outline = CardOliveBorder,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MusizEarnColorScheme,
        typography = Typography,
        content = content
    )
}
