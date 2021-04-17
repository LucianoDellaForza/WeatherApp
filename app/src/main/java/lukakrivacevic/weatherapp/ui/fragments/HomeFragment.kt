package lukakrivacevic.weatherapp.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import lukakrivacevic.weatherapp.R
import lukakrivacevic.weatherapp.adapters.CityAdapter
import lukakrivacevic.weatherapp.data.local.entities.City
import lukakrivacevic.weatherapp.ui.MainActivity
import lukakrivacevic.weatherapp.ui.viewmodels.CityViewModel

class HomeFragment : Fragment(R.layout.fragment_home), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    lateinit var cityViewModel: CityViewModel
    lateinit var cityAdapter: CityAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityViewModel = (activity as MainActivity).cityViewModel

        setupRecyclerView()
        cityAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("city", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_cityWeatherForecastFragment,
                bundle
            )
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            //delete on swipe
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val city = cityAdapter.differ.currentList[position]
                cityViewModel.deleteCity(city)
                Snackbar.make(view, "${city.name} removed from location bookmarks.", Snackbar.LENGTH_LONG).show()
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(rvCities)
        }

        cityViewModel.getAllCities().observe(viewLifecycleOwner, Observer {
            cityAdapter.differ.submitList(it)
        })

        //test for add
        btnAddCity.setOnClickListener {
            if (etCityName.text.isEmpty() || etCityLon.text.isEmpty() || etCityLat.text.isEmpty()) {
                return@setOnClickListener
            } else {
                cityViewModel.saveCity(City(null, etCityName.text.toString(), etCityLon.text.toString(), etCityLat.text.toString()))
                Snackbar.make(view, "New city saved", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnWebView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_webViewFragment)
        }


        setupMap()
    }

    private fun setupRecyclerView() {
        cityAdapter = CityAdapter()
        rvCities.apply {
            adapter = cityAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    //**  Map stuff  :(  **/
    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mLocationRequest: LocationRequest? = null
    var mCustomLocation: LatLng? = null
    private lateinit var mMap: GoogleMap

    private fun setupMap() {
//        val mapFragment = fragmentManager?.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mMap = it
        }
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.uiSettings?.isZoomControlsEnabled = true  //+/- zoom
        mMap?.uiSettings?.isZoomGesturesEnabled = true  //hand zoom
        mMap?.uiSettings?.isCompassEnabled = true    //compass
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(),  Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap?.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mMap?.isMyLocationEnabled = true
        }
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(requireContext())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mGoogleApiClient?.connect()
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest?.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

//        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
//                mLocationRequest, this)
//        }
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }
}