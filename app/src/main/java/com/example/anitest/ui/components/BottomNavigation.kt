package com.example.anitest.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.anitest.navigation.Screen
import com.example.anitest.ui.theme.LightOtakuColorScheme
import com.example.myapplication.MyViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(viewModel: MyViewModel, navController: NavHostController) {
    val connection by viewModel.connection.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry) {
        currentBackStackEntry?.destination?.route?.let { route ->
            viewModel.selectedNavItem.value = route
        }
        viewModel.setIsLoadedAnimeScreen(flag = false)
    }

    NavigationBar (
        containerColor = Color.Transparent,
        contentColor = Color.Transparent
    ){
        if (!connection) {
            NavigationBarItem(
                selected = false,
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
                            tint = LightOtakuColorScheme.onSecondary
                        )}
                       },
                    label = {
                        Text(text = "Retry Connection", color = LightOtakuColorScheme.onSecondary)
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
                            navController.navigate(item.route){
                                launchSingleTop = true
                            }
                        }
                    },
                    label = {
                        Text(text = item.title, color = LightOtakuColorScheme.onSecondary)
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
                                    LightOtakuColorScheme.onSecondary
                                } else LightOtakuColorScheme.secondary
                            )
                        }
                    }
                )
            }
        }
    }
}
