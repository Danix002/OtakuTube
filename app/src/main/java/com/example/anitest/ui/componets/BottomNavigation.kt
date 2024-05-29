package com.example.anitest.ui.componets


import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.myapplication.MyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(viewModel: MyViewModel, navController: NavHostController) {
    NavigationBar (
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,

    ){
        viewModel.navigationItems.value.forEachIndexed { index, item ->
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