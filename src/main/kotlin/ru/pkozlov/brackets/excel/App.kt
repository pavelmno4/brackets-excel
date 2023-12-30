import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import ru.pkozlov.brackets.excel.core.dto.*
import ru.pkozlov.brackets.excel.core.service.BracketGenerationService
import ru.pkozlov.brackets.excel.core.service.FileService
import ru.pkozlov.brackets.excel.core.service.TemplateService
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Year
import java.util.*

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

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}