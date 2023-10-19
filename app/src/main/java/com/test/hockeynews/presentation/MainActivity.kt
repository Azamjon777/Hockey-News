package com.test.hockeynews.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.hockeynews.R
import com.test.hockeynews.databinding.ActivityMainBinding
import com.test.hockeynews.presentation.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.itemIconTintList = null

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        sharedViewModel.scoreGold.observe(this) { score ->
            binding.coinScore.text = score.toString()
            if (score > 50) {
                sharedViewModel.enableAllUsers()
            }
        }

        binding.settingsMain.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            val dialogView = inflater.inflate(R.layout.dialog_sound_settings, null)

            val seekBarSound = dialogView.findViewById<SeekBar>(R.id.seekBarSound)
            val btnOk = dialogView.findViewById<Button>(R.id.btnOk)

            alertDialogBuilder.setView(dialogView)
                .setTitle("Sound settings")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            btnOk.setOnClickListener {
                val soundLevel = seekBarSound.progress
                sharedViewModel.setSoundLevel(soundLevel)
                alertDialog.dismiss()
            }
        }
    }
}