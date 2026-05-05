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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.drawText
import androidx.compose.ui.tooling.preview.Preview
import com.praxso.ui.theme.PraxsoTheme
import com.praxso.ui.theme.dmSans

@Composable
fun WeightChart(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF5FAF9)) // ✅ ADD THIS LINE
            .padding(vertical = 12.dp)
    ) {

        Text(
            text = "Body & Metabolic Trends",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = dmSans,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(Modifier.padding(20.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = "Your weight",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = dmSans
                        )
                        Text(
                            text = "in kg",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontFamily = dmSans
                        )
                    }

                    Row(
                        modifier = Modifier
                            .width(110.dp)
                            .height(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(50.dp, 32.dp)
                                .background(Color.Black, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Monthly", color = Color.White, fontSize = 10.sp)
                        }

                        Spacer(Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .size(50.dp, 32.dp)
                                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Weekly", color = Color.Gray, fontSize = 10.sp)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                Box(modifier = Modifier.fillMaxSize()) {

                    val textMeasurer = androidx.compose.ui.text.rememberTextMeasurer()

                    androidx.compose.foundation.Canvas(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        val width = size.width
                        val height = size.height

                        val leftPadding = 30.dp.toPx()
                        val bottomPadding = 25.dp.toPx()

                        val chartWidth = width - leftPadding
                        val chartHeight = height - bottomPadding

                        val xSpacing = chartWidth / 4

                        // Y Grid
                        val ySteps = listOf(25, 50, 75)

                        ySteps.forEach { step ->
                            val yPos = chartHeight - ((step - 25) / 50f * chartHeight)

                            drawLine(
                                color = Color(0xFFEEEEEE),
                                start = Offset(leftPadding, yPos),
                                end = Offset(width, yPos),
                                strokeWidth = 1.dp.toPx()
                            )

                            drawText(
                                textLayoutResult = textMeasurer.measure(step.toString()),
                                topLeft = Offset(0f, yPos)
                            )
                        }

                        // X Labels
                        val months = listOf("Jan", "Feb", "Mar", "Apr", "May")

                        months.forEachIndexed { i, m ->
                            val x = leftPadding + i * xSpacing
                            drawText(
                                textLayoutResult = textMeasurer.measure(m),
                                topLeft = Offset(x, height - 20.dp.toPx())
                            )
                        }

                        val points = listOf(0.1f, 0.35f, 0.22f, 0.85f, 0.55f)

                        val pts = points.mapIndexed { i, v ->
                            Offset(
                                leftPadding + i * xSpacing,
                                chartHeight - (v * chartHeight)
                            )
                        }

                        // ✅ Smooth Curve (FIXED)
                        val path = Path()
                        val tension = 0.25f

                        path.moveTo(pts[0].x, pts[0].y)

                        for (i in 1 until pts.size) {

                            val p0 = pts.getOrNull(i - 2) ?: pts[i - 1]
                            val p1 = pts[i - 1]
                            val p2 = pts[i]
                            val p3 = pts.getOrNull(i + 1) ?: p2

                            val cp1x = p1.x + (p2.x - p0.x) * tension
                            val cp1y = p1.y + (p2.y - p0.y) * tension

                            val cp2x = p2.x - (p3.x - p1.x) * tension
                            val cp2y = p2.y - (p3.y - p1.y) * tension

                            path.cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
                        }

                        // Fill
                        val fillPath = Path().apply {
                            addPath(path)
                            lineTo(leftPadding + chartWidth, chartHeight)
                            lineTo(leftPadding, chartHeight)
                            close()
                        }

                        drawPath(
                            path = fillPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x75D27A88),
                                    Color(0x00D27A88)
                                ),
                                startY = 0f,
                                endY = chartHeight
                            )
                        )

                        // Line
                        drawPath(
                            path = path,
                            color = Color(0xFFD27A88),
                            style = Stroke(
                                width = 3.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )

                        // Points
                        pts.forEach {
                            drawCircle(
                                color = Color(0xFFD27A88),
                                radius = 6.dp.toPx(),
                                center = it
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 3.dp.toPx(),
                                center = it
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true) @Composable fun WeightChartPreview() { PraxsoTheme { Box(modifier = Modifier.padding(16.dp)) { WeightChart() } } }