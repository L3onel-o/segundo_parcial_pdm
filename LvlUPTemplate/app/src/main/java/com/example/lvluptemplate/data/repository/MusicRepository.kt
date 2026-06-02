package com.example.lvluptemplate.data.repository

import com.example.lvluptemplate.data.local.dao.MusicDao
import com.example.lvluptemplate.data.local.entities.*
import kotlinx.coroutines.flow.Flow

class MusicRepository(private val musicDao: MusicDao) {

    val allGenres: Flow<List<GenreEntity>> = musicDao.getAllGenres()
    val allSongs: Flow<List<SongEntity>> = musicDao.getAllSongs()
    val allPlaylists: Flow<List<PlaylistEntity>> = musicDao.getAllPlaylists()
    val allPlaylistsWithCount: Flow<List<PlaylistWithCount>> = musicDao.getAllPlaylistsWithCount()

    fun getSongsByGenre(genreId: String): Flow<List<SongEntity>> {
        return musicDao.getSongsByGenre(genreId)
    }

    fun getSongsByPlaylist(playlistId: String): Flow<List<SongEntity>> {
        return musicDao.getSongsByPlaylist(playlistId)
    }

    fun getPlaylistById(playlistId: String): Flow<PlaylistEntity?> {
        return musicDao.getPlaylistById(playlistId)
    }

    suspend fun insertPlaylist(playlist: PlaylistEntity) {
        musicDao.insertPlaylists(listOf(playlist))
    }

    suspend fun addSongToPlaylist(playlistId: String, songId: String) {
        musicDao.insertPlaylistSongCrossRefs(listOf(PlaylistSongCrossRef(playlistId, songId)))
    }

    suspend fun removeSongFromPlaylist(playlistId: String, songId: String) {
        musicDao.deletePlaylistSongCrossRef(playlistId, songId)
    }

    fun isSongInPlaylist(playlistId: String, songId: String): Flow<Boolean> {
        return musicDao.isSongInPlaylist(playlistId, songId)
    }
}
