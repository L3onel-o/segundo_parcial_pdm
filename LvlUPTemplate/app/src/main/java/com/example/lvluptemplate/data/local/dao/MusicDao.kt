package com.example.lvluptemplate.data.local.dao

import androidx.room.*
import com.example.lvluptemplate.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {
    // Genres
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres")
    fun getAllGenres(): Flow<List<GenreEntity>>

    // Songs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<SongEntity>)

    @Query("SELECT * FROM songs")
    fun getAllSongs(): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE genreId = :genreId")
    fun getSongsByGenre(genreId: String): Flow<List<SongEntity>>

    // Playlists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylists(playlists: List<PlaylistEntity>)

    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>

    @Query("""
        SELECT p.*, COUNT(ps.songId) as tracksCount 
        FROM playlists p 
        LEFT JOIN playlist_song_cross_ref ps ON p.id = ps.playlistId 
        GROUP BY p.id
    """)
    fun getAllPlaylistsWithCount(): Flow<List<PlaylistWithCount>>

    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    fun getPlaylistById(playlistId: String): Flow<PlaylistEntity?>

    // PlaylistSongCrossRef
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSongCrossRefs(crossRefs: List<PlaylistSongCrossRef>)

    @Query("""
         SELECT s.* FROM songs s
        INNER JOIN playlist_song_cross_ref ps ON s.id = ps.songId
        WHERE ps.playlistId = :playlistId
    """)
    fun getSongsByPlaylist(playlistId: String): Flow<List<SongEntity>>

    @Delete
    suspend fun deletePlaylistSongCrossRef(crossRef: PlaylistSongCrossRef)

    @Query("""
        DELETE FROM playlist_song_cross_ref
        WHERE playlistId = :playlistId AND songId = :songId
    """)
    suspend fun deletePlaylistSongCrossRef(playlistId: String, songId: String)

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM playlist_song_cross_ref
            WHERE playlistId = :playlistId AND songId = :songId
        )
    """)
    fun isSongInPlaylist(playlistId: String, songId: String): Flow<Boolean>
}
