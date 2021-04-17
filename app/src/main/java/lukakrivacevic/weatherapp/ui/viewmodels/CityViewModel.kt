package lukakrivacevic.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lukakrivacevic.weatherapp.data.local.entities.City
import lukakrivacevic.weatherapp.data.repository.CityRepository

class CityViewModel(
    private val cityRepository: CityRepository
) : ViewModel() {

    fun saveCity(city: City) = viewModelScope.launch {
        cityRepository.insert(city)
    }

    fun getAllCities() = cityRepository.getAllCities()

    fun deleteCity(city: City) = viewModelScope.launch {
        cityRepository.deleteCity(city)
    }

}