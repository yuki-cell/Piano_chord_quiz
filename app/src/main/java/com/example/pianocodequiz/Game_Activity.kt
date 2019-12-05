package com.example.pianocodequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class Game_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_)
        //画面遷移の処理
        val btnQuit = findViewById<Button>(R.id.btn_quit)
        btnQuit.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?){
                val intent = Intent(this@Game_Activity, MainActivity::class.java)
                startActivity(intent)
            }
        })
        // 問題のコード名をランダムに表示
        val textView = findViewById<TextView>(R.id.textView)
        val questions = arrayOf("C","D","E","F","G","A","B")
        val question = questions[(0..questions.size).random()]
        textView.text = question



    }

}
