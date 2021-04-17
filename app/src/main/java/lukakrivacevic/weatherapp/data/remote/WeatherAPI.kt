package lukakrivacevic.weatherapp.data.remote

import lukakrivacevic.weatherapp.data.remote.models.WeatherResponse
import lukakrivacevic.weatherapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather")
    suspend fun getCityWeather(
        @Query("lon") longitude: String,
        @Query("lat") latitude: String,
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>
}