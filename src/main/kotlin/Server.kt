import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

suspend fun getAvailableModels(): List<String?> {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("http://localhost:11434/api/tags")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val response = connection.inputStream.bufferedReader().readText()
            val jsonResponse = JSONObject(response)
            val modelsArray = jsonResponse.getJSONArray("models")

            List(modelsArray.length()) { i ->
                modelsArray.getJSONObject(i).getString("name")
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            listOf(null)
        }
    }
}


suspend fun generateResponse(prompt: String, model: String?): String {
    return withContext(Dispatchers.IO) {
        if(model==null){
            "No Model is selected"
        }else{
            try {
                val url = URL("http://localhost:11434/api/generate")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Include the model and prompt in the JSON request body
                val jsonInputString = """{"model": "$model", "prompt": "$prompt", "stream": false}"""
                connection.outputStream.use { it.write(jsonInputString.toByteArray()) }

                val response = connection.inputStream.bufferedReader().readText()
                val jsonResponse = JSONObject(response)
                jsonResponse.getString("response")
            } catch (e: Exception) {
                "Error: ${e.localizedMessage}"
            }
        }
    }
}
