package com.example.kmpdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlin.time.Duration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
//            App()
            // 1.4-组合、重组作用域和 remember()
            test_1_4()
        }
    }
}

@Composable
private fun MainActivity.test_1_4() {
    Column(Modifier.safeContentPadding()) {
        // 包成函数类型对象，需要时再执行
        var name by remember { mutableStateOf("邱珑") }

        Button({}) {
            Text(name)
        }

        lifecycleScope.launch {
            delay(3000)
            name = "皮皮爽"
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}