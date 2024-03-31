package com.example.resumenpracticadeloexplicadoenlasclases

import android.sax.StartElementListener
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.math.log
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



    //INTERNET

    private var token : String = ""

    //creamos una variable de estado que sea de tipo State que hará referencia a los estados descritos dentro de la lcase State
    private val _uiState_internet = MutableStateFlow<State>(State.Idle())
    val uiState_internet : StateFlow<State> = _uiState_internet
    sealed class State{
        //clase para el cargando
        class Idle : State()
        //clase para el error
        class Error(val message: String) : State()
        //clase para el loading
        class Loading : State()
        class SuccesTestBasico (val bootcampList: String) : State()
        class SuccesLogin : State()
        class SuccesGetHeroes(val heroList : List<HeroDto>) : State()
    }

    //funciones que llamarán a la api y que modificarán el valor de la variable de estado "uiState_internet" dependiendo del resultado

    fun call_Api_bootcamp() {
        //como estamos en el "ViewModel" se utiliza el "viewModelScope" en vez del "lifecycleScope" que se usa en las activities
        //lo lanzamos al hilo secundario para que no moleste al hilo principal
        viewModelScope.launch(Dispatchers.IO) {
            //se comienza a hacer la llamada a la api
            val client = OkHttpClient()
            val url = "https://dragonball.keepcoding.education/api/data/bootcamps"
            val request = Request.Builder()
                .url(url)
                .build()
            val call = client.newCall(request)
            val response = call.execute()

            if (response.isSuccessful) {
                response.body?.let {
                    //si llega hasta aquí está todo bien, asique actualizamos el valor de la variable de estado _uiState_internet
                    _uiState_internet.value = State.SuccesTestBasico(it.string())
                } ?: State.Error("No hay dato")
            } else {
                _uiState_internet.value = State.Error("la respuesta no fué exitosa")
            }
        }
    }

    fun launchLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            //cambiamos el valor de la varaible de estado a "Loading" (el cual se podría utilizar para mostrar el circulito cargando)
            _uiState_internet.value = State.Loading()
            val client = OkHttpClient()
            val url = "https://dragonball.keepcoding.education/api/auth/login"
            val credentials = Credentials.basic("cristianhernandelrio@gmail.com", "KQFhbcuj9Hwgf39")
            val formBody = FormBody.Builder() // Esto hace que la request sea de tipo POST
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", credentials)
                .post(formBody)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            _uiState_internet.value =  if (response.isSuccessful)
                response.body?.let {
                    token = it.string()
                    State.SuccesLogin()
                } ?: State.Error("Empty Token")
            else
                State.Error(response.message)
        }
    }

    fun launchGetHeroes() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState_internet.value = State.Loading()
            val client = OkHttpClient()
            val url = "https://dragonball.keepcoding.education/api/heros/all"
            val formBody = FormBody.Builder() // Esto hace que la request sea de tipo POST
                .add("name", "")
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .post(formBody)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            _uiState_internet.value =  if (response.isSuccessful)
                response.body?.let {
                    //acá hacemos el parseo (con Gson) del modelo que nos viene de la api al modelo
                    val herosDtoArray : Array<HeroDto> = Gson().fromJson(it.string(), Array<HeroDto>::class.java)
                    //le pasamos el Array en forma de Lista al estado actualizado a SuccesGetHeroes por medio del parámetro
                    State.SuccesGetHeroes(herosDtoArray.toList())
                } ?: State.Error("Empty Token")
            else
                State.Error(response.message)
        }
    }
}