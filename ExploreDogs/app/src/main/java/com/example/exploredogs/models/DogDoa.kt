package com.example.exploredogs.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDoa {
    @Insert
    suspend fun insertAll(vararg dogs: Dogbreed): List<Long>

    @Query("SELECT * from dogbreed")
    suspend fun getAllDogs(): List<Dogbreed>

    @Query("SELECT * FROM dogbreed  WHERE uuid=:dogid")
    suspend fun getDog(dogid: Int): Dogbreed

    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs()

}