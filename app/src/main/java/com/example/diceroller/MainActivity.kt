package com.example.diceroller

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.diceroller.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var diceImage: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    private var diceFaceNumber: Int = 0
    private var selectedThemeOption by Delegates.notNull<Int>()
    private lateinit var changeThemeDialog: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.activityToolbar)

        diceImage = binding.diceImage
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        diceFaceNumber = savedInstanceState?.getInt("value") ?: 0

        selectedThemeOption = sharedPreferences.getInt("selected_theme", 0)
        themeChanger(selectedThemeOption)

        changeThemeDialog = MaterialAlertDialogBuilder(this)
        changeThemeDialog.setTitle(R.string.change_theme)

        if (diceFaceNumber != 0) {
            imageResourceSetter(diceFaceNumber)
        }

        val rollButton: Button = binding.rollButton
        rollButton.setOnClickListener {
            diceRoller()
            imageResourceSetter(diceFaceNumber)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_theme -> {
                changeThemeDialog.setSingleChoiceItems(
                    arrayOf("Follow System", "Light Mode", "Dark Mode"),
                    sharedPreferences.getInt("selected_theme", 0)
                ) { dialogInterface, selectedOption ->
                    selectedThemeInfoSaver(selectedOption)
                    themeChanger(selectedOption)
                    dialogInterface.dismiss()
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectedThemeInfoSaver(selectedTheme: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("selected_theme", selectedTheme)
        editor.apply()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("value", diceFaceNumber)
    }

    private fun themeChanger(selectedTheme: Int) {
        when (selectedTheme) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun diceRoller() {
        diceFaceNumber = (1..6).random()
    }

    private fun imageResourceSetter(numberShown: Int) {
        when (numberShown) {
            1 -> diceImage.setImageResource(R.drawable.dice_1)
            2 -> diceImage.setImageResource(R.drawable.dice_2)
            3 -> diceImage.setImageResource(R.drawable.dice_3)
            4 -> diceImage.setImageResource(R.drawable.dice_4)
            5 -> diceImage.setImageResource(R.drawable.dice_5)
            6 -> diceImage.setImageResource(R.drawable.dice_6)
            else -> diceImage.setImageResource(R.drawable.empty_dice)
        }
    }
}
