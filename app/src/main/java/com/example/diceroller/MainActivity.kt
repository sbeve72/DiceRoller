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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.activityToolbar)

        diceImage = binding.diceImage
        val rollButton: Button = binding.rollButton
        rollButton.setOnClickListener {
            rollDice()
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

    private fun rollDice() {
        when ((1..6).random()) {
            1 -> diceImage.setImageResource(R.drawable.dice_1)
            2 -> diceImage.setImageResource(R.drawable.dice_2)
            3 -> diceImage.setImageResource(R.drawable.dice_3)
            4 -> diceImage.setImageResource(R.drawable.dice_4)
            5 -> diceImage.setImageResource(R.drawable.dice_5)
            6 -> diceImage.setImageResource(R.drawable.dice_6)
        }
    }
}