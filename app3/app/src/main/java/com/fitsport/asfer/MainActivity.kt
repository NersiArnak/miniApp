package com.fitsport.asfer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.fitsport.asfer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_green)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.main_green)

        val navController = findNavController(R.id.fragment_contant_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.results -> {
                    navController.navigate(
                        R.id.ResultsFragment, null,
                        NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(R.id.ResultsFragment, true)
                            .build()
                    )
                    binding.toolbar.findViewById<TextView>(R.id.text_main).text = getString(R.string.text_name_results)
                }
                R.id.nutrition -> {
                    navController.navigate(
                    R.id.NutritionFragment, null,
                    NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(R.id.NutritionFragment, true)
                        .build()
                    )
                    binding.toolbar.findViewById<TextView>(R.id.text_main).text = getString(R.string.text_name_nutrition)
                }
            }
            true
        }
    }
}
