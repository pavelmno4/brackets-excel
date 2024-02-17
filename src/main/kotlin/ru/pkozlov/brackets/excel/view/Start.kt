package ru.pkozlov.brackets.excel.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.kodein.di.instance
import ru.pkozlov.brackets.excel.core.di.di
import ru.pkozlov.brackets.excel.core.service.BracketService
import ru.pkozlov.brackets.excel.view.shapes.Triangle

@Composable
fun Start(files: MutableState<List<String>>) {
    val bracketService: BracketService by di.instance()

    OutlinedButton(
        onClick = { bracketService.createBrackets(files.value) },
        modifier = Modifier
            .padding(top = 10.dp)
            .size(width = 160.dp, height = 160.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = greenColor),
        shape = Triangle()
    ) {
        Text(
            text = "Start",
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}