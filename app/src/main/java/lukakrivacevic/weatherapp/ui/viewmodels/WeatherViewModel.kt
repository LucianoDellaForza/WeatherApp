package lukakrivacevic.weatherapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lukakrivacevic.weatherapp.data.remote.models.WeatherResponse
import lukakrivacevic.weatherapp.data.repository.WeatherRepository
import lukakrivacevic.weatherapp.util.Resource
import retrofit2.Response

class WeatherViewModel (
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val cityWeatherReport: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()

    fun getCityWeatherForecast(longitude: String, latitude: String) = viewModelScope.launch {
        cityWeatherReport.postValue(Resource.Loading())
        val response = weatherRepository.getCityWeatherForecast(longitude, latitude)
        cityWeatherReport.postValue(handleWeatherResponse(response))
    }

    private fun handleWeatherResponse(response: Response<WeatherResponse>) : Resource<WeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}