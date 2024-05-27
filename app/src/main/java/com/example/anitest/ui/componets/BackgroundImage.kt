package com.example.anitest.ui.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.anitest.R

@Composable
fun BackgroundImage( contentPadding: PaddingValues, content : @Composable () -> Unit) {
    val matrix = ColorMatrix()
    matrix.setToSaturation(0.7F)
    Box(modifier = Modifier.padding(contentPadding)) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(matrix),
            modifier = Modifier
                .fillMaxSize()
                .blur(20.dp)
        )
        content()
    }
}