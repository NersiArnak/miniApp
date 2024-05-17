package org.fitmyss.asfe

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import org.fitmyss.asfe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)

        val navController = findNavController(R.id.fragment_contant_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.myResults -> {
                    navController.navigate(
                        R.id.ResultsFragment, null,
                        NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(R.id.ResultsFragment, true)
                            .build()
                    )
                    binding.toolbar.findViewById<TextView>(R.id.text_main).text = getString(R.string.text_name_results)
                }
                R.id.training -> {
                    navController.navigate(
                        R.id.TrainingsFragment, null,
                        NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(R.id.TrainingsFragment, true)
                            .build()
                    )
                    binding.toolbar.findViewById<TextView>(R.id.text_main).text = getString(R.string.text_name_trainigs)
                }
                R.id.marathon -> {
                    navController.navigate(
                        R.id.MarathonFragment, null,
                        NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(R.id.MarathonFragment, true)
                            .build()
                    )
                    binding.toolbar.findViewById<TextView>(R.id.text_main).text = getString(R.string.text_name_marathon)
                }
                R.id.library -> {
                    navController.navigate(
                        R.id.LibraryFragment, null,
                        NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(R.id.LibraryFragment, true)
                            .build()
                    )
                    binding.toolbar.findViewById<TextView>(R.id.text_main).text = getString(R.string.text_name_library)
                }
                R.id.settings -> {
                    navController.navigate(
                        R.id.SettingsFragment, null,
                        NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(R.id.SettingsFragment, true)
                            .build()
                    )
                    binding.toolbar.findViewById<TextView>(R.id.text_main).text = getString(R.string.text_name_library)
                }
            }
            true
        }
    }
}
