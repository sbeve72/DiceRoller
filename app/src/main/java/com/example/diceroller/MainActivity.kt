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
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var diceImage: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    private var diceFaceNumber: Int = 0
    private var clickedItemId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.activityToolbar)
        diceImage = binding.diceImage
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        diceFaceNumber = savedInstanceState?.getInt("value") ?: 0
        clickedItemId = sharedPreferences.getInt("clickedItemId", R.id.follow_system)

        themeChanger(clickedItemId)

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
        menu.findItem(clickedItemId).isChecked = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.follow_system || item.itemId == R.id.light_mode || item.itemId == R.id.dark_mode) {
            sharedPreferencesSaver(item)
            themeChanger(item.itemId)
            item.isChecked = true
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sharedPreferencesSaver(item: MenuItem) {
        val editor = sharedPreferences.edit()
        editor.putInt("clickedItemId", item.itemId)
        editor.apply()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("value", diceFaceNumber)
    }

    private fun themeChanger(menuItemId: Int) {
        when (menuItemId) {
            R.id.follow_system -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            R.id.light_mode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.id.dark_mode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
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
