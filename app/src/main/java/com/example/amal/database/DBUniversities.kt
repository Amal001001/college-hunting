package com.example.amal.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UniversityTable")
data class DBUniversities(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val country: String,
    val note: String)
