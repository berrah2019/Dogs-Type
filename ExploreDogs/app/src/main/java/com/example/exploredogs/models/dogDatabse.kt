package com.example.exploredogs.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Dogbreed::class),version = 1)
abstract class dogDatabse:RoomDatabase() {
    abstract fun dogdao(): DogDoa

    companion object {
        @Volatile
        private var instance: dogDatabse? = null
        private var Lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: buildDatabase(context).also {
                instance=it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            dogDatabse::class.java,
            "dog Database"
        ).build()
    }

}


