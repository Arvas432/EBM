package com.example.ebm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.ebm.R
import com.example.ebm.ui.search.activity.SearchActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val continueButton = findViewById<TextView>(R.id.continue_button)
        val loginField = findViewById<EditText>(R.id.login_et)
        val passwordField = findViewById<EditText>(R.id.password_et)
        continueButton.setOnClickListener {
            if(true)
            {
                startActivity(Intent(this, SearchActivity::class.java))
            }
        }
    }
}