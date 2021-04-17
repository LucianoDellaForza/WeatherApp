package lukakrivacevic.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import lukakrivacevic.weatherapp.R
import lukakrivacevic.weatherapp.data.local.CityDatabase
import lukakrivacevic.weatherapp.data.repository.CityRepositoryImpl
import lukakrivacevic.weatherapp.data.repository.WeatherRepository
import lukakrivacevic.weatherapp.data.repository.WeatherRepositoryImpl
import lukakrivacevic.weatherapp.ui.viewmodels.CityViewModel
import lukakrivacevic.weatherapp.ui.viewmodels.CityViewModelProviderFactory
import lukakrivacevic.weatherapp.ui.viewmodels.WeatherViewModel
import lukakrivacevic.weatherapp.ui.viewmodels.WeatherViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    lateinit var cityViewModel: CityViewModel
    lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityRepository = CityRepositoryImpl(CityDatabase(this))
        val cityViewModelProviderFactory = CityViewModelProviderFactory(cityRepository)
        cityViewModel = ViewModelProvider(this, cityViewModelProviderFactory).get(CityViewModel::class.java)

        val weatherRepository = WeatherRepositoryImpl()
        val weatherViewModelProviderFactory = WeatherViewModelProviderFactory(weatherRepository)
        weatherViewModel = ViewModelProvider(this, weatherViewModelProviderFactory).get(WeatherViewModel::class.java)
    }
}