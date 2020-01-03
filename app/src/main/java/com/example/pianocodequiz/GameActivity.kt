package com.example.pianocodequiz

import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pianocodequiz.R.id
import com.example.pianocodequiz.R.id.*
import kotlinx.android.synthetic.main.white_key.*


class GameActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_game)
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
        val btnQuit = findViewById<Button>(btn_quit)
        btnQuit.setOnClickListener(object: OnClickListener{
            override fun onClick(v: View?){
                val intent = Intent(this@GameActivity, MainActivity::class.java)
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
        var sounds = mutableMapOf(
            id.btn_C to soundC,
            btn_Csharp to soundC_sharp, id.btn_D to soundD, btn_Dsharp to soundD_sharp,
            id.btn_E to soundE, id.btn_F to soundF, btn_Fsharp to soundF_sharp, id.btn_G to soundG,
            btn_Gsharp to soundG_sharp, id.btn_A to soundA, btn_Asharp to soundA_sharp,
            id.btn_B to soundB)
        //押されたか判断する変数
        var press_record = mutableMapOf(id.btn_C to false,
            btn_Csharp to false, id.btn_D to false, btn_Dsharp to false, id.btn_E to false,
            id.btn_F to false, btn_Fsharp to false, id.btn_G to false, btn_Gsharp to false,
            id.btn_A to false, btn_Asharp to false, id.btn_B to false,
            id.btn_C to false,
            btn_C_higher to false,btn_Csharp_higher to false, btn_D_higher to false,
            btn_Dsharp_higher to false, btn_E_higher to false, btn_F_higher to false,
            btn_Fsharp_higher to false, btn_G_higher to false, btn_Gsharp_higher to false,
            btn_A_higher to false, btn_Asharp_higher to false, btn_B_higher to false)
        val pianoOperator = object: OnClickListener{
            override fun onClick(v: View){
                //play sound and change the color of key
                soundPool.play(sounds[v.id]!!, 1.0f, 1.0f, 0, 0, 1.0f)
                if (press_record[v.id]!!) {
                    //黒鍵のばあい赤->黒
                    if (v.id in listOf(
                            btn_Csharp, btn_Dsharp, btn_Fsharp, btn_Gsharp,
                        btn_Asharp,
                            btn_Csharp_higher, btn_Dsharp_higher, btn_Fsharp_higher,
                            btn_Gsharp_higher, btn_Asharp_higher
                        )){
                        findViewById<Button>(v.id).setBackgroundColor(Color.parseColor("#000000"))
                    }else{ //白鍵の場合、赤->白
                        findViewById<Button>(v.id).setBackgroundColor(Color.parseColor("#FFFEEC"))
                    }
                    press_record[v.id] = false
                } else {
                    findViewById<Button>(v.id).setBackgroundColor(Color.parseColor("#ff0000"))
                    press_record[v.id] = true
                }
            }
        }
        //各鍵盤にsetOnClickListenerを設定
        //normalオクターブ
        var normal_octave = findViewById<FrameLayout>(normal)
        normal_octave.findViewById<Button>(id.btn_C).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(btn_Csharp).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(id.btn_D).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(btn_Dsharp).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(id.btn_E).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(id.btn_F).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(btn_Fsharp).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(id.btn_G).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(btn_Gsharp).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(id.btn_A).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(btn_Asharp).setOnClickListener(pianoOperator)
        normal_octave.findViewById<Button>(id.btn_B).setOnClickListener(pianoOperator)
        //一個上のオクターブの鍵盤
        var octave_higher = findViewById<FrameLayout>(octave_higher)
        octave_higher.findViewById<Button>(btn_C_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_Csharp_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_D_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_Dsharp_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_E_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_F_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_Fsharp_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_G_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_Gsharp_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_A_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_Asharp_higher).setOnClickListener(pianoOperator)
        octave_higher.findViewById<Button>(btn_B_higher).setOnClickListener(pianoOperator)



        // 問題のコード名をランダムに表示
        val textView = findViewById<TextView>(textView)
        val questions = arrayOf("C","D","E","F","G","A","B")
        var answers = mutableMapOf(
            "C" to arrayOf(arrayOf(id.btn_C,id.btn_C_higher),
                            arrayOf(id.btn_E,id.btn_E_higher),
                            arrayOf(id.btn_G,id.btn_G_higher)),
            "D" to arrayOf(arrayOf(id.btn_D,id.btn_D_higher),
                            arrayOf(id.btn_Fsharp,btn_Fsharp_higher),
                            arrayOf(id.btn_A,btn_A_higher)),
            "E" to arrayOf(arrayOf(id.btn_E,btn_E_higher),
                            arrayOf(btn_Gsharp,btn_Gsharp_higher),
                            arrayOf(id.btn_B,btn_B_higher)),
            "F" to arrayOf(arrayOf(id.btn_F,btn_F_higher),
                            arrayOf(id.btn_A,btn_A_higher),
                            arrayOf(id.btn_C,btn_C_higher)),
            "G" to arrayOf(arrayOf(id.btn_G,btn_G_higher),
                            arrayOf(id.btn_B,btn_B_higher),
                            arrayOf(id.btn_D,btn_D_higher)),
            "A" to arrayOf(arrayOf(id.btn_A,btn_A_higher),
                            arrayOf(btn_Csharp,btn_Csharp_higher),
                            arrayOf(id.btn_E,btn_E_higher)),
            "B" to arrayOf(arrayOf(id.btn_B,btn_B_higher),
                            arrayOf(btn_Dsharp,btn_Dsharp_higher),
                            arrayOf(btn_Fsharp,btn_Fsharp_higher)))
        //結果画面を出す処理
//        fun showResult(){
//            var Intent = intent()
//        }
        //問題を出す＋答えの判断の処理
        var counter = 0
        var rightAnswerCount = 0
        var totalGuessCount = 0
        fun setNextQuestion(){
            //問題を呼ぶたびcounterに記録する
            counter += 1
            //ピアノの押した鍵盤を元に戻す
            for ((key, value) in press_record){
                if (value) {
                    press_record[key] = false//押されている鍵盤をfalseにする
                    if (key in listOf(
                            btn_Csharp, btn_Dsharp, btn_Fsharp, btn_Gsharp, btn_Asharp,
                            btn_Csharp_higher, btn_Dsharp_higher, btn_Fsharp_higher,
                            btn_Gsharp_higher, btn_Asharp_higher
                        )){//黒鍵なら赤->黒
                        findViewById<Button>(key).setBackgroundColor(Color.parseColor("#000000"))
                    }else{ //白鍵の場合、赤->白
                        findViewById<Button>(key).setBackgroundColor(Color.parseColor("#FFFEEC"))
                    }
                }
            }
            //問題をランダムに出題
            val question = questions[(questions.indices).random()]
            var answer = answers[question]
            textView.text = question
            //ANSWERボタンを押した時の処理
            val CheckAnswer = object: OnClickListener{
                override fun onClick(v: View){
                    totalGuessCount += 1
                    fun give_result(): Boolean {
                        //atleast one of the key in same_keys has to be pressed(ex C or C_higher)
                        //check 1)answer key is pressed + 2)wrong key is not pressed
                        //1)answer key is pressed
                        var result = true //result for 1) condition
                        for (answer_keys in answer!!) {
                            result = false
                            for (i in press_record) {
                                if (i.value && i.key in answer_keys) {
                                    result = true//key in answer_keys(ex;C or C_higher)is pressed
                                    break
                                }
                            }
                            Log.d("test","first check:"+result.toString())
                            if (result){//continue to check whether answer is correct
                                continue
                            }else{//wrong answer
                                return false
                            }
                        }
                        //check 2)wrong key is not pressed if 1) is passed succefully
                        if (result){
                            Log.d("test","reached")
                            for (i in press_record){
                                var wrong_count = 0
                                for (answer_keys in answer!!) {
                                    if (i.key !in answer_keys && i.value){
                                        wrong_count += 1//key not in answer_keys is pressed
                                        break
                                    }
                                }
                                if (wrong_count == answer.count()) {//key that is not in partt of answer_keys is pressed
                                    return false
                                }else{
                                    continue
                                }
                            }
                        }
                        return true //both condition 1) and 2) was passed
                    }
                    val result:Boolean = give_result()
                    //checking finish------
                    var result_textview = findViewById<TextView>(Result)
                    if (result){
                        rightAnswerCount += 1
                        result_textview.text = "CORRECT"
                        result_textview.setTextColor(Color.RED)
                        if (counter < 5){
                            setNextQuestion()
                        }else{
//                           show result
                            Log.d("test","5 questions are done")
                            val intent = Intent(this@GameActivity, GameResultActivity::class.java)
                            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
                            intent.putExtra("TOTAL_GUESS_COUNT", totalGuessCount)
                            startActivity(intent)
                        }
                    }else{
                        result_textview.text = "WRONG"
                        result_textview.setTextColor(Color.BLUE)

                    }
                }
            }
            findViewById<Button>(answer_btn).setOnClickListener(CheckAnswer)
        }
        //初回の問題の設定
        setNextQuestion()







    }

}
