package com.example.anitest.ui.componets

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("SourceLockedOrientationActivity", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun test(context: Context){
    var showPlayer by remember { mutableStateOf(false) }

    println("######################")
    println(showPlayer)
    println("######################")
    val activity = context as Activity

    EpisodeButton(name = "Doraemon",
        quality = "1080P", index = 1, isDubbed = true,
            {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                GlobalScope.launch {
                    delay(6000)
                    showPlayer = true
                }
            },
            {}
    )
    if(activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {
            showPlayer = false
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        }){
            Surface(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                VideoPlayer( context = context, url = "https://gredirect.info/download.php?url=aHR0cHM6LyAawehyfcghysfdsDGDYdgdsfsdfwstdgdsgtert9URASDGHUSRFSJGYfdsffsderFStewthsfSFtrftesdfyMnE1eDk5ZDNhLmFuZjU5OC5jb20vdXNlcjEzNDIvZDM1MzdmMDliZWZmYWU4MDNlMzg4YjkxNDMzYjFjMzYvRVAuMS52MC4xNjM5MjkxODAzLjcyMHAubXA0P3Rva2VuPVFrek81WDRCcEhUSUs1U0NNU0hsekEmZXhwaXJlcz0xNzE4Mjk2NjU1JmlkPTk4MTIy")
            }
        }
    }
}