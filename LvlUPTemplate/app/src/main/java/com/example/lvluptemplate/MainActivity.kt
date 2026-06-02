package com.example.lvluptemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lvluptemplate.data.local.database.MusicDatabase
import com.example.lvluptemplate.data.local.entities.SongEntity
import com.example.lvluptemplate.data.repository.MusicRepository
import com.example.lvluptemplate.screen.MainScreen
import com.example.lvluptemplate.screen.SearchScreen
import com.example.lvluptemplate.screen.SongDetailScreen
import com.example.lvluptemplate.screen.PlaylistsScreen
import com.example.lvluptemplate.screen.MyPlaylistScreen
import com.example.lvluptemplate.ui.theme.LvlUPTemplateTheme
import com.example.lvluptemplate.viewmodel.MusicViewModel
import com.example.lvluptemplate.viewmodel.MusicViewModelFactory

enum class AppScreen {
    HOME, SEARCH, LIBRARY, MY_PLAYLIST
}

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MusicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar el ViewModel antes de setContent
        val database = MusicDatabase.getDatabase(this, lifecycleScope)
        val repository = MusicRepository(database.musicDao())
        viewModel = ViewModelProvider(this, MusicViewModelFactory(repository)).get(MusicViewModel::class.java)

        setContent {
            LvlUPTemplateTheme {

                var currentNavScreen by remember { mutableStateOf(AppScreen.HOME) }
                var selectedSong by remember { mutableStateOf<SongEntity?>(null) }
                var selectedPlaylistId by remember { mutableStateOf<String?>(null) }

                if (selectedSong != null) {
                    SongDetailScreen(
                        song = selectedSong!!,
                        viewModel = viewModel,
                        onBack = { selectedSong = null },
                        onNavItemSelected = { index ->
                            currentNavScreen = when (index) {
                                1 -> AppScreen.SEARCH
                                2 -> AppScreen.LIBRARY
                                else -> AppScreen.HOME
                            }
                        }
                    )
                } else {
                    when (currentNavScreen) {
                        AppScreen.HOME -> MainScreen(
                            onSongClick = { selectedSong = it },
                            onNavItemSelected = { index ->
                                currentNavScreen = when (index) {
                                    1 -> AppScreen.SEARCH
                                    2 -> AppScreen.LIBRARY
                                    else -> AppScreen.HOME
                                }
                            }
                        )
                        AppScreen.SEARCH -> SearchScreen(
                            onSongClick = { selectedSong = it },
                            onNavItemSelected = { index ->
                                currentNavScreen = when (index) {
                                    0 -> AppScreen.HOME
                                    2 -> AppScreen.LIBRARY
                                    else -> AppScreen.SEARCH
                                }
                            }
                        )
                        AppScreen.LIBRARY -> {
                            PlaylistsScreen(
                                viewModel = viewModel,
                                onPlaylistClick = { playlistId ->
                                    selectedPlaylistId = playlistId
                                    currentNavScreen = AppScreen.MY_PLAYLIST
                                },
                                onNavItemSelected = { index ->
                                    currentNavScreen = when (index) {
                                        0 -> AppScreen.HOME
                                        1 -> AppScreen.SEARCH
                                        else -> AppScreen.LIBRARY
                                    }
                                }
                            )
                        }
                        AppScreen.MY_PLAYLIST -> {
                            if (selectedPlaylistId != null) {
                                MyPlaylistScreen(
                                    playlistId = selectedPlaylistId!!,
                                    viewModel = viewModel,
                                    onBack = {
                                        currentNavScreen = AppScreen.LIBRARY
                                        selectedPlaylistId = null
                                    },
                                    onNavItemSelected = { index ->
                                        currentNavScreen = when (index) {
                                            0 -> AppScreen.HOME
                                            1 -> AppScreen.SEARCH
                                            else -> AppScreen.LIBRARY
                                        }
                                        selectedPlaylistId = null
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
