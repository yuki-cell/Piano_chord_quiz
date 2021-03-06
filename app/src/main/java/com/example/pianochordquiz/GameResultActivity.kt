package com.example.pianochordquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class GameResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_result)
        //proecssing for menu and restart button
        val buttonOperator = object: View.OnClickListener {
            override fun onClick(v: View){
                when(v.id){
                    R.id.Menu ->{
                        val intent = Intent(this@GameResultActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.restart ->{
                        val intent = Intent(this@GameResultActivity, GameActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        var menuButton = findViewById<Button>(R.id.Menu)
        var restartButton = findViewById<Button>(R.id.restart)
        menuButton.setOnClickListener(buttonOperator)
        restartButton.setOnClickListener(buttonOperator)

        //processing for showing the score
        var rightAnswerCount = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0)
        var totalGuessCount = getIntent().getIntExtra("TOTAL_GUESS_COUNT", 0)
        var score_textview = findViewById<TextView>(R.id.score)
        Log.d("test", "right:"+rightAnswerCount.toString()+"guess:"+totalGuessCount.toString())
        if (totalGuessCount != 0){
            score_textview.text =
                ("%,.2f".format((rightAnswerCount.toDouble() / totalGuessCount.toDouble()) * 100)).toString() + "%"
        }else{
            score_textview.text = "0%"
        }

    }
}
