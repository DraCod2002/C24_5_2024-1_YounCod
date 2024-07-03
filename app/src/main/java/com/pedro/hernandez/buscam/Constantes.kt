package com.pedro.hernandez.buscam

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

object Constantes {
    const val anuncio_disponible = "Disponible"
    const val anuncio_vendido = "Vendido"

    val categorias = arrayOf(
        "Todos",
        "Móbiles",
        "Computo",
        "Electrónica",
        "Vehículoes",
        "Consolas",
        "Muebles",
        "Belleza",
        "Libros",
        "Deportes",
        "Juguetes",
        "Mascotas"
    )
    val categoriasIcono = arrayOf(
        R.drawable.ic_category,
        R.drawable.ic_categoria_mobiles,
        R.drawable.ic_categoria_ordenadores,
        R.drawable.ic_categoria_electrodomesticos,
        R.drawable.ic_categoria_vehiculos,
        R.drawable.ic_categoria_consolas,
        R.drawable.ic_categoria_muebles,
        R.drawable.ic_categoria_belleza,
        R.drawable.ic_categoria_libros,
        R.drawable.ic_categoria_sports,
        R.drawable.ic_categoria_juguetes,
        R.drawable.ic_categoria_mascotas


    )
    val condiciones = arrayOf(
        "Nuevo",
        "Usado",
        "Renovado"
    )
    fun ontenerTiempoDis() : Long{
        return System.currentTimeMillis()
    }
    fun obtenerFecha(tiempo : Long) : String{
        val calendario = Calendar.getInstance(Locale.ENGLISH)
        calendario.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy", calendario).toString()
    }
}