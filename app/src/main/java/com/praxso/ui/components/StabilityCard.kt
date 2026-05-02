package com.praxso.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.praxso.ui.theme.dmSans

@Composable
fun StabilityCard(
    modifier: Modifier = Modifier,
    score: String = "78%",
    dataPoints: List<Float> = listOf(25.5f, 26.8f, 29.2f, 31.5f),
    months: List<String> = listOf("Jan", "Feb", "Mar", "Apr")
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        // "Stability Summary" title — bold, near-black, 18sp, left-aligned with 16dp start
        Text(
            text = "Stability Summary",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = dmSans,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
        )

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(305.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF6F6F8) // Warm off-white matching the design
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                // Subtitle — 14sp, DM Sans Regular, muted gray, 2-line
                Text(
                    text = "Based on your recent logs and symptom\npatterns.",
                    fontSize = 14.sp,
                    fontFamily = dmSans,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF9E9E9E),
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // "Stability Score" label — 14sp medium, near-black
                Text(
                    text = "Stability Score",
                    fontSize = 14.sp,
                    fontFamily = dmSans,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1A1A1A)
                )

                // Score value — 26sp bold, near-black
                Text(
                    text = score,
                    fontSize = 26.sp,
                    fontFamily = dmSans,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.padding(bottom = 6.dp)
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

@Composable
fun StabilityChart(
    points: List<Float>,
    months: List<String>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val yLabels = listOf("32d", "28d", "24d")

    // Purple layered chart colours
    val chartColor1 = Color(0xFFDED9F0) // Lightest lavender (wide shadow stroke)
    val chartColor2 = Color(0xFFC3B8E8) // Mid purple stroke
    val chartColor3 = Color(0xFFA890D8) // Top/darkest purple line
    val indicatorColor = Color(0xFF7B8F8A) // Sage green dot & dashed line

    Canvas(modifier = modifier) {
        val width  = size.width
        val height = size.height

        // Reserve space: left for Y-labels, bottom for X-labels
        val leftPadding   = 40.dp.toPx()
        val bottomPadding = 22.dp.toPx() // space for "Jan Feb Mar Apr" row
        val topPadding    = 6.dp.toPx()  // small breathing room at top

        val chartWidth  = width - leftPadding
        val chartHeight = height - bottomPadding - topPadding

        val xSpacing = chartWidth / (points.size - 1)

        // ── Y Axis Labels (32d, 28d, 24d) ──────────────────────────────────
        // Gray (0xFF9E9E9E), 11sp, DM Sans Regular, right-aligned before chart area
        yLabels.forEachIndexed { index, label ->
            val yPos = topPadding + (index * (chartHeight / (yLabels.size - 1)))

            val measured = textMeasurer.measure(
                text = label,
                style = TextStyle(
                    fontSize = 11.sp,
                    color = Color(0xFF9E9E9E),
                    fontFamily = dmSans,
                    fontWeight = FontWeight.Normal
                )
            )

            // Right-align so they end just before the chart area starts
            drawText(
                textLayoutResult = measured,
                topLeft = Offset(
                    x = leftPadding - measured.size.width.toFloat() - 4.dp.toPx(),
                    y = yPos - (measured.size.height / 2f)
                )
            )
        }

        // ── X Axis Labels (Jan, Feb, Mar, Apr) ─────────────────────────────
        // Gray for all except "Mar" which is bold near-black, 11sp
        months.forEachIndexed { index, month ->
            val xPos      = leftPadding + (index * xSpacing)
            val isSelected = month == "Mar"

            val measured = textMeasurer.measure(
                text  = month,
                style = TextStyle(
                    fontSize   = 11.sp,
                    color      = if (isSelected) Color(0xFF1A1A1A) else Color(0xFF9E9E9E),
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontFamily = dmSans
                )
            )

            drawText(
                textLayoutResult = measured,
                topLeft = Offset(
                    x = xPos - (measured.size.width / 2f),
                    y = height - measured.size.height.toFloat() // flush to bottom
                )
            )
        }

        // ── Value → Y coordinate ────────────────────────────────────────────
        fun getY(value: Float): Float {
            val minVal = 24f
            val maxVal = 32f
            val normalized = (value - minVal) / (maxVal - minVal)
            return height - bottomPadding - (normalized * chartHeight)
        }

        // ── Build layered paths (main + shadow layers) ──────────────────────
        val mainPath   = Path()
        val midPath    = Path()
        val bottomPath = Path()

        points.forEachIndexed { index, value ->
            val x = leftPadding + (index * xSpacing)
            val y = getY(value)

            if (index == 0) {
                mainPath.moveTo(x, y)
                midPath.moveTo(x, y + 6.dp.toPx())
                bottomPath.moveTo(x, y + 14.dp.toPx())
            } else {
                val prevX = leftPadding + ((index - 1) * xSpacing)
                val prevY = getY(points[index - 1])

                val cx1 = prevX + (x - prevX) / 2f
                val cx2 = prevX + (x - prevX) / 2f

                mainPath.cubicTo(cx1, prevY,               cx2, y,                x, y)
                midPath.cubicTo( cx1, prevY + 6.dp.toPx(), cx2, y + 6.dp.toPx(),  x, y + 8.dp.toPx())
                bottomPath.cubicTo(cx1, prevY + 14.dp.toPx(), cx2, y + 14.dp.toPx(), x, y + 22.dp.toPx())
            }
        }

        // Filled gradient under the main curve
        val fillPath = Path().apply {
            addPath(mainPath)
            lineTo(width, height - bottomPadding)
            lineTo(leftPadding, height - bottomPadding)
            close()
        }
        drawPath(
            fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(chartColor1.copy(alpha = 0.55f), Color.Transparent),
                startY = getY(31f),
                endY   = height - bottomPadding
            )
        )

        // Wide shadow strokes (back → front for correct layering)
        drawPath(bottomPath, color = chartColor1.copy(alpha = 0.55f), style = Stroke(width = 28.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
        drawPath(midPath,    color = chartColor2.copy(alpha = 0.70f), style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
        drawPath(mainPath,   color = chartColor3,                      style = Stroke(width =  3.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))

        // ── March indicator (index 2) ───────────────────────────────────────
        val marchX    = leftPadding + (2 * xSpacing)
        val marchY    = getY(points[2])
        val dotRadius = 7.5f
        // Dot sits on the curve itself (not floated)
        val dotY = marchY

        // Vertical dashed line: from just below dot down to X-axis row
        drawLine(
            color       = indicatorColor,
            start       = Offset(marchX, dotY + dotRadius + 4.dp.toPx()),
            end         = Offset(marchX, height - bottomPadding + 2.dp.toPx()),
            strokeWidth = 1.5f,
            pathEffect  = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
        )

        // Dot marker (sage green circle on curve)
        drawCircle(
            color  = indicatorColor,
            radius = dotRadius,
            center = Offset(marchX, dotY)
        )

        // ── Tooltip ─────────────────────────────────────────────────────────
        val tooltipWidth  = 76.dp.toPx()
        val tooltipHeight = 40.dp.toPx()
        val tooltipX      = marchX - (tooltipWidth / 2f)
        val tooltipY      = dotY - tooltipHeight - 10.dp.toPx()

        // Black rounded rectangle
        drawRoundRect(
            color        = Color(0xFF1A1A1A),
            topLeft      = Offset(tooltipX, tooltipY),
            size         = Size(tooltipWidth, tooltipHeight),
            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
        )

        // Small downward triangle pointer
        val tri = Path().apply {
            moveTo(marchX - 6.dp.toPx(), tooltipY + tooltipHeight)
            lineTo(marchX + 6.dp.toPx(), tooltipY + tooltipHeight)
            lineTo(marchX, tooltipY + tooltipHeight + 6.dp.toPx())
            close()
        }
        drawPath(tri, color = Color(0xFF1A1A1A))

        // Tooltip text: "Stability" / "Improving" — white, 11sp, DM Sans Regular
        val line1 = textMeasurer.measure(
            text  = "Stability",
            style = TextStyle(color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Normal, fontFamily = dmSans)
        )
        val line2 = textMeasurer.measure(
            text  = "Improving",
            style = TextStyle(color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Normal, fontFamily = dmSans)
        )

        val gap           = 2.dp.toPx()
        val totalTxtH     = line1.size.height + line2.size.height + gap
        val textStartY    = tooltipY + (tooltipHeight - totalTxtH) / 2f

        drawText(line1, topLeft = Offset(marchX - line1.size.width / 2f, textStartY))
        drawText(line2, topLeft = Offset(marchX - line2.size.width / 2f, textStartY + line1.size.height + gap))
    }
}
