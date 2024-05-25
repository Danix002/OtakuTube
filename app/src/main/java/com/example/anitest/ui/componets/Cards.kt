package com.example.anitest.ui.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.anitest.model.Anime


@Composable
fun AnimeCard( anime: Anime) {
    Column (
        modifier = Modifier
            .width(128.dp)
            //.background(Color.Red)
    ) {
        Image(
            painter = rememberAsyncImagePainter(anime.img_url),
            contentDescription = "anime image",
            modifier = Modifier
                .height(180.dp)
                .width(128.dp)
                .clip(RoundedCornerShape(8.dp))
                //.background(Color.Yellow)

        )
        Text(
            text = anime.name,
            color = Color.Black,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                //.background(Color.Black)
        )
    }
}