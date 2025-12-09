package com.example.pcbuildai.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pcbuildai.presentation.auth.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.user) {
        if (state.user != null) onRegisterSuccess()
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = confirm, onValueChange = { confirm = it }, label = { Text("Confirm Password") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.signUp(email, password, confirm) },
            enabled = !state.isLoading
        ) { Text("Зарегистрироваться") }

        TextButton(onClick = onNavigateBack) {
            Text("Уже есть аккаунт? Войти")
        }

        state.error?.let { Text(it, color = Color.Red) }
        if (state.isLoading) CircularProgressIndicator()
    }
}

