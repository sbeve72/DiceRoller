package com.example.diceroller

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    private var diceFaceNumber = 0
    private var selectedThemeOption by Delegates.notNull<Int>()
    private lateinit var alertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_toolbar)
        alertDialogBuilder = MaterialAlertDialogBuilder(this)
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        diceFaceNumber = savedInstanceState?.getInt("value") ?: 0
        selectedThemeOption = sharedPreferences.getInt("selected_theme", 0)

        themeChanger(selectedThemeOption)
        if (diceFaceNumber != 0) {
            imageResourceSetter(diceFaceNumber)
        }
        roll_button.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0) {
            roll_button -> {
                diceRoller()
                imageResourceSetter(diceFaceNumber)
            }
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
                alertDialogBuilder
                    .setTitle(R.string.change_theme)
                    .setSingleChoiceItems(
                        arrayOf("Follow System", "Light Mode", "Dark Mode"),
                        sharedPreferences.getInt("selected_theme", 0)
                    ) { dialogInterface, selectedOption ->
                        selectedThemeInfoSaver(selectedOption)
                        themeChanger(selectedOption)
                        dialogInterface.dismiss()
                    }
                    .create()
                    .show()
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
            1 -> dice_image.setImageResource(R.drawable.dice_1)
            2 -> dice_image.setImageResource(R.drawable.dice_2)
            3 -> dice_image.setImageResource(R.drawable.dice_3)
            4 -> dice_image.setImageResource(R.drawable.dice_4)
            5 -> dice_image.setImageResource(R.drawable.dice_5)
            6 -> dice_image.setImageResource(R.drawable.dice_6)
            else -> dice_image.setImageResource(R.drawable.empty_dice)
        }
    }
}
