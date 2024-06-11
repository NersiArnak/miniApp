package com.fitsport.asfer.pedometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fitsport.asfer.databinding.FragmentPedometerListBinding

class PedometerListFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepsCount = 0
    private var binding: FragmentPedometerListBinding? = null

    private val STRONG_MOVEMENT_THRESHOLD = 30.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPedometerListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)

        loadStepsGoal()

        binding?.saveStepsDay?.setOnClickListener {
            saveStepsGoal()
        }
    }

    override fun onResume() {
        super.onResume()
        val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val acceleration = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()

            if (acceleration > STRONG_MOVEMENT_THRESHOLD) {
                stepsCount++
                binding?.stepsTextView?.text = "Шагов: $stepsCount"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun saveStepsGoal() {
        val stepsGoal = binding?.stepsGoalEditText?.text.toString().toIntOrNull()
        if (stepsGoal != null) {
            val pref = requireContext().getSharedPreferences("PedometerPrefs", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putInt("DailyStepsGoal", stepsGoal)
            editor.apply()
            Toast.makeText(requireContext(), "Цель сохранена", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Пожалуйста, введите правильное число", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadStepsGoal() {
        val pref = requireContext().getSharedPreferences("PedometerPrefs", Context.MODE_PRIVATE)
        if (pref.contains("DailyStepsGoal")) {
            val stepsGoal = pref.getInt("DailyStepsGoal", 0)
            binding?.stepsGoalEditText?.setText(stepsGoal.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
