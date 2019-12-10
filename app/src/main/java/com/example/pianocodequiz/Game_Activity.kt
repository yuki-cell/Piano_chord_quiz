package com.example.pianocodequiz

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.pianocodequiz.R.id
import kotlinx.android.synthetic.main.white_key.*
import kotlin.collections.mutableMapOf as mutableMapOf1


class Game_Activity : AppCompatActivity() {
    //sound poolとそれぞれのsoundの変数
    lateinit var soundPool:SoundPool
    var soundC = 0
    var soundC_sharp = 0
    var soundD = 0
    var soundD_sharp = 0
    var soundE = 0
    var soundF = 0
    var soundF_sharp = 0
    var soundG = 0
    var soundG_sharp = 0
    var soundA = 0
    var soundA_sharp = 0
    var soundB = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_)
        //音を出す処理
        val audioAttributes = AudioAttributes.Builder()
            // USAGE_MEDIA
            // USAGE_GAME
            .setUsage(AudioAttributes.USAGE_GAME)
            // CONTENT_TYPE_MUSIC
            // CONTENT_TYPE_SPEECH, etc.
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            // ストリーム数に応じて
            .setMaxStreams(3)
            .build()
        //画面遷移の処理
        val btnQuit = findViewById<Button>(id.btn_quit)
        btnQuit.setOnClickListener(object: OnClickListener{
            override fun onClick(v: View?){
                val intent = Intent(this@Game_Activity, MainActivity::class.java)
                startActivity(intent)
            }
        })
        // ピアノの音をロードしておく
        soundC = soundPool.load(this, R.raw.sound_do, 1)
        soundC_sharp = soundPool.load(this, R.raw.sound_do_sharp, 1)
        soundD = soundPool.load(this, R.raw.sound_re, 1)
        soundD_sharp = soundPool.load(this, R.raw.sound_re_sharp, 1)
        soundE = soundPool.load(this, R.raw.sound_mi, 1)
        soundF = soundPool.load(this, R.raw.sound_fa, 1)
        soundF_sharp = soundPool.load(this, R.raw.sound_fa_sharp, 1)
        soundG = soundPool.load(this, R.raw.sound_so, 1)
        soundG_sharp = soundPool.load(this, R.raw.sound_so_sharp, 1)
        soundA = soundPool.load(this, R.raw.sound_ra, 1)
        soundA_sharp = soundPool.load(this, R.raw.sound_ra_sharp, 1)
        soundB = soundPool.load(this, R.raw.sound_shi, 1)
        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener{ soundPool, sampleId, status ->
            Log.d("debug", "sampleId=$sampleId")
            Log.d("debug", "status=$status")
        }
        var sounds = kotlin.collections.mutableMapOf(id.btn_C to soundC,
            id.btn_Csharp to soundC_sharp, id.btn_D to soundD, id.btn_Dsharp to soundD_sharp,
            id.btn_E to soundE, id.btn_F to soundF, id.btn_Fsharp to soundF_sharp, id.btn_G to soundG,
            id.btn_Gsharp to soundG_sharp, id.btn_A to soundA, id.btn_Asharp to soundA_sharp,
            id.btn_B to soundB)
        //押されたか判断する変数
        var pressed = kotlin.collections.mutableMapOf(id.btn_C to false,
            id.btn_Csharp to false, id.btn_D to false, id.btn_Dsharp to false, id.btn_E to false,
            id.btn_F to false, id.btn_Fsharp to false, id.btn_G to false, id.btn_Gsharp to false,
            id.btn_A to false, id.btn_Asharp to false, id.btn_B to false)
        val pianoOperator = object: OnClickListener{
            override fun onClick(v: View){
                //play sound and change the color of key
                soundPool.play(sounds[v.id]!!, 1.0f, 1.0f, 0, 0, 1.0f)
                if (pressed[v.id]!!) {
                    //黒鍵のばあい赤->黒
                    if (v.id in listOf(id.btn_Csharp, id.btn_Dsharp, id.btn_Fsharp, id.btn_Gsharp,
                        id.btn_Asharp)){
                        findViewById<Button>(v.id).setBackgroundColor(Color.parseColor("#000000"))
                    }else{ //白鍵の場合、赤->白
                        findViewById<Button>(v.id).setBackgroundColor(Color.parseColor("#FFFEEC"))
                    }
                    pressed[v.id] = false
                } else {
                    findViewById<Button>(v.id).setBackgroundColor(Color.parseColor("#ff0000"))
                    pressed[v.id] = true
                }
            }
        }
        //各鍵盤にsetOnClickListenerを設定
        findViewById<Button>(id.btn_C).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_Csharp).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_D).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_Dsharp).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_E).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_F).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_Fsharp).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_G).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_Gsharp).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_A).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_Asharp).setOnClickListener(pianoOperator)
        findViewById<Button>(id.btn_B).setOnClickListener(pianoOperator)

        // 問題のコード名をランダムに表示
        val textView = findViewById<TextView>(id.textView)
        val questions = arrayOf("C","D","E","F","G","A","B")
        var answers = kotlin.collections.mutableMapOf(
            "C" to arrayOf(id.btn_C,id.btn_E,id.btn_G),
            "D" to arrayOf(id.btn_D,id.btn_Fsharp,id.btn_A),
            "E" to arrayOf(id.btn_E,id.btn_Gsharp,id.btn_B),
            "F" to arrayOf(id.btn_F,id.btn_A,id.btn_C),
            "G" to arrayOf(id.btn_G,id.btn_B,id.btn_D),
            "A" to arrayOf(id.btn_A,id.btn_Dsharp,id.btn_E),
            "B" to arrayOf(id.btn_B,id.btn_Dsharp,id.btn_Fsharp))
        //結果画面を出す処理
        fun showResult(){
            var Intent = intent()
        }
        //問題を出す＋答えの判断の処理
        var counter = 0
        fun setNextQuestion(){
            //問題を呼ぶたびcounterに記録する
            counter += 1
            //ピアノの押した鍵盤を元に戻す
            for ((key, value) in pressed){
                if (value) {
                    pressed[key] = false
                    if (key in listOf(id.btn_Csharp, id.btn_Dsharp, id.btn_Fsharp, id.btn_Gsharp,
                                id.btn_Asharp)){//黒鍵なら赤->黒
                        findViewById<Button>(key).setBackgroundColor(Color.parseColor("#000000"))
                    }else{ //白鍵の場合、赤->白
                        findViewById<Button>(key).setBackgroundColor(Color.parseColor("#FFFEEC"))
                    }
                }
            }
            //問題をランダムに出題
            val question = questions[(0..questions.size).random()]
            var answer = answers[question]
            textView.text = question
            //ANSWERボタンを押した時の処理
            val CheckAnswer = object: OnClickListener{
                override fun onClick(v: View){
                    var result = true
                    for (i in pressed){
                        if (i.key in answer!!){
                            if (!i.value){
                                result = false
                                break
                            }
                        }else{
                            if (i.value){
                                result = false
                                break
                            }
                        }
                    }
                    var result_textview = findViewById<TextView>(id.Result)
                    if (result){
                        result_textview.text = "CORRECT"
                        result_textview.setTextColor(Color.RED)
                        if (counter < 10){
                            setNextQuestion()
                        }else{
                            showResult()
                        }
                    }else{
                        result_textview.text = "WRONG"
                        result_textview.setTextColor(Color.BLUE)

                    }
                }
            }
            findViewById<Button>(id.answer_btn).setOnClickListener(CheckAnswer)
        }
        //初回の問題の設定
        setNextQuestion()







    }

}
