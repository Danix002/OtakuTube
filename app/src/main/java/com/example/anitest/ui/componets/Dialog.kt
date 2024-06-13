package com.example.anitest.ui.componets

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.example.anitest.model.Anime

@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    anime: Anime
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        AnimeBigCard(anime)
    }
}