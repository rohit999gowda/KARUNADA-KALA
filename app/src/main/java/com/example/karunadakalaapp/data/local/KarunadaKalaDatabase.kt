package com.example.karunadakalaapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [WorkshopRegistrationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KarunadaKalaDatabase : RoomDatabase() {
    abstract fun workshopRegistrationDao(): WorkshopRegistrationDao

    companion object {
        @Volatile
        private var INSTANCE: KarunadaKalaDatabase? = null

        fun getInstance(context: Context): KarunadaKalaDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    KarunadaKalaDatabase::class.java,
                    "karunada_kala.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
