package com.example.current_weather.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.current_weather.R
import com.example.current_weather.databinding.FragmentCurrentWeatherBinding
import com.example.current_weather.di.CurrentWeatherComponent
import com.example.current_weather.di.DaggerCurrentWeatherComponent
import com.example.current_weather.domain.model.CurrentWeather
import com.example.current_weather.presentation.viewModel.CurrentWeatherViewModel
import com.example.network.LoadingState
import com.example.network.di.DaggerNetworkComponent
import com.example.network.di.NetworkComponent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CurrentWeatherFragment @Inject constructor() : Fragment() {
    private lateinit var binding: FragmentCurrentWeatherBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CurrentWeatherViewModel by viewModels { viewModelFactory }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(
                    requireContext(),
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getLocation()
            }
        }

    override fun onAttach(context: Context) {
        getComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getLocation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentWeatherBinding.bind(view)

        initialiseObservers()
        initialiseUIElements()
    }

    private fun getComponent(): CurrentWeatherComponent {
        val networkComponent: NetworkComponent = DaggerNetworkComponent.builder()
            .build()

        val currentWeatherComponent = DaggerCurrentWeatherComponent.builder()
            .networkComponent(networkComponent)
            .build()

        return currentWeatherComponent
    }

    private fun initialiseUIElements() {
        binding.rootLayout.visibility = View.INVISIBLE

        binding.loadingBtn.setOnClickListener{
            getLocation()
        }
    }

    private fun initialiseObservers() {
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            setUpCurrentWeather(it)
        }
        viewModel.loadingStateLiveData.observe(viewLifecycleOwner) {
            onLoadingStateChanged(it)
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()
            return
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { lastLocation : Location? ->
                            if (lastLocation == null) {
                                Toast.makeText(requireContext(), "Cannot get location", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                viewModel.getCurrentWeather(
                                    "${lastLocation.latitude}, ${lastLocation.longitude}",
                                    resources.getString(R.string.lang)
                                )
                            }
                        }
                }
                else {
                    viewModel.getCurrentWeather(
                        "${location.latitude}, ${location.longitude}",
                        resources.getString(R.string.lang)
                    )
                }
            }
    }

    private fun setUpCurrentWeather(weather: CurrentWeather) {
        Glide.with(requireContext())
            .load("https:${weather.conditionIcon}")
            .into(binding.weatherImageView)

        binding.cityTextView.text = weather.locationName
        binding.degreeTextView.text =
            resources.getString(R.string.number_celsius, weather.temp_c.toInt())
        binding.conditionTextView.text = weather.conditionText
        binding.feelsLikeTextView.text =
            resources.getString(R.string.feels_like, weather.feelslike_c.toInt())
        binding.windTextView.text = resources.getString(R.string.kph, weather.wind_kph.toInt())
        binding.humidityTextView.text = resources.getString(R.string.percent ,weather.humidity)
        binding.pressureTextView.text = resources.getString(R.string.millibars, weather.pressure_mb)

        val viewDateFormat = SimpleDateFormat("EEEE, d MMM, HH:mm", Locale.getDefault())
        binding.dateTextView.text = viewDateFormat.format(Date())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                binding.loadingBtn.setLoading(false)
                binding.rootLayout.visibility = View.VISIBLE
            }

            LoadingState.LOADING -> {
                binding.loadingBtn.setLoading(true)
            }

            LoadingState.ERROR -> {
                binding.progressBar.visibility = View.GONE
                binding.loadingBtn.setLoading(false)
                Toast.makeText(requireContext(), "Error Occurred!", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).toTypedArray()

        @JvmStatic
        fun newInstance() = CurrentWeatherFragment()
    }
}