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
import androidx.databinding.DataBindingUtil
import com.example.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var diceImage: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    private var numberShown: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.activityToolbar)

        diceImage = binding.diceImage

        numberShown = savedInstanceState?.getInt("value")?: 0

        if (numberShown != 0) {
            imageResourceSetter(numberShown)
        }

        val rollButton: Button = binding.rollButton
        rollButton.setOnClickListener {
            diceRoller()
            imageResourceSetter(numberShown)
        }

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actionbar, menu)

        menu.findItem(sharedPreferences.getInt("clickedItem", R.id.follow_system)).isChecked = true
        when (sharedPreferences.getInt("clickedItem", R.id.follow_system)) {
            R.id.follow_system -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            R.id.light_mode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.id.dark_mode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        return true
    }


    fun sharedPreferencesSaver(item: MenuItem) {
        val editor = sharedPreferences.edit()
        editor.putInt("clickedItem", item.itemId)
        editor.apply()
        recreate()
    }

    private fun diceRoller() {
        numberShown = (1..6).random()
    }

    private fun imageResourceSetter(numberShown: Int) {
        when (numberShown) {
            1 -> diceImage.setImageResource(R.drawable.dice_1)
            2 -> diceImage.setImageResource(R.drawable.dice_2)
            3 -> diceImage.setImageResource(R.drawable.dice_3)
            4 -> diceImage.setImageResource(R.drawable.dice_4)
            5 -> diceImage.setImageResource(R.drawable.dice_5)
            6 -> diceImage.setImageResource(R.drawable.dice_6)
            0 -> diceImage.setImageResource(R.drawable.empty_dice)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("value", numberShown)
    }
}