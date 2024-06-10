package com.fitsport.asfer.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fitsport.asfer.databinding.FragmentSettingsListBinding

class SettingsListFragment : Fragment() {
    private var _binding: FragmentSettingsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)

        binding.clearResults.setOnClickListener {
            clearResults()
            showResultsClearedMessage()
        }

        binding.saveResults.setOnClickListener {
            saveRadioGroupSelection()
            showSaveConfirmation()
        }

        loadRadioGroupSelection()
    }

    private fun clearResults() {
        val pref = requireContext().getSharedPreferences("SurveyPrefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    private fun showResultsClearedMessage() {
        Toast.makeText(requireContext(), "Результаты удалены, перезагрузите приложение", Toast.LENGTH_LONG).show()
    }

    private fun saveRadioGroupSelection() {
        val selectedId = binding.radioSet.checkedRadioButtonId
        val pref = requireContext().getSharedPreferences("SurveyPrefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("SelectedRadioButtonId", selectedId)
        editor.apply()
    }

    private fun loadRadioGroupSelection() {
        val pref = requireContext().getSharedPreferences("SurveyPrefs", Context.MODE_PRIVATE)
        val selectedId = pref.getInt("SelectedRadioButtonId", -1)
        if (selectedId != -1) {
            binding.radioSet.check(selectedId)
        }
    }

    private fun showSaveConfirmation() {
        Toast.makeText(requireContext(), "Данные сохранены", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
