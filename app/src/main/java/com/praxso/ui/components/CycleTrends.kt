package com.praxso.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.praxso.ui.theme.dmSans

@Composable
fun CycleTrends(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(
            text = "Cycle Trends",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = dmSans,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth()
            ) {
                // Background Grid Lines
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 40.dp)
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(3) {
                        Canvas(modifier = Modifier.fillMaxWidth().height(1.dp)) {
                            drawLine(
                                color = Color.LightGray.copy(alpha = 0.3f),
                                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                                strokeWidth = 2f
                            )
                        }
                    }
                }

                // Main Content with Arrows
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left Arrow
                    ArrowButton(direction = "left")

                    // Bars Container
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment  = Alignment.Bottom
                    ) {
                        val trends = listOf(
                            TrendData(28, "Jan"),
                            TrendData(30, "Feb"),
                            TrendData(28, "Mar"),
                            TrendData(32, "Apr"),
                            TrendData(28, "May"),
                            TrendData(28, "Jun")
                        )

                        trends.forEach { trend ->
                            TrendBar(trend)
                        }
                    }

                    // Right Arrow
                    ArrowButton(direction = "right")
                }
            }
        }
    }
}

data class TrendData(val value: Int, val month: String)

@Composable
fun TrendBar(data: TrendData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = data.value.toString(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = dmSans,
            color = Color.Black
        )
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .width(26.dp)
                .height(180.dp)
                .background(Color(0xFFE1DDF9).copy(alpha = 0.8f), CircleShape)
        ) {
            // Green middle segment
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.Center)
                    .offset(y = (-10).dp)
                    .background(Color(0xFF7B8F8A), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Simple Clock/Timer Icon drawn with Canvas
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(Color.White.copy(alpha = 0.6f), style = Stroke(width = 2f))
                    drawLine(Color.White.copy(alpha = 0.6f), center, center.copy(y = center.y - 4f), strokeWidth = 2f)
                    drawLine(Color.White.copy(alpha = 0.6f), center, center.copy(x = center.x + 3f), strokeWidth = 2f)
                }
            }

            // Red bottom segment
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color(0xFFF48FB1).copy(alpha = 0.7f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Simple Drop Icon drawn with Canvas
                Canvas(modifier = Modifier.size(10.dp)) {
                    val path = androidx.compose.ui.graphics.Path().apply {
                        moveTo(size.width / 2, 0f)
                        cubicTo(size.width, size.height * 0.6f, size.width, size.height, size.width / 2, size.height)
                        cubicTo(0f, size.height, 0f, size.height * 0.6f, size.width / 2, 0f)
                    }
                    drawPath(path, Color.White.copy(alpha = 0.6f))
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = data.month,
            fontSize = 12.sp,
            fontFamily = dmSans,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ArrowButton(direction: String) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .border(1.dp, Color(0xFFE1DDF9), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(12.dp)) {
            val path = androidx.compose.ui.graphics.Path().apply {
                if (direction == "left") {
                    moveTo(size.width, 0f)
                    lineTo(0f, size.height / 2)
                    lineTo(size.width, size.height)
                } else {
                    moveTo(0f, 0f)
                    lineTo(size.width, size.height / 2)
                    lineTo(0f, size.height)
                }
            }
            drawPath(path, Color(0xFFB39DDB), style = Stroke(width = 3f, cap = androidx.compose.ui.graphics.StrokeCap.Round))
        }
    }
}
