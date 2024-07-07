package com.example.anitest.ui.screen.sub

import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.anitest.MainActivity
import com.example.anitest.model.Anime
import com.example.anitest.navigation.Navigation
import com.example.anitest.navigation.Screen
import com.example.anitest.utils.NavigationItem
import com.example.myapplication.MyViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val viewModel = mockk<MyViewModel>(relaxed = true)
    private val mockedAnimeSearch: StateFlow<List<Anime>> = MutableStateFlow(
        emptyList()
    )
    private val mockedConnection: StateFlow<Boolean> = MutableStateFlow(
        true
    )
    private val mockedNavigationItem = mutableStateOf(
        listOf(
            NavigationItem(
                title = "Zapping",
                route = Screen.Zapping.route,
                hasNews = false,
                selectedIcon = Icons.Filled.List,
                unselectedIcon = Icons.Outlined.List
            ),
            NavigationItem(
                title = "Explore",
                route = Screen.Home.route,
                hasNews = false,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            NavigationItem(
                title = "Library",
                route = Screen.Library.route,
                hasNews = false,
                selectedIcon = Icons.Filled.Edit,
                unselectedIcon = Icons.Outlined.Edit
            ),
            NavigationItem(
                title = "Profile",
                route = Screen.Profile.route,
                hasNews = false,
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle
            ),
        )
    )

    private val mockedSelectedNavItem: MutableState<String> = mutableStateOf(Screen.Zapping.route)
    private val mockedPopularAnime: StateFlow<List<Anime>> = MutableStateFlow(
        listOf(
            Anime(
                "One Piece",
                "https://gogocdn.net/cover/one-piece-1708412053.png",
                "one-piece",
                ""),
            Anime(
                "Sasayaku You ni Koi wo Utau",
                "https://gogocdn.net/cover/sasayaku-you-ni-koi-wo-utau-1711738463.png",
                "sasayaku-you-ni-koi-wo-utau", ""),
            Anime(
                "Detective Conan",
                "https://gogocdn.net/cover/detective-conan.png",
                "detective-conan",
                "")
        )
    )
    private val mockedIsZappingScreenLoaded : StateFlow<Boolean> = MutableStateFlow(
        true
    )

    @Test
    fun testZappingScreen_DisplayPopularText() {
        every { viewModel.animeSearch } returns mockedAnimeSearch
        every { viewModel.connection } returns mockedConnection
        every { viewModel.navigationItems } returns mockedNavigationItem
        every { viewModel.selectedNavItem } returns mockedSelectedNavItem
        every { viewModel.popularAnime } returns mockedPopularAnime
        every { viewModel.isZappingScreenLoaded } returns mockedIsZappingScreenLoaded

        composeTestRule.activity.setContent {
            Navigation(viewModel, LocalContext.current, Screen.Zapping.route)
        }

        composeTestRule
            .onNodeWithText("Popular")
            .assertIsDisplayed()
    }
}
