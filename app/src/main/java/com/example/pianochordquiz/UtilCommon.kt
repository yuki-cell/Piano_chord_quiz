package com.example.pianochordquiz

import android.app.Application
import android.util.Log
import com.example.pianochordquiz.R.id

/**
 * グローバル変数を扱うクラス
 * Created by sample on 2016/11/18.
 */
class UtilCommon : Application() {
    //variable for sound volume
    private var sound_vol = (0.5).toFloat()
      fun  getSoundVol() : Float {
            Log.d(TAG, "getGlobal")
            return sound_vol
        }
    fun setSoundVol(volume:Float) {
            Log.d(TAG, "setGlobal")
            sound_vol = volume
        }


    //variable for selecting chosen piano chord
    private var all = true
    private var major = false
    private var minor = false
    private var aug = false
    private var sus4 = false
    private var dim = false
    private var SelectedChord = mutableMapOf(id.all to arrayOf("all",all), id.major to arrayOf("",major),
        id.minor to arrayOf("m",minor),id.aug to arrayOf("aug",aug),
        id.sus4 to arrayOf("sus4",sus4), id.dim to arrayOf("dim",dim))
    fun getSelectedChord(): MutableMap<Int, Array<Any>> {
        Log.d(TAG, "getSelecteChord")
        return SelectedChord
    }
    fun setSelectedChord(chord: Int, isChecked:Boolean) {
        SelectedChord[chord]!![1] = isChecked
    }


    /**
     * アプリケーションの起動時に呼び出される
     */
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        sound_vol = (0.5).toFloat()
        all = true
        major = false
        minor = false
        aug = false
        sus4 = false
        dim = false
        SelectedChord = mutableMapOf(id.all to arrayOf("all",all), id.major to arrayOf("",major),
            id.minor to arrayOf("m",minor),id.aug to arrayOf("aug",aug),
            id.sus4 to arrayOf("sus4",sus4), id.dim to arrayOf("dim",dim))
    }

    /**
     * アプリケーション終了時に呼び出される
     */
    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "onTerminate")
        sound_vol = (0.5).toFloat()
        all = true
        major = false
        minor = false
        aug = false
        sus4 = false
        dim = false
        SelectedChord = mutableMapOf(id.all to arrayOf("all",all), id.major to arrayOf("",major),
            id.minor to arrayOf("m",minor),id.aug to arrayOf("aug",aug),
            id.sus4 to arrayOf("sus4",sus4), id.dim to arrayOf("dim",dim))

    }

    companion object {
        private val TAG = "UtilCommon"
    }








}