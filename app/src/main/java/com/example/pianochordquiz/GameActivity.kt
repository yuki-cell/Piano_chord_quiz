package com.example.pianochordquiz

import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pianochordquiz.R.id
import com.example.pianochordquiz.R.id.*


class GameActivity : AppCompatActivity() {
    //initializing variable for soundpool and sound for each keys(24 in total)
    lateinit var soundPool: SoundPool
    //normal-corresponds to C2 range
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
    //higher-corresponds to C3 range
    var soundC_higher = 0
    var soundC_sharp_higher = 0
    var soundD_higher = 0
    var soundD_sharp_higher = 0
    var soundE_higher = 0
    var soundF_higher = 0
    var soundF_sharp_higher = 0
    var soundG_higher = 0
    var soundG_sharp_higher = 0
    var soundA_higher = 0
    var soundA_sharp_higher = 0
    var soundB_higher = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        //common can access to variable in application class(shared to other activities)
        var common = application as UtilCommon
        //setting for sound pool(to play sound for each keys)
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
            // for number of streams
            .setMaxStreams(3)
            .build()

        //processing for quit button
        val btnQuit = findViewById<Button>(btn_quit)
        btnQuit.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@GameActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })

        // load the sound file for each keys
        //normal- C2 key range
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
        //higher -C3 key range
        soundC_higher = soundPool.load(this, R.raw.sound_do_higher, 1)
        soundC_sharp_higher = soundPool.load(this, R.raw.sound_do_sharp_higher, 1)
        soundD_higher = soundPool.load(this, R.raw.sound_re_higher, 1)
        soundD_sharp_higher = soundPool.load(this, R.raw.sound_re_sharp_higher, 1)
        soundE_higher = soundPool.load(this, R.raw.sound_mi_higher, 1)
        soundF_higher = soundPool.load(this, R.raw.sound_fa_higher, 1)
        soundF_sharp_higher = soundPool.load(this, R.raw.sound_fa_sharp_higher, 1)
        soundG_higher = soundPool.load(this, R.raw.sound_so_higher, 1)
        soundG_sharp_higher = soundPool.load(this, R.raw.sound_so_sharp_higher, 1)
        soundA_higher = soundPool.load(this, R.raw.sound_ra_higher, 1)
        soundA_sharp_higher = soundPool.load(this, R.raw.sound_ra_sharp_higher, 1)
        soundB_higher = soundPool.load(this, R.raw.sound_shi_higher, 1)
        // check whether each key is loaded successfully
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            Log.d("debug", "sampleId=$sampleId")
            Log.d("debug", "status=$status")
        }

        var sounds = mutableMapOf(
            id.btn_C to soundC,
            btn_Csharp to soundC_sharp, id.btn_D to soundD, btn_Dsharp to soundD_sharp,
            id.btn_E to soundE, id.btn_F to soundF, btn_Fsharp to soundF_sharp, id.btn_G to soundG,
            btn_Gsharp to soundG_sharp, id.btn_A to soundA, btn_Asharp to soundA_sharp,
            id.btn_B to soundB,

            id.btn_C_higher to soundC_higher,
            btn_Csharp_higher to soundC_sharp_higher, id.btn_D_higher to soundD_higher,
            btn_Dsharp_higher to soundD_sharp_higher,
            id.btn_E_higher to soundE_higher, id.btn_F_higher to soundF_higher,
            btn_Fsharp_higher to soundF_sharp_higher, id.btn_G_higher to soundG_higher,
            btn_Gsharp_higher to soundG_sharp_higher, id.btn_A_higher to soundA_higher,
            btn_Asharp_higher to soundA_sharp_higher,
            id.btn_B_higher to soundB_higher
        )

        //check whether each key is pressed or not
        var press_record = mutableMapOf(
            id.btn_C to false,
            btn_Csharp to false, id.btn_D to false, btn_Dsharp to false, id.btn_E to false,
            id.btn_F to false, btn_Fsharp to false, id.btn_G to false, btn_Gsharp to false,
            id.btn_A to false, btn_Asharp to false, id.btn_B to false,
            id.btn_C to false,
            btn_C_higher to false, btn_Csharp_higher to false, btn_D_higher to false,
            btn_Dsharp_higher to false, btn_E_higher to false, btn_F_higher to false,
            btn_Fsharp_higher to false, btn_G_higher to false, btn_Gsharp_higher to false,
            btn_A_higher to false, btn_Asharp_higher to false, btn_B_higher to false
        )

        //function for when piano key is pressed
        val pianoOperator = object : OnClickListener {
            override fun onClick(v: View) {
                //play sound and change the color of key
                var sound_vol = common.getSoundVol()
                Log.d("test", "sound "+sound_vol.toString())
                soundPool.play(sounds[v.id]!!, sound_vol!!.toFloat(), sound_vol!!.toFloat(), 0, 0, 1.0f)
                if (press_record[v.id]!!) {
                    //黒鍵のばあい赤->黒
                    if (v.id in listOf(
                            btn_Csharp, btn_Dsharp, btn_Fsharp, btn_Gsharp,
                            btn_Asharp,
                            btn_Csharp_higher, btn_Dsharp_higher, btn_Fsharp_higher,
                            btn_Gsharp_higher, btn_Asharp_higher
                        )
                    ) {
                        findViewById<Button>(v.id).setBackgroundColor(Color.parseColor("#000000"))
                    } else { //白鍵の場合、赤->白
                        findViewById<Button>(v.id).setBackgroundColor(Color.parseColor("#FFFEEC"))
                    }
                    press_record[v.id] = false
                } else {
                    findViewById<Button>(v.id).setBackgroundColor(Color.parseColor("#ff0000"))
                    press_record[v.id] = true
                }
            }
        }
        //set pianoOperator to each keys
        //normal-C2 octave
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
        //higher- C3 octave
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


        // store all the questions(chords)
        var base_keys = arrayOf("C","C#","D","D#","E","F","F#","G","G#","A","A#","B")
        //chord_names(major, minor, aug,sus4, dim)
        var chords = mutableMapOf("" to arrayOf(4,3),
            "m" to arrayOf(3,4),"aug" to arrayOf(4,4),"sus4" to arrayOf(5,2),"dim" to arrayOf(3,3))
        //list of piano keys used when checking the answer
        var keys_id = arrayOf(arrayOf(btn_C,btn_C_higher),
            arrayOf(btn_Csharp,btn_Csharp_higher), arrayOf(btn_D,btn_D_higher),
            arrayOf(btn_Dsharp,btn_Dsharp_higher),arrayOf(btn_E,btn_E_higher),
            arrayOf(btn_F,btn_F_higher),arrayOf(btn_Fsharp,btn_Fsharp_higher),
            arrayOf(btn_G,btn_G_higher),arrayOf(btn_Gsharp,btn_Gsharp_higher),
            arrayOf(btn_A,btn_A_higher),arrayOf(btn_Asharp,btn_Asharp_higher),
            arrayOf(btn_B,btn_B_higher),
            arrayOf(btn_C,btn_C_higher),
            arrayOf(btn_Csharp,btn_Csharp_higher), arrayOf(btn_D,btn_D_higher),
            arrayOf(btn_Dsharp,btn_Dsharp_higher),arrayOf(btn_E,btn_E_higher),
            arrayOf(btn_F,btn_F_higher),arrayOf(btn_Fsharp,btn_Fsharp_higher),
            arrayOf(btn_G,btn_G_higher),arrayOf(btn_Gsharp,btn_Gsharp_higher),
            arrayOf(btn_A,btn_A_higher),arrayOf(btn_Asharp,btn_Asharp_higher),
            arrayOf(btn_B,btn_B_higher)
        )

        val textView = findViewById<TextView>(textView)
        var counter = 0
        var rightAnswerCount = 0
        var totalGuessCount = 0
        var answer:ArrayList<Array<Int>>? = arrayListOf()
        var result_textview = findViewById<TextView>(Message)

        //unpress all the piano keys that have been pressed
        fun clear_keys() {
            for ((key, value) in press_record) {
                if (value) {
                    press_record[key] = false//押されている鍵盤をfalseにする
                    if (key in listOf(
                            btn_Csharp, btn_Dsharp, btn_Fsharp, btn_Gsharp, btn_Asharp,
                            btn_Csharp_higher, btn_Dsharp_higher, btn_Fsharp_higher,
                            btn_Gsharp_higher, btn_Asharp_higher
                        )
                    ) {//黒鍵なら赤->黒
                        findViewById<Button>(key).setBackgroundColor(Color.parseColor("#000000"))
                    } else { //白鍵の場合、赤->白
                        findViewById<Button>(key).setBackgroundColor(Color.parseColor("#FFFEEC"))
                    }
                }
            }
        }

        //function for showing the result of taking quiz
        fun show_result(rightAnswerCount:Int, totalGuessCount:Int){
            Log.d("test", "5 questions are done")
            val intent = Intent(this@GameActivity, GameResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            intent.putExtra("TOTAL_GUESS_COUNT", totalGuessCount)
            startActivity(intent)
        }

        //processing for giving question randomly
        var first_question:Boolean = true
        var chosen_chords: ArrayList<Any> = arrayListOf()
        fun get_question(): kotlin.collections.List<Any> {
            var index = (base_keys.indices).random()
            var base_key = base_keys[index]
            //for first question, indicate the type of chord type that user chose
            if (first_question) {
                var selected_chords = common.getSelectedChord().values
                for (selected_chord in selected_chords!!) {
                    if (selected_chord[1] as Boolean) {
                        chosen_chords.add(selected_chord[0])
                    }
                }
            }
            //firstime_finishes-----
            var chord:String
            if ("all" in chosen_chords) {
                chord = (chords.keys).random()
            } else {
                chord = chosen_chords.random() as String
            }
            var question = base_key + chord
            first_question = false
            return listOf(index, chord, question)
        }

        //function for getting answer based on the random question chosen
        fun get_answer(index:Any, chord:Any, answer: ArrayList<Array<Int>>?): ArrayList<Array<Int>>? {
            var key_distances = chords[chord]
            var i = index
            answer!!.add(keys_id[i as Int])
            for (key_distance in key_distances!!) {
                i = i as Int + key_distance
                answer!!.add(keys_id[i as Int])
            }
            return answer
        }

        //function for setting next question
        fun setNextQuestion() {
            result_textview.text = "Press right keys for chord"
            result_textview.setTextColor(Color.BLACK)
            answer = arrayListOf()
            //record the number of time question is given
            counter += 1
            //restore the keys in original condition
            clear_keys()
            //process for choosing question randomly and getting responding answer
            var (index:Any, chord, question) = get_question()
            Log.d("test", "index ${index}, chord ${chord}, question ${question}}")
            answer = get_answer(index, chord, answer)
            Log.d("test", "answer ${answer}")
            textView.text = question.toString()
        }

        //variable that stores true if user guessed the correct answer
        var isCorrect:Boolean? = null
        //process for when answer button is pressed
        // (indicate whether user guessed the right/show correct or wrong on screen/ and
        // decide next operation depedening on number of question tried)
        val CheckAnswer = object : OnClickListener {
            override fun onClick(v: View) {
                totalGuessCount += 1
                //atleast one of the key in same_keys has to be pressed(ex C or C_higher)
                //check 1)answer key is pressed + 2)wrong key is not pressed
                //1)answer key is pressed
                var result = true //result for 1) condition
                isCorrect = null // result for both condition
                for (answer_keys in answer!!) {
                    result = false
                    for (i in press_record) {
                        if (i.value && i.key in answer_keys) {
                            result = true//key in answer_keys(ex;C or C_higher)is pressed
                            break
                        }
                    }
                    Log.d("test", "first check:" + result.toString())
                    if (result) {//continue to check whether answer is correct
                        continue
                    } else {//wrong answer
                        result = false
                        isCorrect = false
                        break
                    }
                }
                //check 2)wrong key is not pressed if 1) is passed succefully
                if (result) {
                    Log.d("test", "reached")
                    for (i in press_record) {
                        var wrong_count = 0
                        for (answer_keys in answer!!) {
                            if (i.key !in answer_keys && i.value) {
                                wrong_count += 1//key not in answer_keys is pressed
                                break
                            }
                        }
                        if (wrong_count == answer!!.count()) {//key that is not in partt of answer_keys is pressed
                            isCorrect = false
                            break
                        } else {
                            continue
                        }
                    }
                }
                //is Correct will remain null if both condition 1) and 2) was passed
                if (isCorrect == null){
                    isCorrect = true
                }
                if (isCorrect as Boolean) {
                    rightAnswerCount += 1
                    result_textview.text = "CORRECT"
                    result_textview.setTextColor(Color.RED)
                    if (counter < 10) {
                        Thread.sleep(500)
                        Handler().postDelayed({ setNextQuestion() }, 2000)
                    } else {
                        show_result(rightAnswerCount, totalGuessCount)

                    }
                } else {
                    result_textview.text = "WRONG"
                    result_textview.setTextColor(Color.BLUE)

                }
            }
        }
        findViewById<Button>(answer_btn).setOnClickListener(CheckAnswer)

        //calling the first question
        setNextQuestion()


        //processing for clear button
        var clear_btn = findViewById<Button>(clear)
        clear_btn.setOnClickListener { clear_keys() }

        //processing for giveup button
        var first_time = true
        var giveup_btn = findViewById<Button>(give_up)
        giveup_btn.setOnClickListener {
            if (first_time) {
                totalGuessCount += 1
                result_textview.text = "Keys included in this chord is..."
                clear_keys()
                for (right_keys in answer!!) {
                    var key = findViewById<Button>(right_keys[0])
                    findViewById<Button>(key.id).setBackgroundColor(Color.parseColor("#ff0000"))
                    press_record[key.id] = true
                    giveup_btn.text = "NEXT"
                    first_time = false
                }
            } else {
                if (counter < 5) {
                    first_time = true
                    giveup_btn.text = "GIVE UP"
                    setNextQuestion()
                }else{
                    show_result(rightAnswerCount, totalGuessCount)
                }
            }
        }

    }



}


