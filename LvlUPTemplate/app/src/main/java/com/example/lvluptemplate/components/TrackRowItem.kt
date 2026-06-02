package com.example.lvluptemplate.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lvluptemplate.data.local.entities.SongEntity
import com.example.lvluptemplate.viewmodel.MusicViewModel

@Composable
fun TrackRowItem(
    song: SongEntity,
    showRemoveOption: Boolean = false,
    playlistId: String? = null,
    viewModel: MusicViewModel? = null
) {
    val context = LocalContext.current
    val expandedMenu = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { Toast.makeText(context, "Reproduciendo: ${song.title}", Toast.LENGTH_SHORT).show() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(45.dp)
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

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = song.title,
                color = Color(0xFF7E49C3),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Box {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Track options",
                tint = Color.Gray,
                modifier = Modifier
                    .clickable { expandedMenu.value = true }
                    .padding(8.dp)
            )

            DropdownMenu(
                expanded = expandedMenu.value,
                onDismissRequest = { expandedMenu.value = false },
                modifier = Modifier.background(Color(0xFF2A2A2A))
            ) {
                if (showRemoveOption && playlistId != null && viewModel != null) {
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color(0xFFFF6B6B),
                                    modifier = Modifier.size(18.dp)
                                )
                                Text("Eliminar de playlist", color = Color.White)
                            }
                        },
                        onClick = {
                            viewModel.removeSongFromPlaylist(playlistId, song.id)
                            Toast.makeText(
                                context,
                                "${song.title} eliminada de la playlist",
                                Toast.LENGTH_SHORT
                            ).show()
                            expandedMenu.value = false
                        }
                    )
                }

                DropdownMenuItem(
                    text = {
                        Text("Reproducir", color = Color.White)
                    },
                    onClick = {
                        Toast.makeText(context, "Reproduciendo: ${song.title}", Toast.LENGTH_SHORT).show()
                        expandedMenu.value = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Ver detalles", color = Color.White)
                    },
                    onClick = {
                        Toast.makeText(context, "Detalles: ${song.artist}", Toast.LENGTH_SHORT).show()
                        expandedMenu.value = false
                    }
                )
            }
        }
    }
}