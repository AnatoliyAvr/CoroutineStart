package com.tolikavr.coroutinestart

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.tolikavr.coroutinestart.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

  private val binding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    binding.buttonLoad.setOnClickListener {
      loadData()
    }
  }

  private fun loadData() {
    Log.d("MainActivity", "Load started $this")
    binding.progress.isVisible = true
    binding.buttonLoad.isEnabled = false
    loadCity { city ->
      binding.tvLocation.text = city
      loadTemperature(city) { temp ->
        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
        Log.d("MainActivity", "Load finished $this")
      }
    }
  }

  private fun loadCity(callback: (String) -> Unit) {
    thread {
      Thread.sleep(5000)
      runOnUiThread {
        callback.invoke("Moscow")
      }
    }
  }

  private fun loadTemperature(city: String, callback: (Int) -> Unit) {
    thread {
      runOnUiThread {
        Toast.makeText(
          this,
          getString(R.string.loading_temperature_toast, city),
          Toast.LENGTH_SHORT
        ).show()
      }
      Thread.sleep(5000)
      runOnUiThread {
        callback.invoke(17)
      }
    }
  }
}