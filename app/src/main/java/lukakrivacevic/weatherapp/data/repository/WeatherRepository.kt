package lukakrivacevic.weatherapp.data.repository

import lukakrivacevic.weatherapp.data.remote.models.WeatherResponse
import retrofit2.Response

interface WeatherRepository {

    suspend fun getCityWeatherForecast(longitude: String, latitude: String): Response<WeatherResponse>
}