package com.example.lvluptemplate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.lvluptemplate.data.local.entities.SongEntity

@Composable
fun AddSongsDialog(
    availableSongs: List<SongEntity>,
    playlistSongs: List<SongEntity>,
    onDismiss: () -> Unit,
    onSongsAdded: (List<SongEntity>) -> Unit
) {
    var selectedSongs by remember {
        mutableStateOf(setOf<String>())
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
                .background(Color(0xFF1A1A1A), RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Agregar Canciones",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(availableSongs) { song ->
                    val isAlreadyInPlaylist = playlistSongs.any { it.id == song.id }
                    val isSelected = selectedSongs.contains(song.id)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isAlreadyInPlaylist) Color(0xFF2A2A2A)
                                else if (isSelected) Color(0xFF7E49C3).copy(alpha = 0.2f)
                                else Color(0xFF151515)
                            )
                            .clickable(enabled = !isAlreadyInPlaylist) {
                                if (!isAlreadyInPlaylist) {
                                    selectedSongs = if (isSelected) {
                                        selectedSongs - song.id
                                    } else {
                                        selectedSongs + song.id
                                    }
                                }
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF232732)),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = song.coverUrl,
                                    contentDescription = "Cover de ${song.title}",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.matchParentSize()
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = song.title,
                                    color = if (isAlreadyInPlaylist) Color.Gray else Color.White,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1
                                )
                                Text(
                                    text = song.artist,
                                    color = Color.Gray,
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    maxLines = 1
                                )
                                if (isAlreadyInPlaylist) {
                                    Text(
                                        text = "(Ya está en la playlist)",
                                        color = Color(0xFF7E49C3),
                                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                                    )
                                }
                            }
                        }

                        if (!isAlreadyInPlaylist) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { checked ->
                                    selectedSongs = if (checked) {
                                        selectedSongs + song.id
                                    } else {
                                        selectedSongs - song.id
                                    }
                                },
                                modifier = Modifier.width(24.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Ya en playlist",
                                tint = Color(0xFF7E49C3),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(Color(0xFF2A2A2A)),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    Text("Cancelar", color = Color.White)
                }

                Button(
                    onClick = {
                        val songsToAdd = availableSongs.filter { song ->
                            selectedSongs.contains(song.id)
                        }
                        onSongsAdded(songsToAdd)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF7E49C3)),
                    enabled = selectedSongs.isNotEmpty(),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    Text("Agregar (${selectedSongs.size})", color = Color.White)
                }
            }
        }
    }
}

