package com.example.anitest.ui.componets

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun AppBar(): Unit {
    var searchString : String by remember { mutableStateOf("") }
    var hideKeyboard by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    fun updateSearch(x: String): Unit {
        searchString = x
    }

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back button" )
            }
        },
        title = {
             TextField(
                 modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                 keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                 keyboardActions = KeyboardActions(onSearch = {
                     focusManager.clearFocus()
                     //onSearch(text)
                 }),
                 singleLine = true,
                 leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "" )},
                 value = searchString, onValueChange = { value: String -> updateSearch(value) },
                 placeholder = { Text(text = "Search anime...")} )
        }
    )

}



