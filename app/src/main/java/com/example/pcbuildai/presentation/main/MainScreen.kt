package com.example.pcbuildai.presentation.main

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    Scaffold {
        Text("You are logged in!")
    }
}
