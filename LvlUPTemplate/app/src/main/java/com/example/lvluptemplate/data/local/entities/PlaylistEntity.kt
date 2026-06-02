package com.example.lvluptemplate.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String
)

data class PlaylistWithCount(
    val id: String,
    val name: String,
    val description: String,
    val tracksCount: Int
)

