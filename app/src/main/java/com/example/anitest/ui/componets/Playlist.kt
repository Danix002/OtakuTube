package com.example.anitest.ui.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.anitest.room.PlaylistEntity

@Composable
fun PlaylistCard(playlist: PlaylistEntity): Unit {
    val playlistTest : PlaylistEntity = PlaylistEntity("PreferitiPreferitiPreferitiPreferiti", "https://static.wikia.nocookie.net/dubbing9585/images/0/07/Ranking_of_Kings.jpg/revision/latest?cb=20230214064655");
    Box (modifier = Modifier
        .width(164.dp)
        .padding(vertical = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .rotate(-8f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
                .height(180.dp)
                .width(128.dp)
        )
        Box(
            modifier = Modifier
                .rotate(8f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.DarkGray)
                .height(180.dp)
                .width(128.dp)
        )
        Image(
            painter = rememberAsyncImagePainter(playlistTest.img),
            contentDescription = "anime image",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .height(180.dp)
                .width(128.dp)
        )
        Box (  contentAlignment = Alignment.Center, modifier = Modifier.padding(bottom = 8.dp).width(128.dp) ) {
            Box (
                Modifier
                    .blur(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.5f))
                    .fillMaxWidth()
                    .height(20.dp))
            Text( maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(horizontal = 12.dp), text = playlist.name, color = Color.White)
        }
    }
}