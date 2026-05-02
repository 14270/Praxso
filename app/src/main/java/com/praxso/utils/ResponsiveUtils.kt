package com.praxso.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun responsiveDp(base: Dp, maxWidth: Dp): Dp {
    val scale = maxWidth / 400.dp
    return base * scale
}