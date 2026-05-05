package com.praxso.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.praxso.R
import com.praxso.ui.theme.PraxsoTheme
import com.praxso.ui.theme.dmSans
import androidx.compose.foundation.BorderStroke

@Composable
fun CycleTrends(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF5FAF9)) // ✅ ADD THIS LINE
            .padding(vertical = 12.dp)
    ) {
        // Title Section
        Column(modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)) {
            Text(
                text = "Cycle Trends",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = dmSans,
                color = Color(0xFF1A1A1A),
                lineHeight = 16.sp,
                letterSpacing = (-0.32).sp,
            )
            Spacer(Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(3.dp)
            )
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(287.dp)
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                // Background Grid Lines
                Canvas(modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 48.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = 61.dp)
                ) {
                    val gridLineColor = Color(0xFFF2F2F2)
                    // Lines at Top, Middle (approx pink top), and Bottom
                    val yPositions = listOf(0f, 115.dp.toPx(), 180.dp.toPx())
                    yPositions.forEach { y ->
                        drawLine(
                            color = gridLineColor,
                            start = androidx.compose.ui.geometry.Offset(0f, y),
                            end = androidx.compose.ui.geometry.Offset(size.width, y),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                }

                // Main Content with Arrows
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left Arrow Button
                    ArrowButton(iconRes = R.drawable.right_arrow_ic)

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 17.dp)
                            .padding(bottom = 16.dp) // Push bars up
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val trends = listOf(
                            TrendData(28, "Jan", 85),
                            TrendData(30, "Feb", 110),
                            TrendData(28, "Mar", 100),
                            TrendData(32, "Apr", 120),
                            TrendData(28, "May", 100),
                            TrendData(28, "Jun", 85)
                        )

                        trends.forEach { trend ->
                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                TrendBar(trend, modifier = Modifier.fillMaxHeight())
                            }
                        }
                    }

                    // Right Arrow Button
                    ArrowButton(iconRes = R.drawable.left_arrow_ic)
                }
            }
        }
    }
}

data class TrendData(val value: Int, val month: String, val greenOffset: Int)

@Composable
fun TrendBar(data: TrendData, modifier: Modifier = Modifier) {
    // Base height for 28 days is increased to 165dp
    val barHeight = (data.value * 165 / 28).dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
    ) {
        // Value label with fixed height for alignment
        Box(
            modifier = Modifier.height(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = data.value.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = dmSans,
                color = Color(0xFF1A1A1A)
            )
        }
        
        Spacer(Modifier.height(8.dp))
        
        Box(
            modifier = Modifier
                .width(14.dp)
                .height(barHeight)
                .background(Color(0xFFB8B1E3), CircleShape)
        ) {
            // Green segment - floating
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = (-data.greenOffset).dp)
                    .background(Color(0xFF6B8E7E), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                SunIcon()
            }

            // Coral/Pink segment - floating significantly above bottom for selected months
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = if (data.month in listOf("Feb", "Mar", "Apr", "May")) (-24).dp else 0.dp)
                    .background(Color(0xFFE6989A), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                DropIcon()
            }
        }
        
        // Original spacer restored
        Spacer(Modifier.height(8.dp))
        
        // Month label to match exact Figma specs
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = data.month,
                fontSize = 8.sp,
                fontFamily = dmSans,
                color = Color(0xFF757575),
                fontWeight = FontWeight.Normal, // 400 Regular
                lineHeight = 8.sp, // 100%
                letterSpacing = 0.sp
            )
        }
    }
}

@Composable
fun SunIcon() {
    Image(
        painter = painterResource(id = R.drawable.sun_ic),
        contentDescription = "Sun Icon",
        modifier = Modifier.size(16.dp).padding(
            2.dp

        )
    )
}

@Composable
fun DropIcon() {
    Canvas(modifier = Modifier.size(12.dp)) {
        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(size.width / 2, 2f)
            cubicTo(size.width * 0.85f, size.height * 0.55f, size.width * 0.85f, size.height * 0.9f, size.width / 2, size.height * 0.95f)
            cubicTo(size.width * 0.15f, size.height * 0.9f, size.width * 0.15f, size.height * 0.55f, size.width / 2, 2f)
            close()
        }
        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun ArrowButton(iconRes: Int) {
    Box(
        modifier = Modifier
            .size(32.dp),
            /*.background(Color(0xFFF5F3FF), CircleShape),*/
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5FAF9)
@Composable
fun CycleTrendsPreview() {
    PraxsoTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            CycleTrends()
        }
    }
}