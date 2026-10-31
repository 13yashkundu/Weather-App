package com.example.weatherz_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val vm : Weather_ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val textView = findViewById<TextView>(R.id.response)
        val retryBtn = findViewById<Button>(R.id.retryButton)
        fun loadWeather() {
            vm.fetchWeather(
                BuildConfig.API_Key,
                "New Delhi",
                onLoading = { loading ->
                    if (loading) {
                        textView.text = "Weather loading..."
                        retryBtn.visibility = View.GONE
                    }
                }
            ) { response ->
                if (response != null) {
                    textView.text =
                        "City: ${response.location.name}\nTemp: ${response.current.temp_c}°C\nWeather: ${response.current.condition.text}"
                    retryBtn.visibility = View.GONE
                } else {
                    textView.text = "Error fetching weather data!"
                    retryBtn.visibility = View.VISIBLE
                }
            }
        }
        loadWeather()
        retryBtn.setOnClickListener {
            loadWeather()
        }
    }
}