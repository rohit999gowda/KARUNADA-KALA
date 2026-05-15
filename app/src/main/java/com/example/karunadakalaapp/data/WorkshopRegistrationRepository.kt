package com.example.karunadakalaapp.data

import com.example.karunadakalaapp.WorkshopRegistration
import com.example.karunadakalaapp.data.local.WorkshopRegistrationDao
import com.example.karunadakalaapp.data.local.WorkshopRegistrationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WorkshopRegistrationRepository(
    private val dao: WorkshopRegistrationDao
) {
    fun observeAll(): Flow<List<WorkshopRegistration>> {
        return dao.observeAll().map { entities ->
            entities.map {
                WorkshopRegistration(
                    workshopTitle = it.workshopTitle,
                    participantName = it.participantName,
                    phoneNumber = it.phoneNumber,
                    participantsCount = it.participantsCount
                )
            }
        }
    }

    suspend fun add(registration: WorkshopRegistration) {
        dao.insert(
            WorkshopRegistrationEntity(
                workshopTitle = registration.workshopTitle,
                participantName = registration.participantName,
                phoneNumber = registration.phoneNumber,
                participantsCount = registration.participantsCount
            )
        )
    }
}
