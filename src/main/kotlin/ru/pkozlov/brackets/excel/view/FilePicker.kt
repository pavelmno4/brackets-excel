package ru.pkozlov.brackets.excel.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.swing.JFileChooser
import javax.swing.UIManager

@Composable
fun FilePicker(files: MutableState<List<String>>) {
    var text by remember { mutableStateOf("Files..") }
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                pickFiles()?.let { updatedFilesList ->
                    files.value = updatedFilesList.map { it.absolutePath }
                    text = updatedFilesList.joinToString { it.name }
                }
            }
        },
        modifier = Modifier
            .size(width = 450.dp, height = 54.dp)
            .padding(
                horizontal = 10.dp,
                vertical = 2.dp
            )
    ) {
        Text(
            text = text,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private suspend fun pickFiles(): List<File>? = withContext(Dispatchers.IO) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val chooser = JFileChooser().apply {
        fileSelectionMode = JFileChooser.FILES_ONLY
        isMultiSelectionEnabled = true
        isVisible = true
    }

    when (val code = chooser.showOpenDialog(null)) {
        JFileChooser.APPROVE_OPTION -> chooser.pickedFiles
        JFileChooser.CANCEL_OPTION -> null
        JFileChooser.ERROR_OPTION -> error("An error occurred while executing JFileChooser::showOpenDialog")
        else -> error("Unknown return code '${code}' from JFileChooser::showOpenDialog")
    }
}

private val JFileChooser.pickedFiles: List<File>
    get() = when {
        selectedFiles.isNotEmpty() -> selectedFiles.toList()
        selectedFile != null -> listOf(selectedFile)
        else -> emptyList()
    }