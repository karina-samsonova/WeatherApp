package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.current_weather.presentation.ui.CurrentWeatherFragment
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.di.MainApp
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    @Inject
    lateinit var fragment: CurrentWeatherFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        (application as MainApp).appComponent.inject(this)

        setCurrentFragment(fragment)

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (fragment.viewModel.isReady) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            }
        )
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, fragment)
            commit()
        }
}