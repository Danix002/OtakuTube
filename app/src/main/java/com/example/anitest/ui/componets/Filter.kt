package com.example.anitest.ui.componets

import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.anitest.model.Genre
import com.example.myapplication.MyViewModel

@Composable
fun Filter(viewModel: MyViewModel) {
    var filterFlag by remember { mutableStateOf(false) }
    val isLoaded by viewModel.isExploreScreenLoaded.collectAsState()
    val genresList by viewModel.genres.collectAsState()
    val filterRequest by viewModel.filterRequest.collectAsState()
    var genreSelected by remember { mutableStateOf<List<Genre>>(listOf(Genre("", ""))) }
    var genreSelectedRemove by remember { mutableStateOf<List<Genre>>(listOf(Genre("", ""))) }

    LaunchedEffect (genreSelected){
        genreSelected.forEach {
            if(it.id != "" && it.titolo != "") {
                viewModel.addFilterRequest(it)
            }
        }
    }

    LaunchedEffect (genreSelectedRemove){
        genreSelectedRemove.forEach {
            if(it.id != "" && it.titolo != "") {
                viewModel.removeFilterRequest(it)
            }
        }
    }

    Button(onClick = { filterFlag = true },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(129, 81, 86),
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(30.dp))
            .fillMaxHeight()
    ) {
        //Text(text = "Filter")
        Icon(imageVector = Icons.Filled.FilterList, contentDescription = "Filter Genre")
    }

    DropdownMenu(
        modifier = Modifier
            .height(300.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(255, 218, 220))
            .padding(horizontal = 16.dp),
        expanded = filterFlag && isLoaded,
        onDismissRequest = { filterFlag = false }
    ) {
        if (!filterRequest.isNullOrEmpty()) {
            Button(
                onClick = {
                    filterRequest?.forEach {
                        viewModel.removeFilterRequest(it)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(129, 81, 86)
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp),
                    text = "Remove all filter",
                    color = Color(255, 218, 220),
                    fontSize = 16.sp
                )
            }
        }
        genresList.forEach { genre ->
            val isSelected = filterRequest?.contains(genre) == true
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = {
                        if (it) {
                            genreSelectedRemove = genreSelectedRemove - genre
                            genreSelected = genreSelected + genre
                        } else {
                            genreSelected = genreSelected - genre
                            genreSelectedRemove = genreSelectedRemove + genre
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(102, 90, 110),
                        uncheckedColor = Color(129, 81, 86)
                    ),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp),
                    text = genre.titolo,
                    color = Color(129, 81, 86),
                    fontSize = 16.sp
                )
            }
        }
    }
}
