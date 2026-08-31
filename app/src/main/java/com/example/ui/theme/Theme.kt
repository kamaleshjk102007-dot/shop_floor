package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = CreativePurple,
    secondary = RoyalGold,
    tertiary = WarmCream,
    background = Color(0xFF17052F),
    surface = RoyalPurple,
    onPrimary = Color.White,
    onSecondary = RoyalPurple,
    onBackground = WarmCream,
    onSurface = Color.White,
    outline = CreativePurple.copy(alpha = 0.55f)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = SleekPrimary,
    secondary = SleekPrimaryContainer,
    tertiary = SleekSecondaryContainer,
    background = SleekBg,
    surface = Color.White.copy(alpha = 0.82f),
    onPrimary = Color.White,
    onSecondary = SleekOnPrimaryContainer,
    onTertiary = SleekOnSecondaryContainer,
    tertiaryContainer = WarmCream,
    onTertiaryContainer = RoyalPurple,
    onBackground = SleekTextMain,
    onSurface = SleekTextMain,
    surfaceVariant = SleekSurfaceVariant,
    onSurfaceVariant = SleekTextMuted,
    outline = SleekBorder,
    error = SleekError
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false, // Force Light Sleek Interface by default
  dynamicColor: Boolean = false, // Set to false to ensure exact theme fidelity
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
