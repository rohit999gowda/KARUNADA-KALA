package com.example.karunadakalaapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workshop_registrations")
data class WorkshopRegistrationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val workshopTitle: String,
    val participantName: String,
    val phoneNumber: String,
    val participantsCount: Int
)
