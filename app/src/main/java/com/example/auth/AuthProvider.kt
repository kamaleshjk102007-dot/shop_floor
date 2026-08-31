package com.example.auth

import android.content.Context
import com.example.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthProvider(private val context: Context) {

    private val sharedPrefs = context.getSharedPreferences("shopfloor_auth_prefs", Context.MODE_PRIVATE)

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUserEmail = MutableStateFlow<String?>(null)
    val currentUserEmail: StateFlow<String?> = _currentUserEmail.asStateFlow()

    private val _rememberMe = MutableStateFlow(false)
    val rememberMe: StateFlow<Boolean> = _rememberMe.asStateFlow()

    private val _savedEmail = MutableStateFlow("")
    val savedEmail: StateFlow<String> = _savedEmail.asStateFlow()

    private val _isLockedOut = MutableStateFlow(false)
    val isLockedOut: StateFlow<Boolean> = _isLockedOut.asStateFlow()

    private val _lockoutTimeRemaining = MutableStateFlow(0)
    val lockoutTimeRemaining: StateFlow<Int> = _lockoutTimeRemaining.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var failedAttempts = 0
    private var lockoutJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    init {
        // Load Remember Me state and Saved Email
        _rememberMe.value = sharedPrefs.getBoolean("remember_me", false)
        _savedEmail.value = sharedPrefs.getString("saved_email", "") ?: ""
    }

    fun setRememberMe(checked: Boolean) {
        _rememberMe.value = checked
        sharedPrefs.edit().putBoolean("remember_me", checked).apply()
        if (!checked) {
            sharedPrefs.edit().remove("saved_email").apply()
            _savedEmail.value = ""
        }
    }

    fun tryAutoLogin(): Boolean {
        val wasLoggedIn = sharedPrefs.getBoolean("is_logged_in", false)
        val email = sharedPrefs.getString("logged_in_email", null)
        if (wasLoggedIn && email != null) {
            _isLoggedIn.value = true
            _currentUserEmail.value = email
            return true
        }
        return false
    }

    fun login(emailInput: String, passwordInput: String, onResult: (Boolean) -> Unit) {
        if (_isLockedOut.value) {
            _errorMessage.value = "Too many failed attempts. Try again in ${_lockoutTimeRemaining.value}s."
            onResult(false)
            return
        }

        _errorMessage.value = null

        // Validate Inputs
        val email = emailInput.trim()
        val password = passwordInput.trim()

        if (email.isEmpty()) {
            _errorMessage.value = "Email or username cannot be empty"
            onResult(false)
            return
        }

        if (password.isEmpty()) {
            _errorMessage.value = "Password cannot be empty"
            onResult(false)
            return
        }

        // Validate email-shaped account identifiers.
        if (email.contains("@") && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "Please enter a valid email address"
            onResult(false)
            return
        }

        if (password.length < 4) {
            _errorMessage.value = "Password must be at least 4 characters"
            onResult(false)
            return
        }

        val configuredAccount = BuildConfig.SHOPFLOOR_USERNAME
        val configuredPassword = BuildConfig.SHOPFLOOR_PASSWORD
        val isConfigured = configuredAccount.isNotBlank() &&
                configuredPassword.isNotBlank() &&
                configuredAccount != "NOT_CONFIGURED" &&
                configuredPassword != "NOT_CONFIGURED"

        if (!isConfigured) {
            _errorMessage.value = "Authentication is not configured. Contact your system administrator."
            onResult(false)
            return
        }

        _isLoading.value = true

        scope.launch {
            val isValid = email == configuredAccount && password == configuredPassword
            _isLoading.value = false

            if (isValid) {
                failedAttempts = 0
                _isLoggedIn.value = true
                _currentUserEmail.value = email

                // Handle session persistence
                val editor = sharedPrefs.edit()
                if (_rememberMe.value) {
                    editor.putBoolean("is_logged_in", true)
                    editor.putString("logged_in_email", email)
                    editor.putString("saved_email", email)
                    _savedEmail.value = email
                } else {
                    editor.putBoolean("is_logged_in", false)
                    editor.remove("logged_in_email")
                }
                editor.apply()

                onResult(true)
            } else {
                failedAttempts++
                if (failedAttempts >= 5) {
                    startLockout()
                } else {
                    _errorMessage.value = "Invalid credentials. Attempt $failedAttempts of 5 before lockout."
                }
                onResult(false)
            }
        }
    }

    private fun startLockout() {
        _isLockedOut.value = true
        _errorMessage.value = "Too many failed attempts. Account locked for 30 seconds."
        lockoutJob?.cancel()
        lockoutJob = scope.launch {
            var remaining = 30
            _lockoutTimeRemaining.value = remaining
            while (remaining > 0) {
                delay(1000)
                remaining--
                _lockoutTimeRemaining.value = remaining
            }
            _isLockedOut.value = false
            failedAttempts = 0
            _errorMessage.value = null
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _currentUserEmail.value = null
        sharedPrefs.edit()
            .putBoolean("is_logged_in", false)
            .remove("logged_in_email")
            .apply()
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
