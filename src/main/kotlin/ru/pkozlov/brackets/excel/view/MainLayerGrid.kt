package ru.pkozlov.brackets.excel.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.MainLayerGrid(content: @Composable BoxScope.() -> Unit) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .weight(1f)
    ) {
        content()
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(0.3f)
    )
}