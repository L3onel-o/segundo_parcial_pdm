package com.example.lvluptemplate.resources

import com.example.lvluptemplate.data.local.entities.GenreEntity
import com.example.lvluptemplate.data.local.entities.SongEntity
import com.example.lvluptemplate.data.local.entities.PlaylistEntity
import com.example.lvluptemplate.data.local.entities.PlaylistSongCrossRef

object DummyData {
    val genres = listOf(
        GenreEntity("g1", "R&B / Soul", "Vibras suaves y voces profundas"),
        GenreEntity("g2", "Urbano Latino", "Reggaeton, Trap y el sazón del Caribe"),
        GenreEntity("g3", "Pop / Pop-Rock", "Los éxitos que definieron y definen generaciones"),
        GenreEntity("g4", "Electrónica / Dance", "Beats sintéticos para mover el esqueleto"),
        GenreEntity("g5", "Indie R&B / Lo-Fi", "Música melancólica para viajar de noche")
    )

    // --- 2. TABLA DE CANCIONES CON GÉNEROS (SongEntity) ---
    val allSongs = listOf(
        // NSQK -> Indie R&B / Lo-Fi (g5)
        SongEntity("s1", "Blamegame", "NSQK", "ATP", "https://m.media-amazon.com/images/I/41ACQgvmvHL._UXNaN_FMjpg_QL85_.jpg", "g5"),
        SongEntity("s2", "309", "NSQK", "Bótanica", "https://i.scdn.co/image/ab67616d0000b273db0bf320fc5001cd184af8fe", "g5"),

        // Keshi -> Indie R&B / Lo-Fi (g5)
        SongEntity("s3", "LIKE I NEED U", "Keshi", "skeletons", "https://cdn-images.dzcdn.net/images/cover/c93a4c15000ea66fe1e8418640923dc3/0x1900-000000-80-0-0.jpg", "g5"),
        SongEntity("s4", "SOMEBODY", "Keshi", "GABRIEL", "https://i.scdn.co/image/ab67616d0000b27319aff2da63b211d75341e8eb", "g5"),

        // The Weeknd -> Pop (g3)
        SongEntity("s5", "Out of Time", "The Weeknd", "Dawn FM", "https://cdn-images.dzcdn.net/images/cover/478a544d29275755b3b8f7b4a1fd7a3c/0x1900-000000-80-0-0.jpg", "g3"),
        SongEntity("s6", "Best Friends", "The Weeknd", "Dawn FM", "https://cdn-images.dzcdn.net/images/cover/478a544d29275755b3b8f7b4a1fd7a3c/0x1900-000000-80-0-0.jpg", "g3"),

        // Bad Bunny -> Urbano Latino (g2)
        SongEntity("s7", "Aguacero", "Bad Bunny", "Un Verano Sin Ti", "https://i.scdn.co/image/ab67616d0000b27349d694203245f241a1bcaa72", "g2"),
        SongEntity("s8", "Tití Me Preguntó", "Bad Bunny", "Un Verano Sin Ti", "https://i.scdn.co/image/ab67616d0000b27349d694203245f241a1bcaa72", "g2"),

        // Daft Punk -> Electrónica / Dance (g4)
        SongEntity("s9", "Something About Us", "Daft Punk", "Discovery", "https://cdn-images.dzcdn.net/images/cover/5718f7c81c27e0b2417e2a4c45224f8a/0x1900-000000-80-0-0.jpg", "g4"),
        SongEntity("s10", "Get Lucky", "Daft Punk", "Random Access Memories", "https://i.scdn.co/image/ab67616d0000b2739b9b36b0e22870b9f542d937", "g4"),

        // GIVEON -> R&B / Soul (g1)
        SongEntity("s11", "Like I Want You", "GIVEON", "Take Time", "https://i.scdn.co/image/ab67616d0000b27390fb297f6a608911e7aaf760", "g1"),
        SongEntity("s12", "Unholy Matrimony", "GIVEON", "Give Or Take", "https://i.scdn.co/image/ab67616d00001e02199a5a0f6e5c2c0ab5f478e5", "g1"),

        // Bruno Mars -> Pop / Pop-Rock (g3)
        SongEntity("s13", "24K Magic", "Bruno Mars", "24K Magic", "https://i.scdn.co/image/ab67616d0000b273232711f7d66a1e19e89e28c5", "g3"),
        SongEntity("s14", "Risk It All", "Bruno Mars", "The Romantic", "https://i.scdn.co/image/ab67616d0000b2733eb8dc748f7efb1470f74395", "g3"),

        // Omar Courtz -> Urbano Latino (g2)
        SongEntity("s15", "GANTEL y BELLAKz", "Omar Courtz", "Por Si Mañana No Estoy", "https://i.scdn.co/image/ab67616d0000b27390af5246adcaa93acb721c17", "g2"),
        SongEntity("s16", "KOKO", "Omar Courtz", "Por Si Mañana No Estoy", "https://i.scdn.co/image/ab67616d0000b27390af5246adcaa93acb721c17", "g2"),

        // Michael Jackson -> Pop / Pop-Rock (g3)
        SongEntity("s17", "Chicago", "Michael Jackson", "XSCAPE", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTk-uggu1z71LCKj0q_OuLwNI_7uYCky_2ETw&s", "g3"),
        SongEntity("s18", "Remember the Time", "Michael Jackson", "Dangerous", "https://cdn-images.dzcdn.net/images/cover/93a5354699d552666448e1c87c976605/1900x1900-000000-80-0-0.jpg", "g3")
    )

    // --- 3. TABLA DE PLAYLISTS (PlaylistEntity) ---
    val playlists = listOf(
        PlaylistEntity(
            id = "p1",
            name = "Late Night Vibes",
            description = "Para cuando el mood está melancólico y relajado."
        ),
        PlaylistEntity(
            id = "p2",
            name = "Party Starters 🔥",
            description = "Éxitos para encender cualquier lugar."
        ),
        PlaylistEntity(
            id = "p3",
            name = "The Kings of Pop",
            description = "De Michael Jackson a The Weeknd, pura realeza musical."
        ),
        PlaylistEntity(
            id = "p4",
            name = "Favorites",
            description = "Canciones que te gustan"
        )
    )

    // --- 4. TABLA INTERMEDIA DE RELACIONES (PlaylistSongCrossRef) ---
    val playlistSongRelations = listOf(
        // Canciones asignadas a "Late Night Vibes" (p1)
        PlaylistSongCrossRef("p1", "s1"),
        PlaylistSongCrossRef("p1", "s3"),
        PlaylistSongCrossRef("p1", "s4"),
        PlaylistSongCrossRef("p1", "s11"),

        // Canciones asignadas a "Party Starters 🔥" (p2)
        PlaylistSongCrossRef("p2", "s5"),
        PlaylistSongCrossRef("p2", "s8"),
        PlaylistSongCrossRef("p2", "s10"),
        PlaylistSongCrossRef("p2", "s13"),
        PlaylistSongCrossRef("p2", "s15"),

        // Canciones asignadas a "The Kings of Pop" (p3)
        PlaylistSongCrossRef("p3", "s17"),
        PlaylistSongCrossRef("p3", "s18"),
        PlaylistSongCrossRef("p3", "s5"),
        PlaylistSongCrossRef("p3", "s14")
    )
}
