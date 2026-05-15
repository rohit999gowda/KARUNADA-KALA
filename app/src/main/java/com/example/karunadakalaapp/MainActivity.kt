package com.example.karunadakalaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.karunadakalaapp.data.WorkshopRegistrationRepository
import com.example.karunadakalaapp.data.local.KarunadaKalaDatabase
import com.example.karunadakalaapp.ui.theme.KARUNADAKALAAPPTheme
import com.google.android.gms.maps.MapsInitializer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runCatching {
            MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST) { }
        }
        val repository = WorkshopRegistrationRepository(
            KarunadaKalaDatabase.getInstance(applicationContext).workshopRegistrationDao()
        )
        enableEdgeToEdge()
        setContent {
            KARUNADAKALAAPPTheme {
                KarunadaKalaApp(workshopRegistrationRepository = repository)
            }
        }
    }
}