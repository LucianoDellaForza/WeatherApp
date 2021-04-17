package lukakrivacevic.weatherapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import lukakrivacevic.weatherapp.data.local.entities.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City): Long    //return id that was inserted

    @Query("SELECT * FROM cities")
    fun getAllCities(): LiveData<List<City>>

    @Delete
    suspend fun deleteArticle(city: City)

}