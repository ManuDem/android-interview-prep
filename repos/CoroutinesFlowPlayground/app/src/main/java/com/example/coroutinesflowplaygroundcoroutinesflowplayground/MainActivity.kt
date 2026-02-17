package com.example.coroutinesflowplaygroundcoroutinesflowplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coroutinesflowplaygroundcoroutinesflowplayground.ui.theme.CoroutinesFlowPlaygroundCoroutinesFlowPlaygroundTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinesFlowPlaygroundCoroutinesFlowPlaygroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CoroutinesFlowDemo(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CoroutinesFlowDemo(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    var status by remember { mutableStateOf("Idle") }
    var logs by remember { mutableStateOf(listOf<String>()) }
    var streamJob by remember { mutableStateOf<Job?>(null) }

    fun stopStream() {
        streamJob?.cancel()
        streamJob = null
        status = "Stopped"
    }

    Column(modifier.padding(16.dp)) {

        Text(text = "Status: $status")
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                stopStream()
                logs = emptyList()
                status = "Loading once..."

                scope.launch {
                    logs = fakeApiOnce()
                    status = "Loaded once ✅ (suspend)"
                }
            }) { Text("suspend") }

            Button(onClick = {
                stopStream()
                logs = emptyList()
                status = "Streaming..."

                streamJob = scope.launch {
                    fakeApiStream().collect { event ->
                        logs = logs + event
                    }
                    status = "Stream completed ✅ (Flow)"
                }
            }) { Text("Flow") }
        }

        Spacer(Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(Modifier.height(12.dp))

        LazyColumn {
            items(logs) { line ->
                Text("• $line")
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}

private suspend fun fakeApiOnce(): List<String> {
    delay(1200)
    return listOf("Item A", "Item B", "Item C")
}

private fun fakeApiStream(): Flow<String> = flow {
    for (i in 1..8) {
        delay(350)
        emit("Event #$i")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutinesFlowPlaygroundCoroutinesFlowPlaygroundTheme {
        Greeting("Android")
    }
}
