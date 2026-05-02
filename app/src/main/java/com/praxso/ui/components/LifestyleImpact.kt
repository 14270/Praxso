package com.praxso.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.praxso.ui.theme.dmSans
import android.content.res.Configuration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LifestyleImpact(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "Lifestyle Impact",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = dmSans,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.padding(start = 4.dp, bottom = 16.dp)
        )

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(360.dp)
                .height(220.dp) // Increased height to accommodate more vertical spacing
                .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Increased spacing for "more place"
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Correlation Strength",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = dmSans,
                        color = Color(0xFF1A1A1A)
                    )

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFF7F6F6),
                        modifier = Modifier
                            .width(78.dp)
                            .height(24.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = "4 months",
                                fontSize = 11.sp,
                                color = Color(0xFF666666),
                                fontFamily = dmSans,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color(0xFF666666),
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }

                // Rows
                val sleepGradient = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB4A8DA),
                        Color(0xFFB4A8DA).copy(alpha = 0.34f)
                    )
                )

                CorrelationRow(label = "Sleep", activeBlocks = 7, defaultBrush = sleepGradient)
                CorrelationRow(label = "Hydrate", activeBlocks = 3, defaultBrush = Brush.verticalGradient(listOf(Color(0xFFFFB2B2), Color(0xFFFF7E7E))))
                CorrelationRow(label = "Caffeine", activeBlocks = 5, defaultBrush = Brush.verticalGradient(listOf(Color(0xFFB4D4CC), Color(0xFF8EB7AF))))
                CorrelationRow(label = "Exercise", activeBlocks = 4, defaultBrush = Brush.verticalGradient(listOf(Color(0xFFFFD5D5), Color(0xFFFBAFAF))))
                
                Spacer(modifier = Modifier.height(4.dp)) // Bottom breathing room
            }
        }
    }
}

@Composable
fun CorrelationRow(
    label: String,
    activeBlocks: Int,
    defaultBrush: Brush
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp), // Increased height for better centering
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.width(56.dp), // Increased to 56dp to prevent truncation of "Exercise" and "Caffeine"
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = dmSans,
            color = Color(0xFF1A1A1A)
        )

        Spacer(modifier = Modifier.width(12.dp)) // Neat gap like Figma

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp) // Spacing from image
        ) {
            repeat(9) { index ->
                val isActive = index < activeBlocks
                val brush = if (isActive) {
                    defaultBrush
                } else {
                    Brush.verticalGradient(listOf(Color(0xFFF1F1F1), Color(0xFFE8E8E8)))
                }

                Box(
                    modifier = Modifier
                        .width(23.dp) // Fixed width: 28px/dp
                        .height(18.dp) // Better proportion like image
                        .background(
                            brush = brush,
                            shape = RoundedCornerShape(6.dp)
                        )
                )
            }
        }
    }
}

@Preview(
    name = "Lifestyle Impact - Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Lifestyle Impact - Dark",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LifestyleImpactPreview() {
    MaterialTheme {
        LifestyleImpact(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}