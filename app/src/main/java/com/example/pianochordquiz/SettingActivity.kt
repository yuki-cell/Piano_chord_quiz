package com.example.pianochordquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog


class SettingActivity : AppCompatActivity() {
    lateinit var soundPool: SoundPool
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        var common = application as UtilCommon

        //processing for user to choose which type of chords to be included(Check box)
        //initilializing check box
        var selectedChords = common.getSelectedChord()
        for (chord in selectedChords){
            findViewById<CheckBox>(chord.key).setChecked((chord.value)[1] as Boolean)
        }
        //processing for when the checkbox is clicked
        val chord_select_operator = object : View.OnClickListener {
            override fun onClick(v: View) {
                if (v is CheckBox) {
                    common.setSelectedChord(v.id, v.isChecked)
                }
            }
        }
        findViewById<CheckBox>(R.id.all).setOnClickListener(chord_select_operator)
        findViewById<CheckBox>(R.id.major).setOnClickListener(chord_select_operator)
        findViewById<CheckBox>(R.id.minor).setOnClickListener(chord_select_operator)
        findViewById<CheckBox>(R.id.aug).setOnClickListener(chord_select_operator)
        findViewById<CheckBox>(R.id.sus4).setOnClickListener(chord_select_operator)
        findViewById<CheckBox>(R.id.dim).setOnClickListener(chord_select_operator)


        //processing for user to control the volume of key played in the game
        //sound_vol is stored in application class so it can be used in other Activities as well
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
        var soundC = 0
        soundC = soundPool.load(this, R.raw.sound_do, 1)
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            Log.d("debug", "sampleId=$sampleId")
            Log.d("debug", "status=$status")
        }
        var volumeBar = findViewById<SeekBar>(R.id.volume);
        var sound_vol = common.getSoundVol()
        //seekbar initial value
        volumeBar.setProgress((sound_vol*100).toInt())
        // seekbar maximum value
        volumeBar.setMax(100)
        volumeBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                //change the volume when seekbar is dragged
                override fun onProgressChanged(
                    seekBar: SeekBar, progress: Int, fromUser: Boolean
                ) {
                    sound_vol = (progress.toFloat()/100)
                    Log.d("test",sound_vol.toString())
                }
                // called when user started dragging seekbar
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // called when user stop dragging seekbar+release sound to check the volume
                    soundPool.play(soundC, sound_vol!!.toFloat(), sound_vol!!.toFloat(), 0, 0, 1.0f)
                }
            }
        )


        //processing for return button
        var return_btn = findViewById<Button>(R.id.return_btn)
        return_btn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?){
                //checks if atleast one type of chord is chosen
                var pass = false
                for (chord in selectedChords) {
                    if (chord.value[1] as Boolean) {
                        pass = true
                    } else {
                        continue
                    }
                }
                if (pass){
                    val intent = Intent(this@SettingActivity, MainActivity::class.java)
                    common.setSoundVol(sound_vol)
                    startActivity(intent)
                }else{
                    AlertDialog.Builder(this@SettingActivity)
                        .setTitle("Error")
                        .setMessage("Please select atleast one type of chord")
                        .setPositiveButton("OK", { dialog, which ->
                            // TODO:Yesが押された時の挙動
                        })
                        .show()
                }

            }
        })

    }
}
