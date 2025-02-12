import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.screen.ChatScreen
import ui.theme.AppTheme
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.SwingUtilities


@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    fun addToSystemTray() {
        if (!SystemTray.isSupported()) return

        val tray = SystemTray.getSystemTray()
        val image = Toolkit.getDefaultToolkit().getImage("icon.png") // Path to icon

        val popup = PopupMenu()
        val exitItem = MenuItem("Exit").apply {
            addActionListener { exitApplication() }
        }

        popup.add(exitItem)

        val trayIcon = TrayIcon(image, "Ollama", popup).apply {
            isImageAutoSize = true
        }

        SwingUtilities.invokeLater {
            tray.add(trayIcon)
        }
    }
    addToSystemTray()
    Window(
        onCloseRequest = ::exitApplication,
        undecorated = true, // Removes default title bar
        resizable = true
    ) {
        val window = this.window
        var offset by remember { mutableStateOf(Point(0, 0)) }

        LaunchedEffect(Unit) {
            window.minimumSize = Dimension(600, 400)
        }

        AppTheme {
            Column(modifier = Modifier.fillMaxSize()) {
                // Custom Title Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 10.dp)
                        .pointerMoveFilter(
                            onEnter = { window.cursor = Cursor(Cursor.HAND_CURSOR); false },
                            onExit = { window.cursor = Cursor(Cursor.DEFAULT_CURSOR); false }
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Ollama", color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))

                    Row {
                        Text(
                            "—", color = Color.White, modifier = Modifier
                                .padding(8.dp).clickable { window.isVisible = false })

                        Text(
                            "✕", color = Color.White, modifier = Modifier
                                .padding(8.dp)
                                .clickable { exitApplication() })
                    }
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    ChatScreen()
                }
            }
        }

        // Drag window
        window.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                offset = e.point
            }
        })

        window.addMouseMotionListener(object : MouseAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                window.setLocation(e.locationOnScreen.x - offset.x, e.locationOnScreen.y - offset.y)
            }
        })
    }
}


