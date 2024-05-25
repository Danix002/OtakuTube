package com.example.anitest.ui.componets

import android.annotation.SuppressLint
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.anitest.ui.screen.HomeScreen
import com.example.myapplication.MyViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation( viewModel: MyViewModel, navController: NavHostController ) {
    NavigationBar {
        viewModel.navigationItems.value.forEachIndexed {index, item ->
            NavigationBarItem(
                selected = viewModel.selectedNavItem.value == item.route,
                onClick = {
                    if (viewModel.selectedNavItem.value != item.route) {
                        viewModel.selectedNavItem.value = item.route
                        navController.navigate(item.route)
                    }
                },
                label = {
                    Text(text = item.title)
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
                            contentDescription = item.title
                        )
                    }
                })
        }
    }
}