package com.example.anitest.ui.screen.sub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anitest.ui.componets.CategoryRowSkeleton


@Composable
fun AnimeScreenSkeleton() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray.copy(alpha = 0.5f))
            .fillMaxWidth()
            .height(238.dp)

    )
    Spacer(modifier = Modifier.height(4.dp))
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray.copy(alpha = 0.5f))
            .fillMaxWidth()
            .height(32.dp)

    )
    Spacer(modifier = Modifier.height(4.dp))
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray.copy(alpha = 0.5f))
            .fillMaxWidth()
            .height(128.dp)

    )
    Spacer(modifier = Modifier.height(4.dp))
    CategoryRowSkeleton()
}

@Composable
@Preview
fun Test() {
    Column {
        AnimeScreenSkeleton()
    }

}