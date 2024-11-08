package com.app.finansmart.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FinanOutlinedButton(
    onClick: () -> Unit,
    text: String,
    borderStroke: BorderStroke = BorderStroke(2.dp, Color(0xFFEBFFDD)),
    containerColor: Color = Color(0xFF494B4D),
    contentColor: Color = Color.White,
    width: Dp = 200.dp,
    height: Dp = 60.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 120.dp
) {
    OutlinedButton(
        onClick = onClick,
        border = borderStroke,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = Modifier
            .size(width, height)
            .offset(x = offsetX, y = offsetY)
    ) {
        Text(text = text)
    }
}
