package com.example.anitest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.anitest.model.Genre
import com.example.anitest.ui.theme.LightOtakuColorScheme
import com.example.myapplication.MyViewModel

@Composable
fun Filter(viewModel: MyViewModel) {
    var filterFlag by remember { mutableStateOf(false) }
    val isLoaded by viewModel.isExploreScreenLoaded.collectAsState()
    val genresList by viewModel.genres.collectAsState()
    val filterRequest by viewModel.filterRequest.collectAsState()
    var genreSelected by remember { mutableStateOf<List<Genre>>(emptyList()) }
    var genreSelectedRemove by remember { mutableStateOf<List<Genre>>(emptyList()) }

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
            containerColor = LightOtakuColorScheme.tertiary,
            contentColor = LightOtakuColorScheme.onTertiary
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
            .background(LightOtakuColorScheme.tertiaryContainer)
            .padding(horizontal = 16.dp),
        expanded = filterFlag && isLoaded,
        onDismissRequest = { filterFlag = false }
    ) {
        if (!filterRequest.isNullOrEmpty()) {
            Button(
                onClick = {
                    viewModel.removeAllFilterRequest()
                    genreSelected = emptyList()
                    genreSelectedRemove = emptyList()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOtakuColorScheme.error
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp),
                    text = "Remove all filter",
                    color = LightOtakuColorScheme.onError,
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
                        checkedColor = LightOtakuColorScheme.secondary,
                        uncheckedColor = LightOtakuColorScheme.tertiary
                    ),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp),
                    text = genre.titolo,
                    color = LightOtakuColorScheme.onTertiaryContainer,
                    fontSize = 16.sp
                )
            }
        }
    }
}
