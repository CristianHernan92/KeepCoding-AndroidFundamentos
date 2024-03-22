package com.example.resumenpracticadeloexplicadoenlasclases

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

//la clase debe heredar de ViewModel
class MainActivityViewModel: ViewModel() {

    //cuando instanciemos esta clase como hereda de view model no se destruirá cuando se gire el celular
    //asique todos los datos se mantendran con el valor que le pongamos acá porque se instanciará con dichos valores y no se perderan
    //ya que la clase no se destruye por más que giremos el celular

    //*(acá en los viewsmodels no se tienen que usar contextos, sino directamente agarrar los valores por medio del "id" del elemento)

    val numAleatorio = Random.nextInt()

    //STATE FLOW
    //creamos un mutable stato flow de tipo entero (se da cuenta que es entero porque le ponemos un número como valor, si sería un string sería de tipo string)
    //sera privado porque no lo usaremos directamente, sino que usaremos la variable que está debajo
    private val _uiState = MutableStateFlow(0)

    val uiState : StateFlow<Int> = _uiState

    //función que suma uno al valor del _uiState
    fun sumarUnoAl_UIState(){
        _uiState.value += 1
    }
}