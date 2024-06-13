package com.example.anitest.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Download
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun EpisodeButton(name: String, quality: String, index: Number, isDubbed: Boolean, onWatch: ()-> Unit, onDownload: ()-> Unit) {

    val principalColor = Color(112, 82, 137)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(3.dp))
            .background(principalColor)
            .padding(start = 8.dp)
            .padding(bottom = 10.dp, end = 4.dp, top = 10.dp)
            .shadow(16.dp)
            .clickable { onWatch() }
    ) {
        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Watch episode", tint = Color.White)
        Text(
            text = name,
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(
            Modifier
                .weight(1f)
                .background(Color.Green)
        )
        if(isDubbed){
            Text(
                text = "Dub",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color(129, 81, 86))
                    .padding(vertical = 3.dp, horizontal = 12.dp)
            )
        }
        Text(
            text = quality,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
        Text(
            text = "$index°",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(horizontal = 4.dp)
        )
        IconButton(
            onClick = { onDownload() },
            modifier = Modifier
                .height(22.dp)
            ){
            Icon(imageVector = Icons.Filled.Download,
                contentDescription = "Watch episode",
                tint = principalColor,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
                    .padding(3.dp)
            )
        }
    }
}

@Composable
fun test(){
    var showPlayer by remember { mutableStateOf(false) }

    EpisodeButton(name = "Doraemon",
        quality = "1080P", index = 1, isDubbed = true, {showPlayer = true}, {})
    if(showPlayer){
        Dialog(onDismissRequest = {}){
            VideoPlayer(url = "https://gredirect.info/download.php?url=aHR0cHM6LyAdeqwrwedffryretgsdFrsftrsvfsfsr9pMms4bWAdrefsdsdfwerFrefdsfrersfdsrfer36343534M1eTRrLmFuZjU5OC5jb20vdXNlcjEzNDIvMDMwZDRjNGJhZWE0ODY2Zjg4M2ZkMjFkZTU0N2U2OTUvRVAuMS52MS4xNzAxNTg1MzA5LjM2MHAubXA0P3Rva2VuPThyUjJzUWYxS0ExU3ZjSFJiSlhoMVEmZXhwaXJlcz0xNzE4MjgyNDUwJmlkPTQyOTU3")
        }

    }
}