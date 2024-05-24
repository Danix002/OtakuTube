package com.example.anitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.anitest.ui.componets.CategoryRow
import com.example.anitest.ui.theme.ANITESTTheme
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ANITESTTheme {

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //getListOfAnime(viewModel, "action")
                    CategoryRow(viewModel, category = "action")
                }

            }
        }

    }
}
/**
fun getListOfAnime(viewModel : MyViewModel, genre: String) {
    var actionAnimeList = viewModel.getAnimeByGenre(1, genre)
    println(actionAnimeList)
    println(actionAnimeList[0].name)
}
 **/

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


