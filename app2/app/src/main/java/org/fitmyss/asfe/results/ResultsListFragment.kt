package org.fitmyss.asfe.results

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.fitmyss.asfe.R
import org.fitmyss.asfe.databinding.FragmentResultsListBinding
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class ResultsListFragment : Fragment() {

    private var _binding: FragmentResultsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferences
    private lateinit var dataHelper: DataHelper
    private val timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)
        super.onViewCreated(view, savedInstanceState)
        dataHelper = DataHelper(requireContext())

        binding.startButton.setOnClickListener { startStopAction() }
        binding.resetButton.setOnClickListener { resetAction() }
        binding.buttonTraining.setOnClickListener {
            findNavController().navigate(R.id.TrainingsFragment)
        }
        binding.buttonMarathon.setOnClickListener {
            findNavController().navigate(R.id.MarathonFragment)
        }

        if (dataHelper.timerCounting()) {
            startTimer(requireContext())
        } else {
            if (dataHelper.startTime() != null && dataHelper.stopTime() != null) {
                val time = Date().time - calcRestartTime().time
                binding.time.text = timeStringFromLong(time)
            }
        }

        timer.scheduleAtFixedRate(TimeTask(), 0, 500)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class TimeTask : TimerTask() {
        private val handler = Handler(Looper.getMainLooper())

        override fun run() {
            handler.post {
                if (_binding != null) {
                    val binding = _binding ?: return@post
                    if (dataHelper.timerCounting() && dataHelper.startTime() != null) {
                        val time = Date().time - dataHelper.startTime()!!.time
                        binding.time.text = timeStringFromLong(time)
                    }
                }
            }
        }
    }

    private fun resetAction() {
        dataHelper.setStopTime(null)
        dataHelper.setStartTime(null)
        stopTimer(requireContext())
        binding.time.text = timeStringFromLong(0)
    }

    private fun stopTimer(context: Context) {
        dataHelper.setTimerCounting(false)
        updateStartButtonText()
    }

    private fun startTimer(context: Context) {
        dataHelper.setTimerCounting(true)
        updateStartButtonText()
    }

    private fun startStopAction() {
        val context = requireContext()
        if (dataHelper.timerCounting()) {
            dataHelper.setStopTime(Date())
            stopTimer(context)
        } else {
            if (dataHelper.stopTime() != null) {
                dataHelper.setStartTime(calcRestartTime())
                dataHelper.setStopTime(null)
            } else {
                dataHelper.setStartTime(Date())
            }
            startTimer(context)
        }
    }

    private fun calcRestartTime(): Date {
        val startTime = dataHelper.startTime()
        val stopTime = dataHelper.stopTime()

        if (startTime != null && stopTime != null) {
            val diff = startTime.time - stopTime.time
            return Date(System.currentTimeMillis() + diff)
        } else {
            // Возвращаем текущее время, если startTime или stopTime равны null
            return Date()
        }
    }

    private fun timeStringFromLong(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes = ((ms / (1000 * 60)) % 60)
        val hours = ((ms / (1000 * 60 * 60)) % 24)
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun updateStartButtonText() {
        val context = requireContext()
        if (dataHelper.timerCounting()) {
            binding.startButton.icon = AppCompatResources.getDrawable(context, R.drawable.ic_pause)
        } else {
            binding.startButton.icon = AppCompatResources.getDrawable(context, R.drawable.ic_play)
        }
    }
}
