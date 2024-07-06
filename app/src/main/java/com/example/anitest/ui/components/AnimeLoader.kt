package com.example.anitest.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.anitest.ui.theme.LightOtakuColorScheme


@Composable
fun AnimeLoaderButton(onClick : () -> Unit, loading : Boolean, text: Boolean): Unit {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = LightOtakuColorScheme.secondary, contentColor = LightOtakuColorScheme.onSecondary),
        onClick = {
            onClick()
        },
        modifier = Modifier.padding(start = 8.dp)
        ) {
            if (!loading && text) Text(text = "Other")
            Icon(tint = LightOtakuColorScheme.onSecondary, imageVector = if (!loading) Icons.Filled.KeyboardArrowRight else Icons.Default.Refresh, contentDescription = "")
    }
}

