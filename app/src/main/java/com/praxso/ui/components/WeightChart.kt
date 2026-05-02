package com.praxso.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeightChart(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your weight",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Monthly",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier
                    .background(Color.Black, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = "Track your weight fluctuations throughout the cycle.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(Color(0xFFFFEBEE), Color.Transparent)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            }
        }
    }
}
