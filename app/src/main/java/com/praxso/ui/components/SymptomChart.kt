package com.praxso.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.praxso.ui.theme.dmSans
import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SymptomChart(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Body Signals",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = dmSans,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.padding(start = 4.dp, bottom = 16.dp)
        )

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(343.dp)
                .height(376.dp)
                .align(Alignment.CenterHorizontally),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header Inside Card
                Text(
                    text = "Symptom Trends",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = dmSans,
                    color = Color(0xFF1A1A1A)
                )
                
                Text(
                    text = "Compared to last cycle",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = dmSans,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Donut Chart Canvas
                    androidx.compose.foundation.Canvas(
                        modifier = Modifier.size(240.dp) // Slightly larger
                    ) {
                        val strokeWidth = 60.dp.toPx() // Thicker for "covered" look

                        // Background Track
                        drawArc(
                            color = Color(0xFFF1F1F1),
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                        )
                        
                        // Bloating - 31%
                        drawArc(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(Color(0xFFF5F2FF), Color(0xFFB4A8DA))
                            ),
                            startAngle = -90f,
                            sweepAngle = 112f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                        )
                        
                        // Fatigue - 21%
                        drawArc(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(Color(0xFFFFE4E4), Color(0xFFE99597))
                            ),
                            startAngle = 22f,
                            sweepAngle = 75f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                        )
                        
                        // Acne - 17%
                        drawArc(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(Color(0xFFF2FAF8), Color(0xFFB4D4CC))
                            ),
                            startAngle = 97f,
                            sweepAngle = 61f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                        )
                        
                        // Mood - 30%
                        drawArc(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(Color(0xFFFFFFFF), Color(0xFFFDECEE))
                            ),
                            startAngle = 158f,
                            sweepAngle = 112f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                        )
                    }

                    // Data Labels (Floating Circles)
                    SymptomLabel(
                        percentage = "31%",
                        label = "Bloating",
                        modifier = Modifier.align(Alignment.CenterEnd).offset(x = (10).dp, y = (-40).dp)
                    )
                    
                    SymptomLabel(
                        percentage = "21%",
                        label = "Fatigue",
                        modifier = Modifier.align(Alignment.BottomCenter).offset(x = (60).dp, y = (-20).dp)
                    )
                    
                    SymptomLabel(
                        percentage = "17%",
                        label = "Acne",
                        modifier = Modifier.align(Alignment.BottomStart).offset(x = (0).dp, y = (-40).dp)
                    )
                    
                    SymptomLabel(
                        percentage = "30%",
                        label = "Mood",
                        modifier = Modifier.align(Alignment.TopStart).offset(x = (10).dp, y = (40).dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SymptomLabel(percentage: String, label: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Surface(
        modifier = modifier.size(72.dp),
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 6.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = percentage,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = dmSans,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = dmSans,
                color = Color(0xFF757575)
            )
        }
    }
}

@Preview(
    name = "Symptom Chart - Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Symptom Chart - Dark",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SymptomChartPreview() {
    androidx.compose.material3.MaterialTheme {
        SymptomChart(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
