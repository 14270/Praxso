package com.praxso.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.praxso.ui.components.CycleTrends
import com.praxso.ui.components.Header
import com.praxso.ui.components.LifestyleImpact
import com.praxso.ui.components.StabilityCard
import com.praxso.ui.components.SymptomChart
import com.praxso.ui.components.WeightChart

@Composable
fun InsightsScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        // Soft pink glow background at the top right
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 120.dp, y = (-120).dp)
                .size(480.dp)
                .background(
                    brush = Brush.radialGradient(
                        0.0f to Color(0x45FF80AB),
                        0.5f to Color(0x20FF80AB),
                        1.0f to Color(0x00FF80AB)
                    ),
                    shape = CircleShape
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item { Header() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { StabilityCard() }
            item { CycleTrends() }
            item { Box(modifier = Modifier.padding(horizontal = 16.dp)) { WeightChart() } }
            item { Box(modifier = Modifier.padding(horizontal = 16.dp)) { SymptomChart() } }
            item { Box(modifier = Modifier.padding(horizontal = 16.dp)) { LifestyleImpact() } }
            // Bottom spacing to ensure content scrolls above the floating nav
            item { Spacer(modifier = Modifier.height(110.dp)) }
        }
    }
}
