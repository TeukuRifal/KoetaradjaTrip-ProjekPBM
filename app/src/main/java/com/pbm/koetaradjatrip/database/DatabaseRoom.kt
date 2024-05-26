package com.pbm.koetaradjatrip.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pbm.koetaradjatrip.models.Data

@Database(entities = [Data::class], version = 2)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseRoom? = null

        fun getDatabase(context: Context): DatabaseRoom {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseRoom::class.java,
                    "data_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}