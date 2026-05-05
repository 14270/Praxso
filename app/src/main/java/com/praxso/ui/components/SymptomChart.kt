package com.praxso.ui.components

import androidx.compose.foundation.background
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
            .background(Color(0xFFF5FAF9))
            .padding(horizontal = 4.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Body Signals",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = dmSans,
            color = Color(0xFF1A1A1A),
            lineHeight = 16.sp,
            letterSpacing = (-0.32).sp,
            modifier = Modifier
                .padding(start = 14.dp, bottom = 16.dp)
        )

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
                .height(376.dp)
                .padding(horizontal = 8.dp)
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
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
            ) {
                // Header Inside Card
                Text(
                    text = "Symptom Trends",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = dmSans,
                    color = Color(0xFF1A1A1A),
                    lineHeight = 16.sp,
                    letterSpacing = (-0.32).sp,
                    modifier = Modifier.size(width = 151.dp, height = 21.dp)
                )
                
                Text(
                    text = "Compared to last cycle",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = dmSans,
                    color = Color(0xFF757575),
                    lineHeight = 14.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Donut Chart Canvas
                    androidx.compose.foundation.Canvas(
                        modifier = Modifier.size(220.dp)
                    ) {
                        val strokeWidth = 50.dp.toPx() 

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
                                colors = listOf(Color(0xFFB4A8DA), Color(0xFFF5F2FF))
                            ),
                            startAngle = -90f,
                            sweepAngle = 112f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                        )
                        
                        // Fatigue - 21%
                        drawArc(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(Color(0xFFFDF2F2), Color(0xFFE99597))
                            ),
                            startAngle = 22f,
                            sweepAngle = 75f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                        )
                        
                        // Acne - 17%
                        drawArc(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(Color(0xFFFAFDFA), Color(0xFF6E8C82))
                            ),
                            startAngle = 97f,
                            sweepAngle = 61f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                        )
                        
                        // Mood - 30%
                        drawArc(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(Color(0xFFFFF1F1), Color(0xFFF4C3C4))
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
                        modifier = Modifier.align(Alignment.Center).offset(x = 116.dp, y = (-78).dp)
                    )
                    
                    SymptomLabel(
                        percentage = "21%",
                        label = "Fatigue",
                        modifier = Modifier.align(Alignment.Center).offset(x = 72.dp, y = 120.dp)
                    )
                    
                    SymptomLabel(
                        percentage = "17%",
                        label = "Acne",
                        modifier = Modifier.align(Alignment.Center).offset(x = (-86).dp, y = 112.dp)
                    )
                    
                    SymptomLabel(
                        percentage = "30%",
                        label = "Mood",
                        modifier = Modifier.align(Alignment.Center).offset(x = (-116).dp, y = (-78).dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SymptomLabel(percentage: String, label: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Surface(
        modifier = modifier.size(72.dp), // Slightly larger to match design proportions
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 8.dp,
        tonalElevation = 2.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = percentage,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = dmSans,
                color = Color(0xFF1A1A1A),
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = dmSans,
                color = Color(0xFF1A1A1A),
                lineHeight = 12.sp
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
                .padding(4.dp)
        )
    }
}
