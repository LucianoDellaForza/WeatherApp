package lukakrivacevic.weatherapp.data.repository

import androidx.lifecycle.LiveData
import lukakrivacevic.weatherapp.data.local.CityDatabase
import lukakrivacevic.weatherapp.data.local.entities.City

class CityRepositoryImpl (
    private val db: CityDatabase
) : CityRepository {

    override suspend fun insert(city: City): Long = db.getCityDao().insert(city)

    override fun getAllCities(): LiveData<List<City>> = db.getCityDao().getAllCities()

    override suspend fun deleteCity(city: City) = db.getCityDao().deleteArticle(city)

}