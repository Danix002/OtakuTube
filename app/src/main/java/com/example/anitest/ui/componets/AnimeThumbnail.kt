package com.example.anitest.ui.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
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
        .height(212.dp)
        .background(Color.Black)
    ) {
        Image(
            painter = rememberAsyncImagePainter(img),
            contentDescription = "anime image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(129, 81, 86), contentColor = Color.White),
            onClick = { showPlayer = true },
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.BottomEnd)
                .padding(bottom = 4.dp, end = 4.dp)
                .shadow(16.dp)
        ) {
            Text(text = "Trailer")
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Watch trailer")
        }
        if (showPlayer) {
            YouTubePlayer(youtubeVideoId = trailer, lifecycleOwner = LocalLifecycleOwner.current)
        }
    }
}

