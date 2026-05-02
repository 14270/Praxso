package com.praxso.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.praxso.ui.theme.dmSans
import com.praxso.R

import androidx.compose.ui.platform.LocalDensity

@Composable
fun Header(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val topPaddingLogo = with(density) { 68.toDp() }
    val topPaddingText = with(density) { 69.5f.toDp() }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // Logo on the left with 16dp padding
        Image(
            painter = painterResource(id = R.drawable.group_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(top = topPaddingLogo, start = 16.dp)
                .size(24.dp)
        )

        Text(
            text = "Insights",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = dmSans,
            color = Color.Black,
            letterSpacing = (-0.02).times(16).sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topPaddingText)
        )
    }
}
