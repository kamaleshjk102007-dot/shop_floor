package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.auth.AuthProvider
import com.example.dashboard.DashboardViewModel
import com.example.screens.HomeScreen
import com.example.screens.LoginScreen
import com.example.screens.SplashScreen
import com.example.screens.UiLanguageRuntime
import com.example.ui.theme.MyApplicationTheme

enum class Screen {
    SPLASH,
    LOGIN,
    DASHBOARD
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiLanguageRuntime.tag = getSharedPreferences("shop_floor_ui", MODE_PRIVATE)
            .getString("app_language", "ENGLISH")
            .let { saved ->
                when (saved) {
                    "TAMIL" -> "ta"; "HINDI" -> "hi"; "TELUGU" -> "te"
                    "KANNADA" -> "kn"; "MALAYALAM" -> "ml"; else -> "en"
                }
            }
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST
            )
        }
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShopFloorApp(
                        authProvider = remember { AuthProvider(applicationContext) },
                        viewModel = remember { DashboardViewModel(applicationContext) }
                    )
                }
            }
        }
    }

    private companion object {
        const val NOTIFICATION_PERMISSION_REQUEST = 901
    }
}

@Composable
fun ShopFloorApp(
    authProvider: AuthProvider,
    viewModel: DashboardViewModel
) {
    var currentScreen by remember { mutableStateOf(Screen.SPLASH) }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            if (targetState == Screen.DASHBOARD || (initialState == Screen.SPLASH && targetState == Screen.LOGIN)) {
                // Slide in from right (forward transition)
                (slideInHorizontally { width -> width } + fadeIn())
                    .togetherWith(slideOutHorizontally { width -> -width } + fadeOut())
            } else {
                // Slide in from left (backwards transition, e.g. Logout)
                (slideInHorizontally { width -> -width } + fadeIn())
                    .togetherWith(slideOutHorizontally { width -> width } + fadeOut())
            }
        },
        label = "ScreenTransition"
    ) { screen ->
        when (screen) {
            Screen.SPLASH -> {
                SplashScreen(
                    authProvider = authProvider,
                    onSplashComplete = { isLoggedIn ->
                        currentScreen = if (isLoggedIn) {
                            Screen.DASHBOARD
                        } else {
                            Screen.LOGIN
                        }
                    }
                )
            }
            Screen.LOGIN -> {
                LoginScreen(
                    authProvider = authProvider,
                    onLoginSuccess = {
                        currentScreen = Screen.DASHBOARD
                    }
                )
            }
            Screen.DASHBOARD -> {
                HomeScreen(
                    authProvider = authProvider,
                    viewModel = viewModel,
                    onLogoutSuccess = {
                        currentScreen = Screen.LOGIN
                    }
                )
            }
        }
    }
}
