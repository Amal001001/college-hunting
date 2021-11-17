package com.example.amal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DBUniversities::class], version = 1, exportSchema = false)
abstract class UniversitiesDatabase: RoomDatabase() {

    abstract fun universitiesDao(): UniversitiesDao

        companion object{
            @Volatile  // writes to this field are immediately visible to other threads
            private var INSTANCE: UniversitiesDatabase? = null

            fun getDatabase(context: Context): UniversitiesDatabase{
                val tempInstance = INSTANCE
                if(tempInstance != null){
                    return tempInstance
                }
                synchronized(this){  // protection from concurrent execution on multiple threads
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UniversitiesDatabase::class.java,
                        "Universities_database"
                    ).fallbackToDestructiveMigration()  // Destroys old database on version change
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
}