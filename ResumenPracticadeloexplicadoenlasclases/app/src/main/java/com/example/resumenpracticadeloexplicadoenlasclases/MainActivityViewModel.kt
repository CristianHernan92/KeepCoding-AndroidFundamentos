package com.example.resumenpracticadeloexplicadoenlasclases

import androidx.lifecycle.ViewModel
import kotlin.random.Random

//la clase debe heredar de ViewModel
class MainActivityViewModel: ViewModel() {

    //cuando instanciemos esta clase como hereda de view model no se destruirá cuando se gire el celular
    //asique todos los datos se mantendran con el valor que le pongamos acá porque se instanciará con dichos valores y no se perderan
    //ya que la clase no se destruye por más que giremos el celular

    val numAleatorio = Random.nextInt()
}