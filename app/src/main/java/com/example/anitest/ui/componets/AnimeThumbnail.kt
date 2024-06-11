package com.example.anitest.ui.componets


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.rememberAsyncImagePainter

@Composable
fun AnimeThumbnailSkeleton() {

}

@Composable
fun AnimeThumbnail(img: String, trailer: String) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(188.dp)
        .background(Color.Gray)
    ){
        Image(
            painter = rememberAsyncImagePainter(img),
            contentDescription = "anime image",
            modifier = Modifier
                .fillMaxSize()
        )
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(102, 90, 110), contentColor = Color.White),
            onClick = { },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "Trailer")
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Watch trailer")
        }
        ExoPlayer(){

        }
    }
}