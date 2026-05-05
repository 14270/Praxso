package com.praxso.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.praxso.ui.theme.PraxsoTheme
import com.praxso.ui.theme.dmSans
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.geometry.Rect

@Composable
fun StabilityCard(
    modifier: Modifier = Modifier,
    score: String = "78%",
    dataPoints: List<Float> = listOf(24.2f, 25.5f, 28.0f, 32.0f),
    months: List<String> = listOf("Jan", "Feb", "Mar", "Apr")
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)) {
            Text(
                text = "Stability Summary",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = dmSans,
                color = Color(0xFF1A1A1A),
                lineHeight = 16.sp,
                letterSpacing = (-0.32).sp,
                modifier = Modifier
            )
            Spacer(Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .width(175.dp)
                    .height(3.dp)
            )
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(305.dp)
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Background radial gradient decoration
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(160.dp)
                        .offset(x = 40.dp, y = (-40).dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0x26FF5E7A),
                                    Color(0x14FF5E7A),
                                    Color(0x00FF5E7A)
                                )
                            ),
                            shape = CircleShape
                        )
                )
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    // Subtitle
                    Text(
                        text = "Based on your recent logs and symptom\npatterns.",
                        fontSize = 14.sp,
                        fontFamily = dmSans,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF757575),
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // "Stability Score" label
                    Text(
                        text = "Stability Score",
                        fontSize = 15.sp,
                        fontFamily = dmSans,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1A1A1A)
                    )

                    // Score value
                    Text(
                        text = score,
                        fontSize = 24.sp,
                        fontFamily = dmSans,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Chart fills the remaining space
                    StabilityChart(
                        points = dataPoints,
                        months = months,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}
@Composable
fun StabilityChart(
    points: List<Float>,
    months: List<String>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val yLabels = listOf("32d", "28d", "24d")

    val outerColor = Color(0xFFE6E0FB)
    val midColor = Color(0xFFB4A8DA)
    val innerColor = Color(0xFF9C90CC)
    val indicatorColor = Color(0xFF4F6A64)

    Canvas(modifier = modifier) {

        val width = size.width
        val height = size.height

        val leftPadding = 40.dp.toPx()
        val rightPadding = 16.dp.toPx()
        val topPadding = 24.dp.toPx()
        val bottomPadding = 28.dp.toPx()

        val chartWidth = width - leftPadding - rightPadding
        val chartHeight = height - topPadding - bottomPadding

        val xSpacing = chartWidth / (points.size - 1)

        fun getY(value: Float): Float {
            val minVal = 24f
            val maxVal = 32f
            val normalized = (value - minVal) / (maxVal - minVal)
            return topPadding + chartHeight - (normalized * chartHeight)
        }

        val baseY = getY(24f)

        // ───────── Y AXIS ─────────
        yLabels.forEachIndexed { index, label ->
            val yPos = topPadding + (index * (chartHeight / (yLabels.size - 1)))

            val measured = textMeasurer.measure(
                text = label,
                style = TextStyle(fontSize = 12.sp, color = Color.Black)
            )

            drawText(
                textLayoutResult = measured,
                topLeft = Offset(8.dp.toPx(), yPos - measured.size.height / 2f)
            )
        }

        // ───────── X AXIS ─────────
        months.forEachIndexed { index, month ->
            val x = leftPadding + index * xSpacing
            val isSelected = month == "Mar"

            val measured = textMeasurer.measure(
                text = month,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = if (isSelected) Color.Black else Color.Gray,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            )

            drawText(
                textLayoutResult = measured,
                topLeft = Offset(
                    x - measured.size.width / 2,
                    height - measured.size.height - 4.dp.toPx()
                )
            )
        }

        // ───────── BAND SHAPE ─────────
        fun taperedBand(
            topValue: Float,
            bottomStartValue: Float,
            bottomEndValue: Float
        ): Path {
            return Path().apply {
                val startX = leftPadding
                val endX = leftPadding + chartWidth

                val topY = getY(topValue)
                val bottomStartY = getY(bottomStartValue)
                val bottomEndY = getY(bottomEndValue)

                moveTo(startX, bottomStartY)
                lineTo(endX, topY)
                lineTo(endX, bottomEndY)
                lineTo(startX, bottomStartY)
                close()
            }
        }

        // ───────── BANDS ─────────
        drawPath(taperedBand(27.8f, 24f, 26.4f), innerColor)

        drawPath(taperedBand(29f, 24f, 27f), midColor)

        drawPath(
            taperedBand(31f, 24f, 24f),
            brush = Brush.linearGradient(
                colors = listOf(
                    outerColor,
                    outerColor.copy(alpha = 0.7f)
                ),
                start = Offset(leftPadding, baseY),
                end = Offset(leftPadding + chartWidth, getY(31f))
            )
        )

        // ───────── DOT (NEATLY SEPARATED) ─────────
        val selectedIndex = months.indexOf("Mar").coerceAtLeast(0)
        val x = leftPadding + selectedIndex * xSpacing

        val rawY = getY(points[selectedIndex])
        val dotLift = 20.dp.toPx() // push dot up nicely
        val y = rawY - dotLift

        drawCircle(
            color = indicatorColor,
            radius = 6.dp.toPx(),
            center = Offset(x, y)
        )

        // ───────── DASHED LINE (5 LINES, PERFECT POSITION) ─────────
        val dashHeight = 6.dp.toPx()
        val gapHeight = 6.dp.toPx()
        val startOffset = 10.dp.toPx() // 🔥 balanced spacing

        var currentY = y + startOffset

        repeat(5) {
            drawLine(
                color = indicatorColor,
                start = Offset(x, currentY),
                end = Offset(x, currentY + dashHeight),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
            currentY += dashHeight + gapHeight
        }

        // ───────── TOOLTIP ─────────
        val tooltipWidth = 90.dp.toPx()
        val tooltipHeight = 50.dp.toPx()

        val tooltipX = x - tooltipWidth / 2
        val tooltipY = y - tooltipHeight - 30.dp.toPx()

        drawRoundRect(
            color = Color.Black,
            topLeft = Offset(tooltipX, tooltipY),
            size = Size(tooltipWidth, tooltipHeight),
            cornerRadius = CornerRadius(12.dp.toPx())
        )

        val triangle = Path().apply {
            moveTo(x - 10.dp.toPx(), tooltipY + tooltipHeight)
            lineTo(x + 10.dp.toPx(), tooltipY + tooltipHeight)
            lineTo(x, tooltipY + tooltipHeight + 10.dp.toPx())
            close()
        }

        drawPath(triangle, Color.Black)

        val t1 = textMeasurer.measure(
            text = "Stability",
            style = TextStyle(color = Color.White, fontSize = 12.sp)
        )

        val t2 = textMeasurer.measure(
            text = "Improving",
            style = TextStyle(color = Color.White, fontSize = 12.sp)
        )

        drawText(
            textLayoutResult = t1,
            topLeft = Offset(
                x - t1.size.width / 2,
                tooltipY + 8.dp.toPx()
            )
        )

        drawText(
            textLayoutResult = t2,
            topLeft = Offset(
                x - t2.size.width / 2,
                tooltipY + 8.dp.toPx() + t1.size.height
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF9F9FB)
@Composable
fun StabilityCardPreview() {
    PraxsoTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            StabilityCard()
        }
    }
}
