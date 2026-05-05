package com.praxso.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.praxso.R
import com.praxso.ui.theme.PraxsoTheme
import com.praxso.ui.theme.dmSans

// ── Design Tokens ────────────────────────────────────────
private val NavBackground      = Color(0xF2FFFFFF)
private val NavSelectedColor   = Color(0xFF000000)
private val NavUnselectedColor = Color(0x66000000)
private val BorderColor        = Color(0xFFEAEAEA)

@Composable
fun BottomNav(
    selectedIndex: Int = 2,
    onItemSelected: (Int) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding() // ✅ handles system bottom space
            .padding(bottom = 12.dp), // ✅ pushes UI slightly upward
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ── Navigation Pill ─────────────────────
            Row(
                modifier = Modifier
                    .width(270.dp)
                    .height(64.dp)
                    .clip(RoundedCornerShape(50))
                    .background(NavBackground)
                    .border(1.dp, BorderColor, RoundedCornerShape(50))
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                NavItem(
                    iconRes = R.drawable.home_ic,
                    label = "Home",
                    selected = selectedIndex == 0,
                    onClick = { onItemSelected(0) }
                )

                NavItem(
                    iconRes = R.drawable.track_ic_,
                    label = "Track",
                    selected = selectedIndex == 1,
                    onClick = { onItemSelected(1) }
                )

                NavItem(
                    iconRes = R.drawable.insights_ic_,
                    label = "Insights",
                    selected = selectedIndex == 2,
                    onClick = { onItemSelected(2) }
                )
            }

            // ── Floating Add Button ─────────────────
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .offset(y = (-2).dp)
                    .shadow(10.dp, CircleShape, clip = false)
                    .background(Color.White, CircleShape)
                    .border(1.dp, BorderColor, CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {},
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_ic),
                    contentDescription = "Add",
                    tint = NavSelectedColor,
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

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(23.dp)
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = label,
            color = tint,
            fontSize = 10.5.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            fontFamily = dmSans
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun BottomNavPreview() {
    PraxsoTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNav()
        }
    }
}