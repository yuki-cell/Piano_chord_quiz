package com.example.pianochordquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //processing for start and setting button
        val btnToGame = findViewById<Button>(R.id.start)
        btnToGame.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?){
                val intent = Intent(this@MainActivity, GameActivity::class.java)
                startActivity(intent)
            }
        })
        val btnToSetting = findViewById<Button>(R.id.setting)
        btnToSetting.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?){
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
            }
        })

    }
}
