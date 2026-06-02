package com.example.lvluptemplate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lvluptemplate.data.local.entities.PlaylistEntity
import com.example.lvluptemplate.data.local.entities.PlaylistWithCount
import com.example.lvluptemplate.data.local.entities.SongEntity
import com.example.lvluptemplate.data.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class MusicViewModel(private val repository: MusicRepository) : ViewModel() {

    val allPlaylists: StateFlow<List<PlaylistWithCount>> = repository.allPlaylistsWithCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allSongs: StateFlow<List<SongEntity>> = repository.allSongs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createPlaylist(name: String) {
        viewModelScope.launch {
            val newPlaylist = PlaylistEntity(
                id = UUID.randomUUID().toString(),
                name = name,
                description = ""
            )
            repository.insertPlaylist(newPlaylist)
        }
    }

    fun getSongsForPlaylist(playlistId: String): Flow<List<SongEntity>> {
        return repository.getSongsByPlaylist(playlistId)
    }

    fun getPlaylist(playlistId: String): Flow<PlaylistEntity?> {
        return repository.getPlaylistById(playlistId)
    }

    fun addSongToPlaylist(playlistId: String, songId: String) {
        viewModelScope.launch {
            repository.addSongToPlaylist(playlistId, songId)
        }
    }

    fun removeSongFromPlaylist(playlistId: String, songId: String) {
        viewModelScope.launch {
            repository.removeSongFromPlaylist(playlistId, songId)
        }
    }

    fun addMultipleSongsToPlaylist(playlistId: String, songs: List<SongEntity>) {
        viewModelScope.launch {
            songs.forEach { song ->
                repository.addSongToPlaylist(playlistId, song.id)
            }
        }
    }

    fun addSongToFavorites(songId: String) {
        viewModelScope.launch {
            repository.addSongToPlaylist("p4", songId)
        }
    }

    fun removeSongFromFavorites(songId: String) {
        viewModelScope.launch {
            repository.removeSongFromPlaylist("p4", songId)
        }
    }

    fun isSongInFavorites(songId: String): Flow<Boolean> {
        return repository.isSongInPlaylist("p4", songId)
    }
}

class MusicViewModelFactory(private val repository: MusicRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
