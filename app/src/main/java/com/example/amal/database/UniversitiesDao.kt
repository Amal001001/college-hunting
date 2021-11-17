package com.example.amal.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UniversitiesDao {
            @Insert(onConflict = OnConflictStrategy.IGNORE)
            suspend fun addUniversity(university: DBUniversities)

            @Query("SELECT * FROM UniversityTable ORDER BY id ASC")
            fun getUniversities(): LiveData<List<DBUniversities>>

            @Update
            suspend fun updateUniversity(university: DBUniversities)

            @Delete
            suspend fun deleteUniversity(university: DBUniversities)
}