package com.example.anitest

import android.os.Bundle
import android.content.res.Configuration
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.anitest.navigation.Navigation
import com.example.anitest.navigation.Screen
import com.example.anitest.ui.theme.ANITESTTheme
import com.example.myapplication.MyViewModel


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ANITESTTheme {
                MyActivityContent()
            }
        }
    }

    @Composable
    fun MyActivityContent() {
        val configuration = resources.configuration
        var loading by remember {
            mutableStateOf(true)
        }
        val connection by viewModel.connection.collectAsState()

        LaunchedEffect(Unit) {
            if (!viewModel.testConnection()) {
                viewModel.selectedNavItem.value = Screen.Library.route
            }
            loading = false
        }

        val window = this@MainActivity.window
        SideEffect {
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                window.statusBarColor = Color.Transparent.toArgb()
                window.navigationBarColor = Color.Transparent.toArgb()
                window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        )
            } else {
                window.statusBarColor = Color(102, 90, 110).toArgb()
                window.navigationBarColor = Color(102, 90, 110).toArgb()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            if (loading) {
                Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
                    Image(painter = painterResource(id = R.drawable.app_icon), contentDescription = "" )
                }
            } else {
                Navigation(viewModel, LocalContext.current,
                    if (connection) Screen.Home.route else Screen.Library.route
                )
            }

            if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black))
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


