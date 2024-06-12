package com.example.anitest.ui.componets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter

@Composable
fun AnimeThumbnailSkeleton() {
    // Implementa questa funzione se necessario
}

@Composable
fun AnimeThumbnail(img: String, trailer: String) {
    var showPlayer by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(188.dp)
        .background(Color.Gray)
    ) {
        Image(
            painter = rememberAsyncImagePainter(img),
            contentDescription = "anime image",
            modifier = Modifier.fillMaxSize()
        )
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(102, 90, 110), contentColor = Color.White),
            onClick = { showPlayer = true },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "Trailer")
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Watch trailer")
        }
        if (showPlayer) {
            YouTubePlayer(trailer)
        }
    }
}

@Composable
fun YouTubePlayer(trailer: String) {

}

private const val YOUTUBE_API_KEY = "YOUR_YOUTUBE_API_KEY"
