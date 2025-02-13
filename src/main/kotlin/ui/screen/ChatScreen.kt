@file:Suppress("DEPRECATION")

package ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val logo = "drawable/logo.svg"
    var prompt by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<String>()) }
    val scope = rememberCoroutineScope()

    fun sendMessage() {
        if (prompt.isNotBlank()) {
            messages = messages + "You: $prompt"
            val userMessage = prompt
            prompt = ""

            scope.launch {
                val response = generateResponse(userMessage)
                messages = messages + "Ollama: $response"
            }
        }
    }

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
            Row(
                Modifier.fillMaxWidth().padding(20.dp).padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = prompt,
                    onValueChange = { newText -> prompt = newText },
                    label = { Text("Enter Text") },
                    shape = CircleShape,
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = { sendMessage() }
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                Spacer(Modifier.width(10.dp))
                Button(onClick = { sendMessage() }, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(50.dp)) {
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
            LazyColumn(Modifier.padding(paddingValues).padding(16.dp).fillMaxSize()) {
                items(messages.size) { index ->
                    ChatBubble(messages[index], index % 2 == 0)
                }
            }
        }
    }
}


suspend fun generateResponse(prompt: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("http://localhost:11434/api/generate")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            // Include the model and prompt in the JSON request body
            val jsonInputString = """{"model": "qwen2.5-coder:0.5b", "prompt": "$prompt", "stream": false}"""
            connection.outputStream.use { it.write(jsonInputString.toByteArray()) }

            val response = connection.inputStream.bufferedReader().readText()
            val jsonResponse = JSONObject(response)
            jsonResponse.getString("response")
        } catch (e: Exception) {
            "Error: ${e.localizedMessage}"
        }
    }
}