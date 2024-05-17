package org.fitmyss.asfe.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.fitmyss.asfe.databinding.FragmentSettingsListBinding

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

        val pref = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)

        val settings = getDataFromSharedPrefs(pref)

        if(settings?.name.isNullOrEmpty())
            binding.nameET.setText("")
        else
            binding.nameET.setText(settings?.name)

        if(!settings?.level.isNullOrEmpty())
            when(settings?.level) {
                "1" -> binding.level1.isChecked = true
                "2" -> binding.level2.isChecked = true
                "3" -> binding.level3.isChecked = true
            }

        if(settings?.minute != null && settings.minute == true)
            binding.minuteSwitch.isChecked = true

        if(settings?.km != null && settings.km == true)
            binding.kmSwitch.isChecked = true

        if(settings?.run != null && settings.run == true)
            binding.runSwitch.isChecked = true

        if(!settings?.typeOfSport.isNullOrBlank())
            when(settings?.typeOfSport) {
                "1" -> binding.sport1.isChecked = true
                "2" -> binding.sport2.isChecked = true
                "3" -> binding.sport3.isChecked = true
            }

        binding.saveBtn.setOnClickListener {
            val level = if(binding.level1.isChecked) "1" else if(binding.level2.isChecked) "2" else "3"
            val typeOfSport = if(binding.sport1.isChecked) "1" else if(binding.sport2.isChecked) "2" else "3"

            setDataToSharedPrefs(pref, SettingsItem(
                name = binding.nameET.text.toString(),
                level = level,
                minute = binding.minuteSwitch.isChecked,
                km = binding.kmSwitch.isChecked,
                run = binding.runSwitch.isChecked,
                typeOfSport = typeOfSport
            ))
        }
    }

    private fun getDataFromSharedPrefs(pref: SharedPreferences) : SettingsItem? {
        val json = pref.getString("settings", "").orEmpty()
        val type = object : TypeToken<SettingsItem?>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun setDataToSharedPrefs(pref: SharedPreferences, data: SettingsItem) {
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString("settings", Gson().toJson(data)).apply()
    }

}
