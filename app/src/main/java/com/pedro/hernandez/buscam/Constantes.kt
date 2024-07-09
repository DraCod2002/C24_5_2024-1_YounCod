package com.pedro.hernandez.buscam

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateFormat
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
        "Vehículos",
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
    fun agregarAnuncioFav (context : Context, idAnuncio : String){
        val firebaseAuth = FirebaseAuth.getInstance()
        val tiempo = Constantes.ontenerTiempoDis()

        val hashMap = HashMap<String, Any>()
        hashMap["idAnuncio"] = idAnuncio
        hashMap["tiempo"] = tiempo

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!).child("Favoritos").child(idAnuncio)
            .setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(context,
                    "Anuncio agregado a Favoritos",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                Toast.makeText(context,
                    "${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }
    fun eliminarAnuncioFav (context: Context, idAnuncio: String){
        val firebaseAuth = FirebaseAuth.getInstance()

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!).child("Favoritos").child(idAnuncio)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context,
                    "Anuncio eliminado de Favoritos",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                Toast.makeText(context,
                    "${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }
    fun mapaIntent (context: Context, latitud : Double, longitud : Double){
        val googleMapIntentUri = Uri.parse("http://maps.google.com/maps?daddr=$latitud,$longitud")

        val mapIntent = Intent(Intent.ACTION_VIEW, googleMapIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager)!=null){
            //La app de google maps si está instalada
            context.startActivity(mapIntent)
        }else{
            //Si la app de google maps no está instalada
            Toast.makeText(context, "No tienes instalada la aplicación de Google Maps",
                Toast.LENGTH_SHORT).show()
        }

    }
    fun llamarIntent (context: Context, tef : String){
        val intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:$tef"))
        context.startActivity(intent)
    }

    fun smsIntent (context: Context, tel : String){
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("smsto:$tel"))
        intent.putExtra("sms_body", "")
        context.startActivity(intent)
    }
}