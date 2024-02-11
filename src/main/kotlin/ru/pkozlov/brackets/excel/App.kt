package ru.pkozlov.brackets.excel

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.kodein.di.instance
import ru.pkozlov.brackets.excel.core.di.di
import ru.pkozlov.brackets.excel.core.service.BracketService

@Preview
@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

//fun main() = application {
//    Window(onCloseRequest = ::exitApplication) {
//        App()
//    }
//}

fun main(): Unit {
    val bracketService: BracketService by di.instance()

    val files: List<String> = listOf(
        "/Users/ruakzp8/Documents/bracket/Юноши 2004-2005.xlsx"
    )

    bracketService.createBrackets(files)
}