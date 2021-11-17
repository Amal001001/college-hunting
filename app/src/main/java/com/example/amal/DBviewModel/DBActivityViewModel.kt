package com.example.amal.DBviewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.amal.database.DBUniversities
import com.example.amal.database.UniversitiesDatabase
import com.example.amal.database.UniversitiesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DBActivityViewModel (application: Application): AndroidViewModel(application){
     private val repository: UniversitiesRepository
        private val universities: LiveData<List<DBUniversities>>

        init {
            val universitiesDao = UniversitiesDatabase.getDatabase(application).universitiesDao()
            repository = UniversitiesRepository(universitiesDao)
            universities = repository.getUniversities
        }

        fun readFromDB(): LiveData<List<DBUniversities>> {
                return universities
        }

        fun addUniversity(name: String, country: String, note: String) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.addUniversity(DBUniversities(0, name,country,""))
                readFromDB()
            }
        }

        fun updateUniversity(id: Int,name: String, country: String, note: String) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.updateUniversity(DBUniversities(id,name, country, note))
                readFromDB()
            }
        }

        fun deleteUniversity(id: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.deleteUniversity(DBUniversities(id, "","",""))
                readFromDB()
            }
        }
}