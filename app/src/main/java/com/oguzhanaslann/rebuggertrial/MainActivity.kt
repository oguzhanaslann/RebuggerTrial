package com.oguzhanaslann.rebuggertrial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oguzhanaslann.rebuggertrial.ui.theme.RebuggerTrialTheme
import com.theapache64.rebugger.Rebugger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RebuggerTrialTheme { // A surface container using the 'background' color from the theme
                val text = remember { mutableStateOf("Hello, World!") }
                MainScreen(
                    text = text.value,
                    onClick = { count ->
                        text.value = if (count % 2 == 0) {
                            randomString()
                        } else {
                            "Hello World!"
                        }
                    }
                )
            }
        }
    }

    private fun randomString(): String {
        return listOf(
            "Hello World!",
            "lorem",
            "ipsum",
            "dolor",
            "sit",
        ).random()
    }
}

@Composable
fun MainScreen(
    text : String = "Hello World!",
    onClick: (Int) -> Unit = {}
) {

    val count = remember { mutableStateOf(0) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    count.value++
                    onClick(count.value)
                }
            ) {

                Rebugger(
                    trackMap = mapOf(
                        "count" to count.value,
                        "text" to text
                    ),
                    composableName = "MainScreen Button",
                    logger = { tag, message -> Log.e(tag, message) }
                )

                Text(text = "Hello World!" + count.value)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RebuggerTrialTheme {
        MainScreen()
    }
}

