package com.example.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.AuthProvider
import com.example.R

@Composable
fun LoginScreen(
    authProvider: AuthProvider,
    onLoginSuccess: () -> Unit
) {
    // AuthProvider State Observables
    val isLoading by authProvider.isLoading.collectAsState()
    val errorMessage by authProvider.errorMessage.collectAsState()
    val isLockedOut by authProvider.isLockedOut.collectAsState()
    val lockoutTimeRemaining by authProvider.lockoutTimeRemaining.collectAsState()
    val rememberMePref by authProvider.rememberMe.collectAsState()
    val savedEmail by authProvider.savedEmail.collectAsState()

    // Form inputs state
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMeChecked by remember { mutableStateOf(false) }

    // Dialog state
    var showForgotDialog by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val ambientMotion = rememberInfiniteTransition(label = "login-ambient-motion")
    val purpleDrift by ambientMotion.animateFloat(
        initialValue = -18f,
        targetValue = 22f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "purple-drift"
    )
    val goldDrift by ambientMotion.animateFloat(
        initialValue = 16f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gold-drift"
    )
    val cardLift by ambientMotion.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "card-lift"
    )

    // Pre-populate fields on load
    LaunchedEffect(savedEmail, rememberMePref) {
        rememberMeChecked = rememberMePref
        if (rememberMePref && savedEmail.isNotEmpty()) {
            emailInput = savedEmail
        }
    }

    // Dismiss error message on change
    LaunchedEffect(emailInput, passwordInput) {
        authProvider.clearErrorMessage()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF031B35),
                        Color(0xFF064B73),
                        Color(0xFF087FA7)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = purpleDrift.dp, y = (-45).dp)
                .size(230.dp)
                .background(Color(0xFF16C8FF).copy(alpha = 0.20f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = goldDrift.dp, y = 55.dp)
                .size(190.dp)
                .background(Color(0xFF58E8FF).copy(alpha = 0.16f), CircleShape)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .widthIn(max = 420.dp)
                    .fillMaxWidth()
                    .offset(y = cardLift.dp)
                    .graphicsLayer {
                        shadowElevation = 34f
                        shape = RoundedCornerShape(34.dp)
                        clip = false
                    },
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0A5278).copy(alpha = 0.86f)),
                shape = RoundedCornerShape(34.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF45DFFF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF1681AA).copy(alpha = 0.58f),
                                    Color(0xFF073A61).copy(alpha = 0.88f)
                                )
                            )
                        )
                        .padding(horizontal = 30.dp, vertical = 26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo
                    Image(
                        painter = painterResource(R.drawable.ocs_logo),
                        contentDescription = "OCS — Delivering Commitments, Crafting Excellence",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Labour & Efficiency Control",
                        fontSize = 13.sp,
                        color = Color(0xFFC9F5FF),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Input: Email/Username
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Username", color = Color(0xFFBDEFFF)) },
                        placeholder = { Text("Enter your account", color = Color(0xFF83C9DE)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Email",
                                tint = Color(0xFF60E6FF)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF52E5FF),
                            unfocusedBorderColor = Color(0xFF147BA8),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF52E5FF),
                            focusedContainerColor = Color(0xFF032D57).copy(alpha = 0.86f),
                            unfocusedContainerColor = Color(0xFF032D57).copy(alpha = 0.76f)
                        ),
                        shape = RoundedCornerShape(6.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input: Password
                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = { passwordInput = it },
                        label = { Text("Password", color = Color(0xFFBDEFFF)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Password",
                                tint = Color(0xFF60E6FF)
                            )
                        },
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = icon, contentDescription = "Toggle Visibility", tint = Color(0xFFBDEFFF))
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF52E5FF),
                            unfocusedBorderColor = Color(0xFF147BA8),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF52E5FF),
                            focusedContainerColor = Color(0xFF032D57).copy(alpha = 0.86f),
                            unfocusedContainerColor = Color(0xFF032D57).copy(alpha = 0.76f)
                        ),
                        shape = RoundedCornerShape(6.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Row: Remember Me & Forgot Password
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                val nextChecked = !rememberMeChecked
                                rememberMeChecked = nextChecked
                                authProvider.setRememberMe(nextChecked)
                            }
                        ) {
                            Checkbox(
                                checked = rememberMeChecked,
                                onCheckedChange = {
                                    rememberMeChecked = it
                                    authProvider.setRememberMe(it)
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF1CC8F2),
                                    uncheckedColor = Color(0xFF9ADCEC)
                                )
                            )
                            Text(
                                text = "Remember Me",
                                fontSize = 13.sp,
                                color = Color(0xFFD8F8FF)
                            )
                        }

                        Text(
                            text = "Forgot password?",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF7DEAFF),
                            modifier = Modifier
                                .clickable { showForgotDialog = true }
                                .padding(4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Error Notification Banner
                    AnimatedVisibility(visible = errorMessage != null) {
                        errorMessage?.let {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF9DEDC), RoundedCornerShape(8.dp))
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = "Error icon",
                                    tint = Color(0xFFB3261E),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = it,
                                    color = Color(0xFF601410),
                                    fontSize = 13.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    // Submit Button with Progress Loader or Countdown Lock
                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            authProvider.login(emailInput, passwordInput) { success ->
                                if (success) {
                                    onLoginSuccess()
                                }
                            }
                        },
                        enabled = !isLoading && !isLockedOut,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF087DBB),
                            disabledContainerColor = Color(0xFF205A75)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 12.dp,
                            pressedElevation = 2.dp
                        ),
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else if (isLockedOut) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Locked",
                                modifier = Modifier.size(18.dp),
                                tint = Color(0xFF6B4A7D)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "LOCKED OUT (${lockoutTimeRemaining}s)",
                                color = Color(0xFF6B4A7D),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        } else {
                            Text(
                                text = "LOGIN",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer info
            Text(
                text = "Terminal v1.4.2 • Secured Local Mode",
                fontSize = 11.sp,
                color = Color(0xFFC9F5FF)
            )
        }
    }

    // Forgot Password Credentials Dialog
    if (showForgotDialog) {
        AlertDialog(
            onDismissRequest = { showForgotDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Password recovery information",
                    tint = Color(0xFF6D28D9),
                    modifier = Modifier.size(40.dp)
                )
            },
            title = {
                Text(
                    text = "Password Recovery",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E1065),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "Contact your system administrator to reset your password.",
                    color = Color(0xFF5B3A75),
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showForgotDialog = false }) {
                    Text("Close", color = Color(0xFF6D28D9), fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Color.White.copy(alpha = 0.72f)
        )
    }
}
