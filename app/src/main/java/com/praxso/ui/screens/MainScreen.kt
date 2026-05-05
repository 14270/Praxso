package com.praxso.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.praxso.ui.navigation.BottomNav

@Composable
fun MainScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize() ,
    bottomBar = {
            BottomNav()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            InsightsScreen()
        }
    }
}