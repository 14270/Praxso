package com.praxso.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.praxso.R
import com.praxso.ui.theme.dmSans

// ── Design Tokens ────────────────────────────────────────
private val NavBackground      = Color.White
private val NavSelectedColor   = Color(0xFF1A1A1A)   // Black
private val NavUnselectedColor = Color(0xFF9E9E9E)   // Gray
private val ShadowColor        = Color(0x0F000000)   // Even softer shadow for premium feel

@Composable
fun BottomNav(
    selectedIndex: Int = 2,          // 0=Home, 1=Track, 2=Insights
    onItemSelected: (Int) -> Unit = {}
) {
    // Outer container for floating position
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 28.dp), // Added top padding to create space from content
        contentAlignment = Alignment.Center
    ) {
        // Main Row with 343dp width and 64dp height for more breathing room
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Navigation Pill
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .shadow(
                        elevation = 15.dp, 
                        shape = RoundedCornerShape(32.dp), 
                        clip = false, 
                        ambientColor = ShadowColor, 
                        spotColor = ShadowColor
                    )
                    .background(NavBackground, RoundedCornerShape(32.dp))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NavItem(
                    iconRes = R.drawable.frame,
                    label = "Home",
                    selected = selectedIndex == 0,
                    onClick = { onItemSelected(0) }
                )
                NavItem(
                    iconRes = R.drawable.frame2,
                    label = "Track",
                    selected = selectedIndex == 1,
                    onClick = { onItemSelected(1) }
                )
                NavItem(
                    iconRes = R.drawable.frame3,
                    label = "Insights",
                    selected = selectedIndex == 2,
                    onClick = { onItemSelected(2) }
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 2. Separate Plus Circle
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .shadow(
                        elevation = 15.dp, 
                        shape = CircleShape, 
                        clip = false, 
                        ambientColor = ShadowColor, 
                        spotColor = ShadowColor
                    )
                    .background(NavBackground, CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { /* Add action */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.frame4),
                    contentDescription = "Add",
                    tint = NavUnselectedColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    iconRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (selected) NavSelectedColor else NavUnselectedColor
    val labelWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(vertical = 4.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(22.dp) // Adjusted to match Figma proportion
        )
        Spacer(modifier = Modifier.height(4.dp)) // Neat spacing between icon and text
        Text(
            text = label,
            color = tint,
            fontSize = 11.sp,
            fontWeight = labelWeight,
            fontFamily = dmSans,
            letterSpacing = 0.sp
        )
    }
}