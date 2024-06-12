package com.example.anitest.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun AnimeTitles( /** name: String, titles: List<String> **/ ) {
    var expandedTitles by remember {
        mutableStateOf(false)
    }
    val name =  "Shinjeki no Kyojin"
    val titles = listOf<String>("進撃の巨人", "Attack on Titan",  "OtherName")
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black.copy(alpha = 0.9f))
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier
            .width(25.dp)
            .padding(start = 12.dp, end = 12.dp)
            .background(Color.Red)
            .height(20.dp)
        )
        Text(
            text = titles[0],
            color = Color.White,
            fontSize = 20.sp
        )
        Spacer(
            Modifier
                .weight(1f)
                .background(Color.Green))
        Box(){
            IconButton( onClick = { expandedTitles = expandedTitles.not() }) {
                Icon(tint = Color.White, imageVector = Icons.Filled.MoreVert, contentDescription = "" )
            }
            DropdownMenu(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black)
                    .padding(horizontal = 16.dp),
                expanded = expandedTitles,
                onDismissRequest = { expandedTitles = false }) {
                titles.subList(1, titles.size).forEach {
                    Text( modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp) , text = it, color = Color.White)

                }
            }
        }

    }
}