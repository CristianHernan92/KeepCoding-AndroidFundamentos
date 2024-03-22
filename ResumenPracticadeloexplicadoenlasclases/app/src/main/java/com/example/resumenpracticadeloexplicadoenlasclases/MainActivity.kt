package com.example.resumenpracticadeloexplicadoenlasclases

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.resumenpracticadeloexplicadoenlasclases.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val VECES_ON_CREATE = "VECES_ON_CREATE"

    //VIEW MODELS
    //fijarse que no instanciamos el ViewModel, aquí estamos obteniendo una instancia del ViewModel MainActivityViewModel
    private val viewModel : MainActivityViewModel by viewModels()

    //ACCESO A LOS ELEMENTOS DE LA VISTA USANDO BINDING
    //como tipo para el binding nos aparecera para poner los nombres de las vistas xml que existen actualmentea (usaremos la que deseemos manipular con el binding) más la palabra "Binding"
    lateinit var binding : ActivityMainBinding

    //*cuando decimos Activity nos referiremos a la clase

    /*
    El método onCreate() en Android se ejecuta cuando se crea una actividad por primera vez. Esto ocurre cuando la actividad está siendo inicializada y se está construyendo la interfaz de usuario asociada a esa actividad.

    onCreate() es el primer método que se llama cuando se inicia una actividad. Aquí es donde se realizan las inicializaciones básicas necesarias para configurar la actividad, como la inflación del diseño de la interfaz de usuario utilizando setContentView(), la inicialización de variables, la configuración de la barra de acción (ActionBar), la asociación de escuchadores de eventos, entre otros.

    Es importante tener en cuenta que onCreate() no se llama solo cuando la actividad se inicia por primera vez, sino también cuando la actividad se recrea después de ser destruida debido a una rotación de la pantalla u otros cambios de configuración. En estos casos, la información de estado de la actividad puede ser recuperada a través del parámetro savedInstanceState proporcionado al método onCreate().

    En resumen, onCreate() es fundamental para la inicialización de una actividad y se llama tanto cuando se crea por primera vez como cuando se recrea después de ser destruida.
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //le damos valor al binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //agarramos el elemento con id "activityMainTextView" que pusimos en la vista "activity_main.xml"
        var textview = binding.activityMainTextView
        //utilizamos uno de los metodos del elemento que agarramos
        textview?.text = "Hola, soy el textview"
        textview?.setOnClickListener {
            textview?.text = "Se hizo click en el TextView"
        }

        //button para el State Flow
        var button = binding.activityMainButton
        button?.setOnClickListener {
            //llamamos a la función que le suma uno a la variable state flow
            viewModel.sumarUnoAl_UIState()
        }
        //aquí usams la corrutina lifecycleScope (hilo secundario), se usa para que lo que ponemos adentro desaparezca también y no quede en memoria cuando se borre el activity
        //adentro le pusimos un bloque de código que se ejecutará cada vez que se detecte que cambió el valor de la variable state flow (cuando se clickee en el botón de arriba se ejecutará, porque el botón hace que la variable state flow cambie de valor)
        lifecycleScope.launch {
            viewModel.uiState.collect{it
                binding.activityMainTextView?.text = it.toString()
            }
        }

        //LOGS
        Log.d("tag debug", "soy un tag de debug")
        Log.e("tag error", "soy un tag de error")
        Log.i("tag info", "soy un tag de info")
        Log.v("tag verbose", "soy un tag de verbose") //LOG VERBOSE
        Log.w("tag warning", "soy un tag de warning")

        Log.i("cristian tag onCreate","onCreate")

        //obtenemos el valor del sharedpreference
        val vecesOnCreate = cargarDePreferencias()
        Log.i("tag sharedpreferences", vecesOnCreate.toString())

        //guardamos el valor en el sharedpreference
        guardarEnSharedPreferences(vecesOnCreate + 2)




        Log.i("tag ViewModel", viewModel.numAleatorio.toString())

        //POPUP(TOAST)
        //le pasamos como contexto "this", luego el mensaje y luego la duración (que puede ser larga "LENGTH_LONG" o corta "LENGTH_SHORT")
        Toast.makeText(this, "Este es un popup toast",Toast.LENGTH_LONG).show()

        //ACCESO A LOS STRINGS
        //agarramos el texto string "toast_text" (se utiliza el contexto para agarrar el string, lo agaramos utilizando el contexto actual, es decir de la pripia clase MainActivity, sino necesitaríamos acceder de alguna manera al contexto tal)
        //"R" de resources
        val string = getString(R.string.toast_text)
    }

    /*
    El método onStart() en Android se ejecuta después de que onCreate() haya finalizado su ejecución o después de que una actividad haya sido reiniciada mediante onRestart(), y antes de que la actividad se haga visible al usuario.
    En resumen, onStart() se ejecuta en las siguientes situaciones:

    1)_Cuando la actividad se está iniciando por primera vez.
    2)_Cuando la actividad se está reiniciando después de estar detenida (por ejemplo, cuando se llama a onRestart()).
    3)_Cuando el usuario navega de regreso a la actividad después de que esta haya estado en pausa o detenida.

    En el método onStart(), puedes realizar acciones necesarias para preparar la interfaz de usuario antes de que sea visible al usuario. Por ejemplo, puedes cargar datos desde una base de datos, configurar escuchadores de eventos, realizar operaciones de inicialización que no requieran que la interfaz de usuario esté completamente visible, entre otros.
    Es importante tener en cuenta que onStart() se ejecuta antes de que la actividad sea visible para el usuario, por lo que debes evitar realizar tareas que puedan afectar el rendimiento o la experiencia del usuario durante este tiempo.
     */
    override fun onStart() {
        super.onStart()
        Log.i("cristian tag onStart","onStart")
    }

    /*
    El método onResume() en Android se ejecuta cuando la actividad está a punto de comenzar a interactuar con el usuario. Se llama justo antes de que la actividad se vuelva visible y esté completamente enfocada. En otras palabras, onResume() se ejecuta cuando la actividad está a punto de ingresar al primer plano y se volverá interactiva para el usuario.

    onResume() se llama en las siguientes situaciones:

    1)_Cuando la actividad se está iniciando por primera vez.
    2)_Cuando el usuario regresa a la actividad después de haber estado en segundo plano o después de que otra actividad que estaba encima de ella se haya cerrado.
    3)_Después de que se haya completado el método onStart().

    En el método onResume(), puedes realizar tareas que necesitan ejecutarse cada vez que la actividad se vuelve visible o interactuable para el usuario. Por ejemplo, puedes comenzar a reproducir medios, registrar receptores de transmisión o actualizar la interfaz de usuario para reflejar los cambios realizados en otras actividades.

    Es importante tener en cuenta que onResume() es el punto donde la actividad se considera activa y en primer plano, por lo que debes asegurarte de administrar correctamente los recursos y el estado de la aplicación en este punto.
     */
    override fun onResume() {
        super.onResume()
        Log.i("cristian tag onResume","onResume")
    }

    //EL ONPAUSE ES MUY IMPORTANTE, PORQUE ES EL CICLO DE VIDA RECURRENTE QUIZÄS Y EN DONDE SE AGREGARÏAN TODAS LAS COSAS A PAUSAR (como reproducciones de animaciones,etc.)

    /*
    El método onPause() en Android se ejecuta cuando la actividad está a punto de perder el foco y está en proceso de pasar a un estado de pausa. Se llama justo antes de que la actividad se vuelva parcialmente visible o completamente oculta al usuario.

    En resumen, onPause() se ejecuta en las siguientes situaciones:

    1)_Cuando otra actividad se está iniciando y está ocupando la pantalla completa o parcialmente, lo que hace que la actividad actual pierda el foco.
    2)_Cuando se muestra un cuadro de diálogo, una notificación o cualquier otra interfaz de usuario flotante que cubra parcial o completamente la actividad actual.
    3)_Cuando el usuario presiona el botón de inicio o cambia a otra aplicación, haciendo que la actividad actual pase a segundo plano.
    4)_Antes de que la actividad actual se detenga o se destruya, pero después de que se haya completado el método onStop().

    En el método onPause(), generalmente realizas tareas que deben detenerse o pausarse cuando la actividad ya no es completamente visible para el usuario, como detener animaciones, pausar reproducciones de medios, guardar datos no críticos, entre otras.

    Es importante tener en cuenta que onPause() se llama cada vez que la actividad está a punto de entrar en un estado de pausa, pero no se garantiza que la actividad se detenga o se destruya inmediatamente después de su ejecución. La actividad aún puede permanecer en memoria si el sistema decide mantenerla en caché o si el usuario vuelve a ella.
     */
    override fun onPause() {
        super.onPause()
        Log.i("cristian tag onPause","onPause")
    }


    /*
    El método onStop() en Android se ejecuta cuando una actividad está a punto de ser ocultada de la vista del usuario. Esto puede ocurrir cuando:

    1)_Otra actividad de la misma aplicación se está iniciando y está ocupando toda la pantalla.
    2)_La actividad actual está siendo cubierta parcial o completamente por otra ventana flotante, como un cuadro de diálogo o una notificación.
    3)_La actividad actual está siendo ocultada porque la aplicación está siendo minimizada (pasando a segundo plano) o porque el usuario está cambiando a otra aplicación.
    4)_La actividad actual está siendo cerrada por completo, pero antes de que se llame al método onDestroy().

    El método onStop() es un buen lugar para liberar recursos que no son críticos cuando la actividad ya no es visible para el usuario, como detener la reproducción de medios, liberar recursos de la cámara, desregistrar receptores de transmisión, etc.
    Es importante tener en cuenta que onStop() no garantiza que la actividad se destruirá inmediatamente después de su ejecución. La actividad aún puede permanecer en memoria si el sistema decide mantenerla en caché para una recuperación más rápida, o si el usuario vuelve a ella a través del botón de retroceso o de otras interacciones con la interfaz de usuario.
    */
    override fun onStop() {
        super.onStop()
        Log.i("cristian tag onStop","onStop")
    }

    /*
    El método onDestroy() en Android se ejecuta justo antes de que la actividad sea destruida y eliminada de la memoria del dispositivo. Este método se llama cuando la actividad está en proceso de finalización y está a punto de ser eliminada por completo.

    En resumen, onDestroy() se ejecuta en las siguientes situaciones:

    1)_Cuando la actividad está siendo cerrada explícitamente mediante una llamada al método finish().
    2)_Cuando el sistema Android decide destruir la actividad para liberar recursos debido a limitaciones de memoria u otras razones.
    3)_Cuando la actividad se recrea debido a cambios de configuración, como una rotación de la pantalla, y la instancia anterior de la actividad se está destruyendo para dar paso a una nueva instancia con la nueva configuración.

    En el método onDestroy(), generalmente realizas tareas de limpieza finales, como liberar recursos no críticos, desvincularse de servicios, cancelar tareas asíncronas, entre otras acciones. También es un buen lugar para guardar el estado persistente de la actividad si es necesario para que pueda ser restaurado en el futuro.

    Es importante tener en cuenta que onDestroy() es el último método que se llama antes de que la actividad sea eliminada de la memoria y ya no esté disponible para el usuario.
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.i("cristian tag onDestroy","onDestroy")
    }

    /*
    El método onRestart() en Android se ejecuta cuando una actividad que estaba detenida está a punto de ser reiniciada y volverá a comenzar a interactuar con el usuario. Se llama justo antes de que la actividad se muestre nuevamente después de haber estado en un estado detenido.

    onRestart() se ejecuta en las siguientes situaciones:

    1)_Cuando el usuario vuelve a la actividad después de haberla detenido, ya sea porque presionó el botón de retroceso desde la siguiente actividad, o porque la actividad anterior en la pila de actividades fue destruida y la actividad actual se recrea.
    2)_Cuando la actividad se está reiniciando después de haber estado en estado detenido y antes de que se vuelva a mostrar al usuario.
    3)_Es importante tener en cuenta que onRestart() se llama después de que la actividad haya estado en un estado detenido, pero antes de que vuelva a ser visible para el usuario. Puedes usar este método para realizar tareas de inicialización que solo deben realizarse cuando la actividad se reinicia, como volver a configurar los recursos necesarios para reanudar la actividad.

    En resumen, onRestart() es un paso intermedio entre el estado detenido y el estado visible de una actividad, y te brinda la oportunidad de realizar acciones específicas que deben llevarse a cabo durante este período de transición.
     */
    override fun onRestart() {
        super.onRestart()
        Log.i("cristian tag onRestart","onRestart")
    }

    //SHARED PREFERENCES

    //-se guardara las veces que se ejecuta el onCreate
    fun guardarEnSharedPreferences(vecesOnCreate: Int){
        //la clase en la que estamos hereda de AppCompatActivity(), entonces por eso podemos acceder a "getPreferences" porque estamos en un contexto. Si no estaríamos en una activity que hereda de AppCompatActivity() tendríamos que pasar dicho contexto de alguna manera para poder usar el getPreferences
        val preferences = getPreferences(Context.MODE_PRIVATE)

        val preferencesEditables = preferences.edit()
        //ahora el tipo de dato que guardaremos en el shared references
        //en este caso tenemos que pasarle el "nombre" para luego buscar el fichero por ese nombre y luego el valor
        preferencesEditables.putInt("VECES_ON_CREATE", vecesOnCreate)

        //guardamos el sharedreferences
        preferencesEditables.apply()
    }

    fun cargarDePreferencias() : Int{
        val preferences = getPreferences(Context.MODE_PRIVATE)

        //leemos la sharedpreference que guardamos
        //si no encuentra el sharedreference retoranrá 0
        val valorLeído = preferences.getInt(this.VECES_ON_CREATE,0)
        return valorLeído
    }

}