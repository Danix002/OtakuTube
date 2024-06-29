package com.example.anitest.ui.componets

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anitest.R
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun UserProfile(/*imageId: Int*/) {
    var imageId by remember { mutableIntStateOf(R.drawable.avatar1) }
    var edit by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.None,
                    modifier = Modifier
                        .size(245.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Test", fontSize = 20.sp, color = Color.White, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { edit = true }) {
                    Icon(
                        imageVector = Icons.Filled.ModeEdit,
                        contentDescription = "Edit",
                        tint = Color.White
                    )
                }
            }
        }
        if(edit){
            UserProfileEditPopUp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileEditPopUp() {
    val imageIds = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
        R.drawable.avatar5,
        R.drawable.avatar6,
        R.drawable.avatar7,
        R.drawable.avatar8,
        R.drawable.avatar9,
        R.drawable.avatar10,
        R.drawable.avatar11,
        R.drawable.avatar12,
        R.drawable.avatar13,
        R.drawable.avatar14
    )

    var nameValue by remember { mutableStateOf("") }
    var selectedImg by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier
        .width(400.dp)
        .height(500.dp)
        .clip(RoundedCornerShape(30.dp))
        .background(Color(255, 218, 220)), contentAlignment = Alignment.Center
    ){
        LazyColumn{
            item {
                Text(
                    text = "Choose your avatar",
                    fontSize = 24.sp,
                    color = Color(102, 90, 110),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .height(450.dp)
                        .fillMaxWidth()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(4.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(imageIds) { imageId ->
                            val isSelected = imageId == selectedImg
                            val buttonColor = if (isSelected) Color.Black else Color.Transparent

                            Button(
                                onClick = { selectedImg = imageId },
                                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                modifier = Modifier.fillMaxWidth().height(100.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = imageId),
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.clip(CircleShape)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Modify your name",
                    fontSize = 24.sp,
                    color = Color(102, 90, 110),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(30.dp)),
                        singleLine = true,
                        value = nameValue,
                        onValueChange = { value: String -> nameValue = value },
                        placeholder = {
                            Text(
                                text = "Modify your name...",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        },
                        textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(102, 90, 110).copy(alpha = 0.8f),
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(102, 90, 110)
                        ),
                        onClick = { /*TODO*/ }) {
                        Text(
                            text = "Cancel",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Confirm",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}