package lukakrivacevic.weatherapp.data.repository

import lukakrivacevic.weatherapp.data.remote.RetrofitInstance

class WeatherRepositoryImpl : WeatherRepository {

    override suspend fun getCityWeatherForecast(
        longitude: String,
        latitude: String
    ) = RetrofitInstance.api.getCityWeather(longitude, latitude)
}