package com.example.resumenpracticadeloexplicadoenlasclases

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivityViewModelTest {

    //instanciamos el ViewModel
    val vm = MainActivityViewModel()
    @Test
    fun comprobarIntEnRango() {
        assertTrue(vm.numAleatorio > Int.MIN_VALUE)
        assertTrue(vm.numAleatorio < Int.MAX_VALUE)
    }
}