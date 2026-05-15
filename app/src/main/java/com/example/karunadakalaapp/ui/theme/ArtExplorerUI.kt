package com.example.karunada_kala.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Art Form Data Class
data class ArtForm(
    val id: Int,
    val name: String,
    val region: String,
    val imageUrl: String,
    val description: String,
    val history: String,
    val characteristics: List<String>,
    val relatedArtists: List<String>,
    val category: String,
    val heritageStatus: String,
    val isFavorite: Boolean = false
)

// Sample Art Forms with Real Images
val artForms = listOf(
    ArtForm(
        id = 1,
        name = "Yakshagana",
        region = "Coastal Karnataka",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Yakshagana_performance.jpg/640px-Yakshagana_performance.jpg",
        description = "Ancient classical narrative dance-drama with elaborate costumes and dramatic makeup",
        history = "Yakshagana originated in the coastal districts of Karnataka during the 16th century. It blends dance, music, and drama to tell stories from Hindu mythology. The art form was traditionally performed in temples during festivals as an offering to the gods.",
        characteristics = listOf(
            "Elaborate makeup and costumes",
            "Complex footwork patterns",
            "Synchronized group movements",
            "Mythological narratives",
            "Live music accompaniment"
        ),
        relatedArtists = listOf("Padmavati Ensemble", "Mandara Yakshagana Troupe", "Apsara Classical Arts"),
        category = "Dance-Drama",
        heritageStatus = "UNESCO Recognized"
    ),
    ArtForm(
        id = 2,
        name = "Dollu Kunitha",
        region = "Deccan Plateau",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/Dollu_kunita.jpg/640px-Dollu_kunita.jpg",
        description = "Rhythmic drum dance performed during festivals and celebrations",
        history = "Dollu Kunitha emerged from shepherd communities in the Deccan region. Performed around a central figure who beats the drum (dollu), dancers move in circular formations creating rhythmic patterns. It remains popular during crop festivals and weddings.",
        characteristics = listOf(
            "Central drum performer",
            "Circular group formation",
            "High-energy movements",
            "Festival-oriented",
            "Community participation"
        ),
        relatedArtists = listOf("Surabhi Dollu Kunitha Group", "Village Performers Network"),
        category = "Folk Dance",
        heritageStatus = "Protected Cultural Form"
    ),
    ArtForm(
        id = 3,
        name = "Bidriware",
        region = "Bijapur & Kalaburagi",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Bidriware_decorative_plate.jpg/640px-Bidriware_decorative_plate.jpg",
        description = "Intricate metalware craft using brass, copper, and zinc with traditional inlay techniques",
        history = "Bidriware originated in Bijapur during the Adil Shahi dynasty. Artisans developed unique inlay techniques combining different metals. Each piece is handcrafted using traditional methods passed down through generations.",
        characteristics = listOf(
            "Brass and copper inlay",
            "Zinc alloy base",
            "Hand-etched patterns",
            "Traditional oxidation",
            "Decorative and functional"
        ),
        relatedArtists = listOf("Bidri Master Craftsmen Association", "Heritage Metal Works"),
        category = "Metalcraft",
        heritageStatus = "GI Tagged"
    ),
    ArtForm(
        id = 4,
        name = "Ilkal Weaving",
        region = "Belagavi District",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/Ilkal_saree_border.jpg/640px-Ilkal_saree_border.jpg",
        description = "Traditional hand-woven silk and cotton sarees with unique border designs",
        history = "Ilkal weaving has been practiced for centuries in the Belagavi district. Known for distinctive reversible border designs (mundavalya), these sarees combine the finest silk and cotton threads in intricate patterns.",
        characteristics = listOf(
            "Hand-woven technique",
            "Reversible borders",
            "Silk-cotton blend",
            "Traditional patterns",
            "Sustainable production"
        ),
        relatedArtists = listOf("Ilkal Weavers Cooperative", "Traditional Loom Masters"),
        category = "Textile Art",
        heritageStatus = "GI Tagged"
    ),
    ArtForm(
        id = 5,
        name = "Kinnala Toys",
        region = "Rural Karnataka",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/39/Traditional_clay_toys.jpg/640px-Traditional_clay_toys.jpg",
        description = "Handcrafted clay and wooden toys, important folk art from rural Karnataka",
        history = "Kinnala toys represent centuries-old folk craft tradition. Created by rural artisans using locally sourced clay and wood, these toys have been cherished by generations of children.",
        characteristics = listOf(
            "Hand-molded clay",
            "Natural coloring",
            "Sustainable materials",
            "Cultural storytelling",
            "Child-safe designs"
        ),
        relatedArtists = listOf("Village Toy Makers", "Artisan Collectives"),
        category = "Folk Craft",
        heritageStatus = "Traditional Practice"
    ),
    ArtForm(
        id = 6,
        name = "Mysore Painting",
        region = "Mysore",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Mysore_painting_sample.jpg/640px-Mysore_painting_sample.jpg",
        description = "Fine art form depicting religious and mythological themes on paper using natural pigments",
        history = "Mysore painting developed during the royal Wodeyar dynasty. Masters created elaborate temple paintings and manuscript illustrations using traditional techniques with natural colors derived from minerals and plants.",
        characteristics = listOf(
            "Natural pigments",
            "Religious themes",
            "Fine brushwork",
            "Gold leaf accents",
            "Intricate detailing"
        ),
        relatedArtists = listOf("Mysore Art Academy", "Heritage Painters Guild"),
        category = "Painting",
        heritageStatus = "Royal Heritage"
    )
)

// Main Art Explorer Screen
@Composable
fun ArtExplorerScreen() {
    var selectedArt by remember { mutableStateOf<ArtForm?>(null) }
    var favorites by remember { mutableStateOf<Set<Int>>(emptySet()) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (selectedArt == null) {
            // Explorer List View
            ArtFormsList(
                artForms = artForms,
                favorites = favorites,
                onArtClick = { selectedArt = it },
                onFavoriteToggle = { id ->
                    favorites = if (favorites.contains(id)) {
                        favorites - id
                    } else {
                        favorites + id
                    }
                }
            )
        } else {
            // Detail View
            ArtFormDetail(
                artForm = selectedArt!!,
                isFavorite = favorites.contains(selectedArt!!.id),
                onBackClick = { selectedArt = null },
                onFavoriteToggle = {
                    favorites = if (favorites.contains(selectedArt!!.id)) {
                        favorites - selectedArt!!.id
                    } else {
                        favorites + selectedArt!!.id
                    }
                }
            )
        }
    }
}

// Art Forms List with Cards
@Composable
fun ArtFormsList(
    artForms: List<ArtForm>,
    favorites: Set<Int>,
    onArtClick: (ArtForm) -> Unit,
    onFavoriteToggle: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFC60C30),
                            Color(0xFF003DA5)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Art Form Explorer",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Discover Karnataka's Cultural Treasures",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Art Forms Grid
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(artForms) { artForm ->
                ArtFormCard(
                    artForm = artForm,
                    isFavorite = favorites.contains(artForm.id),
                    onCardClick = { onArtClick(artForm) },
                    onFavoriteClick = { onFavoriteToggle(artForm.id) }
                )
            }
        }
    }
}

// Individual Art Form Card
@Composable
fun ArtFormCard(
    artForm: ArtForm,
    isFavorite: Boolean,
    onCardClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color.Gray)
            ) {
                AsyncImage(
                    model = artForm.imageUrl,
                    contentDescription = artForm.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Category Badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    color = Color(0xFFC60C30),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = artForm.category,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Favorite Button
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(50.dp))
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color(0xFFC60C30)
                    )
                }
            }

            // Content
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = artForm.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003DA5)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📍 ${artForm.region}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        color = Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = artForm.heritageStatus,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF003DA5),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = artForm.description,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp),
                    fontStyle = FontStyle.Italic
                )

                Text(
                    text = "Tap to explore",
                    fontSize = 12.sp,
                    color = Color(0xFFC60C30),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

// Detailed Art Form View
@Composable
fun ArtFormDetail(
    artForm: ArtForm,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF0F0F0)
                    ),
                    modifier = Modifier.size(40.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("←", fontSize = 20.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = onFavoriteToggle) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color(0xFFC60C30),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Large Hero Image
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = artForm.imageUrl,
                            contentDescription = artForm.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.4f)
                                        ),
                                        startY = 100f
                                    )
                                )
                        )

                        // Title Overlay
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = artForm.name,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = artForm.region,
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }

            item {
                // Info Cards Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoCard(
                        icon = "🎭",
                        label = "Category",
                        value = artForm.category,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        icon = "🏆",
                        label = "Heritage",
                        value = artForm.heritageStatus,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                // Description Section
                Column {
                    Text(
                        text = "Overview",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF003DA5)
                    )
                    Text(
                        text = artForm.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp),
                        lineHeight = 20.sp
                    )
                }
            }

            item {
                // History Section
                Column {
                    Text(
                        text = "Historical Background",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF003DA5)
                    )
                    Text(
                        text = artForm.history,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp),
                        lineHeight = 20.sp
                    )
                }
            }

            item {
                // Characteristics Section
                Column {
                    Text(
                        text = "Key Characteristics",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF003DA5)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        artForm.characteristics.forEach { characteristic ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .size(24.dp),
                                    color = Color(0xFFC60C30),
                                    shape = RoundedCornerShape(50.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            text = "✓",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                                Text(
                                    text = characteristic,
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                // Related Artists Section
                Column {
                    Text(
                        text = "Featured Artisans",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF003DA5)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        artForm.relatedArtists.forEach { artist ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF5F5F5)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "👤 $artist",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                // Call to Action Buttons
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /* Workshop booking */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFC60C30)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Find Workshops",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { /* Find artisans */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF003DA5)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Find Artisans",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// Info Card Component
@Composable
fun InfoCard(
    icon: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 24.sp)
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF003DA5),
                modifier = Modifier.padding(top = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
