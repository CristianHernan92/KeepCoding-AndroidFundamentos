package com.example.resumenpracticadeloexplicadoenlasclases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.resumenpracticadeloexplicadoenlasclases.databinding.ActivityMain2Binding
import com.example.resumenpracticadeloexplicadoenlasclases.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {

    //agregamos el enlace con el diseño de la vista con el binding para obtener acceso a los elementos de la vista como ya vimos antes cuando explicamos el binding
    lateinit var binding : ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}