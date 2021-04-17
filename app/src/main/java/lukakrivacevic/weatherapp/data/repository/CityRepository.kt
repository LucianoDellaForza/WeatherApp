package lukakrivacevic.weatherapp.data.repository

import androidx.lifecycle.LiveData
import lukakrivacevic.weatherapp.data.local.entities.City

interface CityRepository {

    suspend fun insert(city: City): Long

    fun getAllCities(): LiveData<List<City>>

    suspend fun deleteCity(city: City)
}