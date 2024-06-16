package com.example.anitest.ui.componets

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.anitest.model.Episode
import com.example.myapplication.MyViewModel

@Composable
fun EpisodeButton(quality: String, index: Number, isDubbed: Boolean, onWatch: ()-> Unit, onDownload: ()-> Unit) {

    val principalColor = Color(112, 82, 137)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(3.dp))
            .padding(2.dp)
            .background(principalColor)
            .padding(start = 8.dp)
            .padding(bottom = 10.dp, end = 4.dp, top = 10.dp)
            .shadow(16.dp)
            .clickable { onWatch() }
    ) {
        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Watch episode", tint = Color.White)
        Text(
            text = "Ep. $index",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(horizontal = 4.dp)
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
                .width(80.dp)
                .padding(horizontal = 12.dp)
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

@SuppressLint("SourceLockedOrientationActivity", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EpisodesDialog(context: Context, viewModel: MyViewModel, episodes: List<Episode>, isDubbed: Boolean, open: Boolean, onDismiss: () -> Unit){
    var showPlayer by remember { mutableStateOf(false) }
    val activity = context as Activity
    val currentEP by viewModel.currentEP.collectAsState()

    if(open) {
        Dialog(
            properties = DialogProperties(
            usePlatformDefaultWidth = false
            ),
            onDismissRequest = { onDismiss() }
        ){
            Box(modifier = Modifier
                .background(Color(242, 218, 255))
                .fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(episodes) { index, ep ->
                        if(index == 0){
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Row {
                                    IconButton(
                                        colors = IconButtonDefaults.filledIconButtonColors(
                                            containerColor = Color.Transparent
                                        ),
                                        onClick = { onDismiss() }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "",
                                            tint = Color(112, 82, 137)
                                        )
                                    }
                                    Text(text = "Episodes",
                                        color = Color(112, 82, 137),
                                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                                    )
                                }
                            }
                        }
                        EpisodeButton(
                            quality = ep.ep[(ep.ep.size) - 1].name,
                            index = ep.index + 1,
                            isDubbed = isDubbed,
                            {
                                viewModel.setCurrentEP(index)
                                activity.requestedOrientation =
                                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            },
                            {}
                        )
                    }
                }
            }
        }
    }

    if(activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {
                showPlayer = false
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }){
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    VideoPlayer(
                        onBack = {
                            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        },
                        context = context,
                        index = currentEP,
                        urls = episodes.map { episode -> episode.ep[(episode.ep.size) - 1].link } )
                }
            }
    }
}