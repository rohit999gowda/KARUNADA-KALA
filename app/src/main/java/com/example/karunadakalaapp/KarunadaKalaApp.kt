package com.example.karunadakalaapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import coil.compose.AsyncImage
import com.example.karunadakalaapp.data.WorkshopRegistrationRepository
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.launch

enum class HomeTab(val label: String) {
    Explorer("Explorer"),
    Map("Artisan Map"),
    Workshops("Workshops"),
    Events("Events"),
    Marketplace("Marketplace")
}

private fun HomeTab.icon(): ImageVector = when (this) {
    HomeTab.Explorer -> Icons.Outlined.Palette
    HomeTab.Map -> Icons.Outlined.Map
    HomeTab.Workshops -> Icons.Outlined.School
    HomeTab.Events -> Icons.Outlined.Event
    HomeTab.Marketplace -> Icons.Outlined.Storefront
}

private fun isPlaceholderMapsApiKey(key: String?): Boolean {
    val k = key?.trim().orEmpty()
    if (k.isEmpty() || k.equals("null", ignoreCase = true)) return true
    val bad = setOf(
        "YOUR_MAPS_API_KEY",
        "INSERT_YOUR_API_KEY_HERE",
        "API_KEY",
        "DEBUG_KEY"
    )
    return bad.any { k.equals(it, ignoreCase = true) }
}

@Composable
private fun rememberHasValidGoogleMapsApiKey(): Boolean {
    val context = LocalContext.current
    return remember {
        runCatching {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            val key = appInfo.metaData?.getString("com.google.android.geo.API_KEY")
            !isPlaceholderMapsApiKey(key)
        }.getOrDefault(false)
    }
}

private fun dialArtisanPhone(context: Context, phone: String) {
    runCatching {
        context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
    }
}

data class ArtForm(
    val name: String,
    val region: String,
    val description: String,
    val youtubeUrl: String,
    val history: String,
    val heritageNotes: String,
    val whereToExperience: String,
    val notableFeatures: List<String>,
    /** Primary gallery image (Local R.drawable or URL). */
    val imageUrl: Any,
    /** Hero image beside the explorer title. */
    val heroImageUrl: Any,
    /** Additional photos for the detail gallery strip. */
    val relatedImageUrls: List<Any>
)

data class Artisan(
    val name: String,
    val specialization: String,
    val district: String,
    val phone: String,
    val latLng: LatLng
)

data class Workshop(
    val title: String,
    val guruName: String,
    val level: String,
    val feeInr: Int,
    val date: String,
    val venue: String
)

data class CulturalEvent(
    val title: String,
    val date: String,
    val venue: String,
    val district: String
)

data class Product(
    val name: String,
    val category: String,
    val artisan: String,
    val priceInr: Int
)

data class WorkshopRegistration(
    val workshopTitle: String,
    val participantName: String,
    val phoneNumber: String,
    val participantsCount: Int
)

object DemoDataRepository {
    val artForms = listOf(
        ArtForm(
            name = "Yakshagana",
            region = "Udupi / Dakshina Kannada",
            description = "Classical narrative dance-drama with elaborate costumes.",
            youtubeUrl = "https://www.youtube.com/results?search_query=yakshagana+performance",
            history = "Yakshagana is a traditional theatre form from coastal Karnataka that blends dance, music, dialogue, and spectacle. Night-long performances retell episodes from the epics and regional lore with rhythmic percussion and richly embroidered costumes.",
            heritageNotes = "It is sustained by family troupes (melas), temples, and seasonal festivals. Distinct styles such as Badagutittu and Tenkutittu shape costumes, music, and choreography.",
            whereToExperience = "Coastal temple festivals, cultural sabhas in Udupi and Mangaluru, and dedicated winter performances near village maidans.",
            notableFeatures = listOf(
                "Percussion ensemble led by chende and maddale",
                "Elaborate headgear and facial expression grammar",
                "Improvised dialogue layered over structured sequences"),
            imageUrl = R.drawable.yakshagana_primary,
            heroImageUrl = R.drawable.yakshagana_primary,
            relatedImageUrls = listOf(R.drawable.yakshagana_gallery_2)
        ),
        ArtForm(
            name = "Dollu Kunitha",
            region = "Tumakuru",
            description = "Dynamic drum dance performed during festivals and processions.",
            youtubeUrl = "https://www.youtube.com/results?search_query=dollu+kunitha",
            history = "Dollu Kunitha is a powerful folk drum dance linked to Kuruba communities around Karnataka's central districts. Performers carry large drums slung across the torso and weave athletic leaps into circular formations.",
            heritageNotes = "Historically tied to rituals honoring Shiva, the practice expanded into secular celebrations while retaining devotional openings.",
            whereToExperience = "Dasara processions in Mysuru region, village fairs in Tumakuru, and curated folk showcases during statewide festivals.",
            notableFeatures = listOf(
                "Synchronized shoulder lifts with enormous drums",
                "Call-and-response singing between leads and chorus",
                "Explosive footwork mapped to percussive cadences"
            ),
            imageUrl = R.drawable.dollu_kunitha_primary,
            heroImageUrl = R.drawable.dollu_kunitha_primary,
            relatedImageUrls = listOf(R.drawable.dollu_kunitha_gallery_2)
        ),
        ArtForm(
            name = "Bidriware",
            region = "Bidar",
            description = "Metal craft with intricate silver inlay on alloy surfaces.",
            youtubeUrl = "https://www.youtube.com/results?search_query=bidriware+craft",
            history = "Bidriware emerged under the Bahmani courts and layers Persian motifs onto zinc-alloy bodies that turn charcoal-black after oxidation. Fine silver wire is inlaid before polishing reveals shimmering flora and geometry.",
            heritageNotes = "Master craftsmen still mine soil unique to Bidar fort precincts for the characteristic deep tone and texture.",
            whereToExperience = "Bidar artisan lanes, government emporiums, and curated craft exhibitions across Bengaluru and Hyderabad.",
            notableFeatures = listOf(
                "Secret soil treatments darkening the alloy",
                "Silver inlay embedded flush with mirror polish",
                "Motifs blending arabesques with local flora"
            ),
            imageUrl = R.drawable.bidriware_primary,
            heroImageUrl = R.drawable.bidriware_primary,
            relatedImageUrls = listOf(R.drawable.bidriware_gallery_4)
        ),
        ArtForm(
            name = "Ilkal Weaving",
            region = "Belagavi",
            description = "Traditional handloom sarees with signature borders and pallu.",
            youtubeUrl = "https://www.youtube.com/results?search_query=ilkal+weaving",
            history = "Ilkal sarees marry cotton body cloth with rayon or silk pallus using traditional pit looms. Kasuti embroidery often accents festive variants worn during Lingayat and rural ceremonies.",
            heritageNotes = "Weaver households coordinate dye lots so signature 'togalu' borders remain instantly recognizable across generations.",
            whereToExperience = "Ilkal town cooperative societies, handloom fairs in Belagavi, and artisan clusters along the Krishna basin.",
            notableFeatures = listOf(
                "Contrast borders woven separately then joined",
                "Jewel-toned pallus against earthy bodies",
                "Community rituals marking loom inauguration seasons"
            ),
            imageUrl = R.drawable.ilkal_gallery_2,
            heroImageUrl = R.drawable.ilkal_primary,
            relatedImageUrls = listOf(R.drawable.ilkal_primary)
        ),
        ArtForm(
            name = "Kinnala Toys",
            region = "Koppal",
            description = "Folk wooden and clay toys rooted in rural storytelling.",
            youtubeUrl = "https://www.youtube.com/results?search_query=kinnala+toys",
            history = "Artisans in Kinnala village sculpt lightweight figures from softwood, coat them with lime paste, and paint lucid narratives from mythology and village life.",
            heritageNotes = "The craft doubles as movable storytelling props during Dasara tableaux and classroom heritage drives.",
            whereToExperience = "Koppal village studios, folk museums in Gadag, and seasonal exhibitions traveling through Hubballi-Dharwad.",
            notableFeatures = listOf(
                "Vegetable pigments layered over gessoed wood",
                "Miniature ensembles depicting myth episodes",
                "Collaborative workshops reviving natural dyes"
            ),
            imageUrl = R.drawable.kinnala_primary,
            heroImageUrl = R.drawable.kinnala_primary,
            relatedImageUrls = listOf(R.drawable.kinnala_gallery_2)
        )
    )

    val artisans = listOf(
        Artisan("Ramesh Bidri", "Bidriware", "Bidar", "+919980011223", LatLng(17.9133, 77.5301)),
        Artisan("Savita Ilkal", "Ilkal Weaving", "Belagavi", "+919902223344", LatLng(15.8497, 74.4977)),
        Artisan("Mahadev Kinnala", "Kinnala Toys", "Koppal", "+919911112222", LatLng(15.3450, 76.1548)),
        Artisan("Prakash Yakshagana", "Yakshagana Coaching", "Udupi", "+918888776655", LatLng(13.3409, 74.7421))
    )

    val workshops = listOf(
        Workshop("Yakshagana Beginners Bootcamp", "Guru Prakash", "Beginner", 1200, "24 May 2026", "Udupi Town Hall"),
        Workshop("Bidriware Engraving Basics", "Shri Ramesh", "Beginner", 1800, "31 May 2026", "Bidar Craft Studio"),
        Workshop("Ilkal Weave Immersion", "Guru Savita", "Intermediate", 2200, "08 Jun 2026", "Belagavi Weavers Hub"),
        Workshop("Dollu Rhythm Lab", "Guru Shivu", "Advanced", 1500, "15 Jun 2026", "Tumakuru Folk Center")
    )

    val events = listOf(
        CulturalEvent("Yakshagana Night Performance", "24 May 2026", "Town Hall", "Udupi"),
        CulturalEvent("Dollu Kunitha Folk Fest", "02 Jun 2026", "Open Grounds", "Tumakuru"),
        CulturalEvent("Bidri Heritage Expo", "15 Jun 2026", "Craft Center", "Bidar"),
        CulturalEvent("Ilkal Weavers Festival", "29 Jun 2026", "Community Hall", "Belagavi")
    )

    val products = listOf(
        Product("Handcrafted Bidri Vase", "Bidriware", "Ramesh Bidri", 3200),
        Product("Ilkal Cotton Saree", "Weavings", "Savita Ilkal", 2800),
        Product("Traditional Kinnala Toy Set", "Toys", "Mahadev Kinnala", 900),
        Product("Folk Percussion Instrument", "Instruments", "Shivu Folk Crafts", 1500)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KarunadaKalaApp(workshopRegistrationRepository: WorkshopRegistrationRepository) {
    var showSplash by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1800)
        showSplash = false
    }

    AnimatedVisibility(
        visible = showSplash,
        enter = fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = 0.94f),
        exit = fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 1.05f)
    ) {
        CreativeOpeningSplash()
    }

    AnimatedVisibility(
        visible = !showSplash,
        enter = fadeIn(animationSpec = tween(350)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        KarunadaKalaMain(workshopRegistrationRepository = workshopRegistrationRepository)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KarunadaKalaMain(workshopRegistrationRepository: WorkshopRegistrationRepository) {
    var selectedTab by remember { mutableStateOf(HomeTab.Explorer) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Karunada-Kala",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Directory of Pride!",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily.Cursive,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.08.sp
                            ),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                tonalElevation = 3.dp,
                shadowElevation = 10.dp,
                shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
            ) {
                NavigationBar(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                    tonalElevation = 0.dp
                ) {
                    HomeTab.entries.forEach { tab ->
                        val selected = selectedTab == tab
                        NavigationBarItem(
                            selected = selected,
                            onClick = { selectedTab = tab },
                            icon = {
                                Icon(
                                    imageVector = tab.icon(),
                                    contentDescription = tab.label,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            HomeTab.Explorer -> ArtFormExplorerScreen(Modifier.padding(innerPadding))
            HomeTab.Map -> ArtisanMapScreen(Modifier.padding(innerPadding))
            HomeTab.Workshops -> WorkshopsScreen(
                modifier = Modifier.padding(innerPadding),
                workshopRegistrationRepository = workshopRegistrationRepository
            )
            HomeTab.Events -> EventsScreen(Modifier.padding(innerPadding))
            HomeTab.Marketplace -> MarketplaceScreen(Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun CreativeOpeningSplash() {
    val pulse = rememberInfiniteTransition(label = "splash")
    val scale by pulse.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(700),
            repeatMode = RepeatMode.Reverse
        ),
        label = "splash-scale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎭 🥁 🧵 🎨 🛍️",
            modifier = Modifier.scale(scale),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Welcome to Karunada-Kala",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Preserving Karnataka's art with pride",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun FramedExplorerArtImage(
    imageSource: Any,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val outerShape = RoundedCornerShape(14.dp)
    val matShape = RoundedCornerShape(10.dp)
    Box(
        modifier = modifier
            .width(118.dp)
            .aspectRatio(3f / 4f)
            .border(3.dp, MaterialTheme.colorScheme.primary, outerShape)
            .padding(6.dp)
            .background(MaterialTheme.colorScheme.surface, matShape)
            .padding(4.dp)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
            .padding(3.dp)
            .clip(RoundedCornerShape(6.dp))
    ) {
        AsyncImage(
            model = imageSource,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ArtFormRelatedGalleryStrip(
    artName: String,
    imageSources: List<Any>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            count = imageSources.size,
            key = { index -> "${artName}_$index" }
        ) { index ->
            val source = imageSources[index]
            val thumbShape = RoundedCornerShape(12.dp)
            Box(
                modifier = Modifier
                    .width(172.dp)
                    .aspectRatio(4f / 3f)
                    .border(2.dp, MaterialTheme.colorScheme.primary, thumbShape)
                    .padding(4.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(9.dp))
                    .padding(3.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(7.dp))
                    .padding(2.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                AsyncImage(
                    model = source,
                    contentDescription = "$artName related photo ${index + 1}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtFormExplorerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var detailArtForm by remember { mutableStateOf<ArtForm?>(null) }
    val detailSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    detailArtForm?.let { selected ->
        ModalBottomSheet(
            onDismissRequest = { detailArtForm = null },
            sheetState = detailSheetState
        ) {
            ArtFormDetailSheetContent(
                artForm = selected,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(bottom = 28.dp)
            )
        }
        LaunchedEffect(selected.name) {
            detailSheetState.expand()
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Discover Karnataka Art Forms",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        items(DemoDataRepository.artForms) { artForm ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FramedExplorerArtImage(
                            imageSource = artForm.heroImageUrl,
                            contentDescription = "${artForm.name} art"
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = artForm.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Cursive,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            )
                            Text(
                                artForm.region,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Text(artForm.description, style = MaterialTheme.typography.bodyMedium)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            modifier = Modifier.height(38.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            onClick = { detailArtForm = artForm }
                        ) {
                            Text("Read more", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            modifier = Modifier.height(38.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            onClick = {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, Uri.parse(artForm.youtubeUrl))
                                )
                            }
                        ) {
                            Text("Watch on YouTube", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArtFormDetailSheetContent(artForm: ArtForm, modifier: Modifier = Modifier) {
    val galleryImages = remember(artForm) {
        buildList {
            add(artForm.imageUrl)
            addAll(artForm.relatedImageUrls)
        }
    }
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Gallery",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        ArtFormRelatedGalleryStrip(artName = artForm.name, imageSources = galleryImages)
        Text(artForm.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("History", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Text(artForm.history, style = MaterialTheme.typography.bodyMedium)
        Text("Living heritage", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Text(artForm.heritageNotes, style = MaterialTheme.typography.bodyMedium)
        Text("Where to experience", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Text(artForm.whereToExperience, style = MaterialTheme.typography.bodyMedium)
        Text("Highlights", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        artForm.notableFeatures.forEach { line ->
            Text("• $line", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun ArtisanQuickCallRow(
    artisan: Artisan,
    useInteractiveMap: Boolean,
    onShowOnMap: (Artisan) -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Phone,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(26.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = artisan.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${artisan.specialization} · ${artisan.district}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = artisan.phone,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            FilledTonalButton(
                onClick = { dialArtisanPhone(context, artisan.phone) },
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text("Call", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
            }
            if (useInteractiveMap) {
                AssistChip(
                    onClick = { onShowOnMap(artisan) },
                    label = { Text("Map") }
                )
            }
        }
    }
}

@Composable
private fun ArtisanMapMarker(
    artisan: Artisan,
    onMarkerClick: () -> Unit
) {
    val markerState = rememberUpdatedMarkerState(position = artisan.latLng)
    Marker(
        state = markerState,
        title = artisan.name,
        snippet = "${artisan.specialization} - ${artisan.district}",
        onClick = {
            onMarkerClick()
            false
        }
    )
}

@Composable
private fun ArtisanMapScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var selectedType by remember { mutableStateOf("All") }
    var selectedArtisan by remember { mutableStateOf<Artisan?>(null) }
    val types = listOf("All", "Bidriware", "Ilkal Weaving", "Kinnala Toys", "Yakshagana Coaching")
    val karnatakaCenter = LatLng(15.3173, 75.7139)
    val mapAvailable = remember {
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
    }
    val hasValidMapsKey = rememberHasValidGoogleMapsApiKey()
    val useInteractiveMap = mapAvailable && hasValidMapsKey
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(karnatakaCenter, 6.8f)
    }
    LaunchedEffect(useInteractiveMap) {
        if (useInteractiveMap) {
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(karnatakaCenter, 6.8f))
        }
    }

    val visibleArtisans = DemoDataRepository.artisans.filter {
        selectedType == "All" || it.specialization == selectedType
    }

    val mapFallbackMessage = when {
        !mapAvailable ->
            "Google Play services are not available on this device, so the interactive map cannot load. You can still call artisans below."
        else ->
            "Maps API key missing or invalid in the merged manifest. Ensure `MAPS_API_KEY` is set in local.properties at the project root, then rebuild. You can still call artisans below."
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Artisan Map",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            types.forEach { type ->
                FilterChip(
                    selected = selectedType == type,
                    onClick = {
                        selectedType = type
                        selectedArtisan = null
                    },
                    label = { Text(type) }
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .defaultMinSize(minHeight = 260.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            if (useInteractiveMap) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = false),
                    uiSettings = MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = false)
                ) {
                    visibleArtisans.forEach { artisan ->
                        key(artisan.name) {
                            ArtisanMapMarker(
                                artisan = artisan,
                                onMarkerClick = {
                                    selectedArtisan = artisan
                                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(artisan.latLng, 9.5f))
                                }
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            mapFallbackMessage,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    items(visibleArtisans) { artisan ->
                        Text("${artisan.name} — ${artisan.specialization} (${artisan.district})")
                    }
                }
            }
        }

        if (useInteractiveMap) {
            AssistChip(
                onClick = {
                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(karnatakaCenter, 6.8f))
                },
                label = { Text("Show Karnataka State View") }
            )
        }

        Text(
            text = "Artisans",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Each name shows the craft and district. Use Call to open the dialer.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            visibleArtisans.forEach { artisan ->
                ArtisanQuickCallRow(
                    artisan = artisan,
                    useInteractiveMap = useInteractiveMap,
                    onShowOnMap = { a ->
                        selectedArtisan = a
                        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(a.latLng, 9.5f))
                    }
                )
            }
        }

        selectedArtisan?.let { artisan ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(artisan.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("${artisan.specialization} - ${artisan.district}")
                    Text("Phone: ${artisan.phone}")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilledTonalButton(
                            onClick = { dialArtisanPhone(context, artisan.phone) },
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Outlined.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Call ${artisan.name.split(" ").firstOrNull() ?: "artisan"}")
                        }
                        AssistChip(
                            onClick = {
                                val mapIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("geo:${artisan.latLng.latitude},${artisan.latLng.longitude}?q=${Uri.encode(artisan.name)}")
                                )
                                if (mapIntent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(mapIntent)
                                }
                            },
                            label = { Text("Open Directions") }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkshopsScreen(
    modifier: Modifier = Modifier,
    workshopRegistrationRepository: WorkshopRegistrationRepository
) {
    var selectedWorkshop by rememberSaveable { mutableStateOf(DemoDataRepository.workshops.first().title) }
    var workshopMenuExpanded by remember { mutableStateOf(false) }
    var participantName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var participantsCountText by rememberSaveable { mutableStateOf("1") }
    var registrationMessage by rememberSaveable { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val workshopRegistrations by workshopRegistrationRepository.observeAll().collectAsState(initial = emptyList())
    val selectedWorkshopEvent = DemoDataRepository.workshops.firstOrNull { it.title == selectedWorkshop }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Workshops & Learning", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Workshop Registration", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Text("Choose a workshop from the list")
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = selectedWorkshop,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { workshopMenuExpanded = true },
                            label = { Text("Workshop event") },
                            trailingIcon = {
                                Text(
                                    text = if (workshopMenuExpanded) "▲" else "▼",
                                    modifier = Modifier.clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) { workshopMenuExpanded = !workshopMenuExpanded },
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = workshopMenuExpanded,
                            onDismissRequest = { workshopMenuExpanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DemoDataRepository.workshops.forEach { workshop ->
                                DropdownMenuItem(
                                    text = {
                                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                            Text(workshop.title, fontWeight = FontWeight.SemiBold)
                                            Text(
                                                "${workshop.date} · ${workshop.venue}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                "Guru ${workshop.guruName} · ${workshop.level} · ₹${workshop.feeInr}",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    },
                                    onClick = {
                                        selectedWorkshop = workshop.title
                                        workshopMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = participantName,
                        onValueChange = { participantName = it },
                        label = { Text("Participant Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = participantsCountText,
                        onValueChange = { participantsCountText = it.filter(Char::isDigit).take(2) },
                        label = { Text("Number of Participants") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            val participantCount = participantsCountText.toIntOrNull() ?: 0
                            val validPhone = phoneNumber.filter(Char::isDigit).length >= 10
                            if (participantName.isBlank() || !validPhone || participantCount <= 0) {
                                registrationMessage = "Please enter a valid name, phone number, and participant count."
                                return@Button
                            }
                            scope.launch {
                                workshopRegistrationRepository.add(
                                    WorkshopRegistration(
                                        workshopTitle = selectedWorkshop,
                                        participantName = participantName.trim(),
                                        phoneNumber = phoneNumber.trim(),
                                        participantsCount = participantCount
                                    )
                                )
                            }
                            registrationMessage = "Registration submitted for $selectedWorkshop."
                            participantName = ""
                            phoneNumber = ""
                            participantsCountText = "1"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Register Now")
                    }
                    registrationMessage?.let {
                        Text(it, color = MaterialTheme.colorScheme.primary)
                    }
                    selectedWorkshopEvent?.let {
                        Text(
                            text = "Registering for: ${it.title} on ${it.date} at ${it.venue}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        items(DemoDataRepository.workshops) { workshop ->
            InfoCard(
                title = workshop.title,
                subtitle = "${workshop.guruName} - ${workshop.level}",
                body = "Date: ${workshop.date} | Venue: ${workshop.venue} | Fee: INR ${workshop.feeInr}"
            )
        }

        if (workshopRegistrations.isNotEmpty()) {
            item {
                Text("Recent Registrations", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            items(workshopRegistrations) { registration ->
                InfoCard(
                    title = registration.workshopTitle,
                    subtitle = registration.participantName,
                    body = "Phone: ${registration.phoneNumber} | Participants: ${registration.participantsCount}"
                )
            }
        }
    }
}

@Composable
private fun EventsScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Upcoming Performances", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        items(DemoDataRepository.events) { event ->
            InfoCard(
                title = event.title,
                subtitle = "${event.date} - ${event.venue}",
                body = "District: ${event.district} | Actions: Interested / Going"
            )
        }
    }
}

@Composable
private fun MarketplaceScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Artisan Marketplace", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        items(DemoDataRepository.products) { product ->
            InfoCard(
                title = product.name,
                subtitle = "${product.category} - ${product.artisan}",
                body = "Price: INR ${product.priceInr} | Contact artisan for custom order"
            )
        }
    }
}

@Composable
private fun InfoCard(title: String, subtitle: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            Text(body, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
