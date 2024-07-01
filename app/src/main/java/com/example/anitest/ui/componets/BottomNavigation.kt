package com.example.anitest.ui.componets


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.anitest.navigation.Screen
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(viewModel: MyViewModel, navController: NavHostController) {
    val connection by viewModel.connection.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    NavigationBar (
        containerColor = Color.Transparent,
        contentColor = Color.Transparent
    ){
        if (!connection) {
            NavigationBarItem(selected = false,
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.testConnection()) {
                            viewModel.selectedNavItem.value = Screen.Home.route
                        }
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "refresh connection",
                            tint = Color.White
                        )}
                       },
                    label = {
                        Text(text = "Retry Connection", color = Color.White)
                    }
            )
        }
        viewModel.navigationItems.value.forEachIndexed { index, item ->
            if (connection || item.route == Screen.Library.route || item.route == Screen.Profile.route) {
                NavigationBarItem(
                    selected = viewModel.selectedNavItem.value == item.route,
                    onClick = {
                        if (viewModel.selectedNavItem.value != item.route) {
                            viewModel.selectedNavItem.value = item.route
                            navController.navigate(item.route)
                        }
                    },
                    label = {
                        Text(text = item.title, color = Color.White)
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                } else if (item.hasNews) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (viewModel.selectedNavItem.value == item.route) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title,
                                tint = if (viewModel.selectedNavItem.value != item.route) {
                                    Color.White
                                } else Color(102, 90, 110)
                            )
                        }
                    }
                )
            }
        }
    }
}
