package com.pedro.hernandez.buscam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.pedro.hernandez.buscam.Fragmentos.FragmentBuscar
import com.pedro.hernandez.buscam.Fragmentos.FragmentChats
import com.pedro.hernandez.buscam.Fragmentos.FragmentFavoritos
import com.pedro.hernandez.buscam.Fragmentos.FragmentInicio
import com.pedro.hernandez.buscam.Fragmentos.FragmentPerfil
import com.pedro.hernandez.buscam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        verFragmentInicio()

        binding.BottonNV.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.Item_Inicio->{
                    verFragmentInicio()
                    true
                }
                R.id.Item_Chats->{
                    verFragmentChats()
                   true
                }
                R.id.Item_Favoritos->{
                    verFragmentFavoritos()
                    true
                }
                R.id.Item_Buscar->{
                    verFragmentBuscar()
                    true
                }
                R.id.Item_Perfil->{
                    verFragmentPerfil()
                    true
                }
                else->{
                    false
                }
            }
        }
    }
    private fun comprobarSesion(){
        if (firebaseAuth.currentUser == null){
            startActivity(Intent(this,OpcionesLogin::class.java))
            finishAffinity()
        }
    }
    private fun verFragmentInicio(){
        binding.TituloRl.text = "INICIO"
        val fragment = FragmentInicio()
        val fragmentTrasition = supportFragmentManager.beginTransaction()
        fragmentTrasition.replace(binding.FragmentL1.id,fragment,"FragmentInicio")
        fragmentTrasition.commit()
    }
    private fun verFragmentBuscar(){
        binding.TituloRl.text = "BUSCAR"
        val fragment = FragmentBuscar()
        val fragmentTrasition = supportFragmentManager.beginTransaction()
        fragmentTrasition.replace(binding.FragmentL1.id,fragment,"FragmentBuscar")
        fragmentTrasition.commit()

    }
    private fun verFragmentChats(){
        binding.TituloRl.text = "CHATS"
        val fragment = FragmentChats()
        val fragmentTrasition = supportFragmentManager.beginTransaction()
        fragmentTrasition.replace(binding.FragmentL1.id,fragment,"FragmentChats")
        fragmentTrasition.commit()

    }
    private fun verFragmentFavoritos(){
        binding.TituloRl.text = "FAVORITOS"
        val fragment = FragmentFavoritos()
        val fragmentTrasition = supportFragmentManager.beginTransaction()
        fragmentTrasition.replace(binding.FragmentL1.id,fragment,"FragmentFavoritos")
        fragmentTrasition.commit()

    }
    private fun verFragmentPerfil(){
        binding.TituloRl.text = "PERFIL"
        val fragment = FragmentPerfil()
        val fragmentTrasition = supportFragmentManager.beginTransaction()
        fragmentTrasition.replace(binding.FragmentL1.id,fragment,"FragmentPerfil")
        fragmentTrasition.commit()

    }
}