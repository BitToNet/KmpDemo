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
        // by 方法是为了提供getValue和setvalue功能，防止每次传递mutableState，而是传递的mutableState.value
        // var name by mutableStateOf("邱珑")
        // var name = mutableStateOf("邱珑")
        // by和=的区别：
        // 1、=传递的是mutableState对象，
        //      by传递的是mutableState.value，所以使用by生成的name的时候,直接用 Text(name)
        //      而使用=生成的name的时候,需要加.value Text(name.value)
        // 2.第二点不同是
        //      =生成的name，他的监听可以传递，
        //      by不行，比如 val name1 = name; name1改变的时候，不会引发重组

        // remember 是为了在name值被修改的时候，引发页面重组，导致name的初始化重复执行
        // 包成函数类型对象，需要时再执行
        // remember起到缓存作用，第一次调用我的时候，我会去执行，第二次调用会把缓存给你
        var name by remember { mutableStateOf("邱珑") }

        // 不使用remember的情况下，text和mutableState在同一层，text重组会导致name重新初始化
        // 包了remember以后他们在同一层也可以使用了
        Text(name)

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