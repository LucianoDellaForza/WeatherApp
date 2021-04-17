package lukakrivacevic.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lukakrivacevic.weatherapp.data.repository.CityRepository

class CityViewModelProviderFactory(
    private val cityRepository: CityRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityViewModel(cityRepository) as T
    }
}