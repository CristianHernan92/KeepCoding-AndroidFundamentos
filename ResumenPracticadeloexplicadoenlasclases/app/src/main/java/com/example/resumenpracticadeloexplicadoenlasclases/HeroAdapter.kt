package com.example.resumenpracticadeloexplicadoenlasclases

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.resumenpracticadeloexplicadoenlasclases.databinding.CeldaBinding

//el adapter utiliza RecyclerView, el recyclerview está echo de una manera que hace que sea performante la aplicación, busca eso por lo visto, es decir por ejemplo las celdas de la lista que el usuario no va a ver no las crea, sino que va utilizando las que el usuario va dejando atrás y se dejan de ver cuando hace scrooll, esto hace que por ejemplo si pintamos las celdas impares de un color diferente a las pares se veran bien las que ya está viendo el usuario, pero cuando empieza a hacer scroll, como se van reusando las celdas, se pueden ver mal, hay que tener en cuenta que vovlerá a pintar la celda y pasará por el "onBindViewHolder"

//el "ADAPTER"
class HeroAdapter: RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {
    private var heroList: List<Hero> = emptyList()

    //esta clase vendría a ser el activity de la celda
    //-se usa como el tipo del adaptador del "RECYCLERVIEW" que necesita el ADAPTER
    //-como diseño xml de dicha vista es el "celda.xml" (por eso ponemos CeldaBinding en el parámetro binding)
    class HeroViewHolder(private val binding: CeldaBinding) : RecyclerView.ViewHolder(binding.root){
        fun mostrarPosición(position: Int){
            binding.id.text = position.toString()
        }

        fun mostrarHeroe(hero: Hero){
            binding.nombre.text = hero.nombre

            //hacemos que al hacer click en la celda se muestre el popup toast con el nombre del heroe
            binding.root.setOnClickListener {
                Toast.makeText(this.binding.root.context,hero.nombre,Toast.LENGTH_LONG).show()
            }
        }
    }

    //le decimos cuantos elementos tiene mi lista
    override fun getItemCount(): Int {
        return this.heroList.count()
    }

    //este sería el "onCreate" del activity
    //acá haríamos lo que hacemos típicamente en el "onCreate" de las activities
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        //retornamos una vista de la celda instanciada
        //hacemos lo de instanciar el binding con la vista para acceder a los elementos de la vista por medio del binding
        return HeroViewHolder(
            CeldaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //le decimos lo que tiene que hacer cuando lo vaya a pintar a la vista
    //el parámetro "holder" representa a la vista celda y el position la posición de la celda
    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        //ejecutamos la función que creamos en el activity que hace escribir la posición de la celda en el diseño de la vista
        holder.mostrarPosición(position)
        holder.mostrarHeroe(this.heroList[position])
    }

    //esta función la usamos para actualizar la lista (antes de que la aplicación se esté ejecutando y mostrando al usuario)
    fun actualizarLista(heroList: List<Hero>){
        this.heroList = heroList
    }

    //esta función la usamos para actualizar la lista (ya estando la aplicación mostrandose al usuario)
    fun actualizarListaDesdeBotón(heroList: List<Hero>){
        this.heroList = heroList
        //como actualizamos la lista con la aplicación ya abierta en ejecución le tenemos que decir al adapter que se tiene que actualizar porque ya no se actualiza automáticamente como antes, sino que no sabe que se tiene que actualizar
        notifyDataSetChanged()
    }
}