package com.example.kmpdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    // 这个方法写在最后才有效
                    .verticalScroll(rememberScrollState())
            ) {
//            App()
                // 1.4-组合、重组作用域和 remember()
                test_1_4()
                // 基础组件在compose中的替代物
                test_1_5()
                //1.7-Modifier 到底是什么：强大又难懂的核心概念
                test_1_7()
            }
        }
    }
}

@Composable
private fun test_1_7() {
    Column(
        Modifier
            .background(Color.Red)
            // Modifier不是builder设计模式
            // Modifier的调用顺序对结果有影响，这里代表先绘制一个红色背景，再在基础上加padding
            .padding(8.dp)
            // 圆角
            .background(Color.Blue, RoundedCornerShape(24.dp))
            // 点击的响应范围在padding里面，不包括padding
            .clickable {}
            .padding(8.dp)
            .padding(8.dp)
    ) {
        Image(
            painterResource(R.drawable.ic_launcher_background),
            "头像",
            // 圆形图片
            Modifier
                // 处理背景色
//                .background(Color.Blue, RoundedCornerShape(24.dp))
                // 前景背景都处理
                .clip(CircleShape)
                // 设置尺寸
                .size(150.dp)
//                .fillMaxWidth()
//                .fillMaxWidth()
        )
        Text("皮皮爽", Modifier.clickable {})
        Button(
            {},
            colors = ButtonColors(Color.White, Color.Blue, Color.Green, Color.Red)
        ) {
            Text("点击")
        }
    }
}

@Composable
private fun test_1_5() {
    val nums = (1..100).toList()
    Column(Modifier.safeContentPadding()) {
        var name by remember { mutableStateOf("邱珑") }
        Text(
            text = name,
            modifier = Modifier
                .safeContentPadding()
                .background(Color.Blue),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        Image(painterResource(R.drawable.ic_launcher_background), "头像")
        Button({
            name = "点了"
        }) {
            Text(
                text = name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }
        // LinearLayout == Column、Row
        // FrameLayout == Box、setContent摆放规则跟Box类似
        // RelativeLayout == Row + Column + Box
        // RecycleView = LazyColumn、LazyRow
        LazyColumn(
            Modifier
                .background(Color.Green)
                .height(200.dp)
        ) {
            item {
                Text(
                    text = "Top",
                )
            }
            items(nums.size) {
                Text(
                    text = "第 $it 项",
                )
            }
            item {
                Text(
                    text = "Bottom",
                )
            }
        }
        // ScrollView == Modifier.verticalScroll
        Column(
            Modifier
                .background(Color.Green)
                .height(200.dp)
                // 这个方法写在最后才有效
                .verticalScroll(rememberScrollState())

        ) {
            Text(
                text = "Top",
            )
            for (num in nums) {
                Text(
                    text = "第 $num 项",
                )
            }
            Text(
                text = "Bottom",
            )
        }
        // ViewPage == HorizontalPager()
        HorizontalPager(
            modifier = Modifier
                .background(Color.Red)
                .height(50.dp), state = rememberPagerState() { 2 }) {
            Text(
                text = "Top",
            )
            Text(
                text = "Bottom",
            )
        }
    }

}

@Composable
private fun MainActivity.test_1_4() {
    WeComposeTheme {
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
}

@Preview
@Composable
fun AppAndroidPreview() {
//    App()
    test_1_7()
}

@Composable
fun CharCounter(value: String) {
    // remember会防止重复初始化，
    // 在传入的值改变时需要重复初始化的情况，可以设置value，value改变就会重新初始化
    val length = remember(value) { value.length }
    Text("字符串的长度是：${length}")
}