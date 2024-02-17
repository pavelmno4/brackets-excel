package ru.pkozlov.brackets.excel.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun BoxScope.ElementsUnderlayer(content: @Composable ColumnScope.() -> Unit) {
    Row {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.1f)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12))
                .weight(1f)
                .background(Color.DarkGray)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.1f)
        )
    }
}