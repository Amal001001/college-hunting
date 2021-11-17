package com.example.amal.database

import androidx.lifecycle.LiveData


class UniversitiesRepository(private val universitiesDao: UniversitiesDao) {

    val getUniversities: LiveData<List<DBUniversities>> = universitiesDao.getUniversities()

        suspend fun addUniversity(university: DBUniversities){
            universitiesDao.addUniversity(university)
        }

        suspend fun updateUniversity(university: DBUniversities){
            universitiesDao.updateUniversity(university)
        }

        suspend fun deleteUniversity(university: DBUniversities){
            universitiesDao.deleteUniversity(university)
        }
}