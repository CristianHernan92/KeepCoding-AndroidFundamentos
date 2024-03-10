package com.example.resumenpracticadeloexplicadoenlasclases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //LOGS
        Log.d("tag debug", "soy un tag de debug")
        Log.e("tag error", "soy un tag de error")
        Log.i("tag info", "soy un tag de info")
        Log.v("tag verbose", "soy un tag de verbose") //LOG VERBOSE
        Log.w("tag warning", "soy un tag de warning")
    }
}