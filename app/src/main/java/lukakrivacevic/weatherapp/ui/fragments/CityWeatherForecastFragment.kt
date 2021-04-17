package lukakrivacevic.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_city_weather_forecast.*
import kotlinx.android.synthetic.main.item_city.*
import lukakrivacevic.weatherapp.R
import lukakrivacevic.weatherapp.data.remote.models.WeatherResponse
import lukakrivacevic.weatherapp.ui.MainActivity
import lukakrivacevic.weatherapp.ui.viewmodels.WeatherViewModel
import lukakrivacevic.weatherapp.util.Constants.METRIC
import lukakrivacevic.weatherapp.util.Resource

const val TAG = "CityWeatherFragment"

class CityWeatherForecastFragment : Fragment(R.layout.fragment_city_weather_forecast) {

    lateinit var weatherViewModel: WeatherViewModel

    val args: CityWeatherForecastFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel = (activity as MainActivity).weatherViewModel

        //fetchovacu ovde
        val city = args.city
        weatherViewModel.getCityWeatherForecast(city.longitude, city.latitude)
        Log.e(TAG, city.name + "," + city.latitude + "," + city.longitude)
        tvCityNameHeadline.text = city.name

        weatherViewModel.cityWeatherReport.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    progressBar.visibility = View.INVISIBLE
                    tvLoading.visibility = View.INVISIBLE
                    holderLayout.visibility = View.VISIBLE
                    it.data?.let { weatherResponse ->
                        updateUi(weatherResponse)
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.INVISIBLE
                    tvLoading.visibility = View.INVISIBLE
                    holderLayout.visibility = View.INVISIBLE
                    it.message?.let {message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    tvLoading.visibility = View.VISIBLE
                    holderLayout.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun updateUi(weatherResponse: WeatherResponse) {
        //Metric sistem je default pri slanju API-ju. Mogao bih da dodam spinner za menjanje u imperial
        val degreeScale = if(METRIC) " C" else " K"
        val unitOfSpeed = if(METRIC) "km/h" else "mph"
        tvTemperature.text = weatherResponse.main.temp.toString() + degreeScale
        tvMinTemperature.text = weatherResponse.main.temp_min.toString() + degreeScale
        tvMaxTemperature.text = weatherResponse.main.temp_max.toString() + degreeScale
        tvHumidity.text = weatherResponse.main.humidity.toString() + "%"
        tvWindSpeed.text = weatherResponse.wind.speed.toString() + unitOfSpeed
        //tvRainChances.text = weatherResponse.   //ne postoji ovaj info, nadji formulu na netu ako postoji
    }
}