import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.screen.ChatScreen
import ui.theme.AppTheme


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        AppTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                ChatScreen()
            }
        }
    }
}

