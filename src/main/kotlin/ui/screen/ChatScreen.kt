@file:Suppress("DEPRECATION")

package ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val logo = "drawable/logo.svg"
    var messages = remember { mutableListOf<String>() }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(logo),
                            contentDescription = "navigation",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                title = { Text("Ollama") }
            )
        }
    ) { paddingValues ->
        if (messages.isEmpty()) {
            Column(
                Modifier.padding(paddingValues).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painterResource(logo), "logo", Modifier.size(100.dp))
            }
        } else {
            LazyColumn(Modifier.padding(paddingValues).fillMaxSize()) {

            }
        }
    }
}