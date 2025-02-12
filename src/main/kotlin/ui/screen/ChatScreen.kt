@file:Suppress("DEPRECATION")

package ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val logo = "drawable/logo.svg"
    var prompt by remember { mutableStateOf("") }
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
                title = { Text("Ollama") },
            )
        },
        bottomBar = {
            Row(Modifier.fillMaxWidth().padding(20.dp).padding(horizontal = 30.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = prompt,
                    onValueChange = { newText -> prompt = newText },
                    label = { Text("Enter Text") },
                    shape = CircleShape,
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                Spacer(Modifier.width(10.dp))
                Button(onClick = {}, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(50.dp)) {
                    Icon(Icons.Rounded.KeyboardArrowRight, "enter")
                }
            }
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