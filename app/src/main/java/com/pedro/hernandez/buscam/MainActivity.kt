package com.pedro.hernandez.buscam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.pedro.hernandez.buscam.Fragmentos.FragmentAnuncios
import com.pedro.hernandez.buscam.Fragmentos.FragmentChats
import com.pedro.hernandez.buscam.Fragmentos.FragmentInicio
import com.pedro.hernandez.buscam.Fragmentos.FragmentPerfil
import com.pedro.hernandez.buscam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        verFragmentInicio()
        comprobarSesion()
        binding.BottonNV.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Item_Inicio -> {
                    verFragmentInicio()
                    true
                }

                R.id.Item_Anuncios -> {
                    verFragmentAnuncios()
                    true
                }

                R.id.Item_Chats -> {
                    verFragmentChats()
                    true
                }

                R.id.Item_Perfil -> {
                    verFragmentPerfil()
                    true
                }

                else -> {
                    false
                }
            }

        }
    }

        private fun comprobarSesion(){
            if(firebaseAuth.currentUser == null){
                startActivity(Intent(this, OpcionesLogin::class.java))
                finishAffinity()
            }
        }
        private fun verFragmentInicio(){
            binding.TituloRl.text = "Inicio"
            val fragment = FragmentInicio()
            val fragmentTransition = supportFragmentManager.beginTransaction()
            fragmentTransition.replace(binding.FragmentL1.id, fragment, "FragmentInicio")
            fragmentTransition.commit()
        }
        private fun verFragmentAnuncios(){
            binding.TituloRl.text = "Anuncios"
            val fragment = FragmentAnuncios()
            val fragmentTransition = supportFragmentManager.beginTransaction()
            fragmentTransition.replace(binding.FragmentL1.id, fragment, "FragmentAnuncios")
            fragmentTransition.commit()
        }
        private fun verFragmentChats(){
            binding.TituloRl.text = "Chats"
            val fragment = FragmentChats()
            val fragmentTransition = supportFragmentManager.beginTransaction()
            fragmentTransition.replace(binding.FragmentL1.id, fragment, "FragmentChats")
            fragmentTransition.commit()
        }
        private fun verFragmentPerfil(){
            binding.TituloRl.text = "Perfil"
            val fragment = FragmentPerfil()
            val fragmentTransition = supportFragmentManager.beginTransaction()
            fragmentTransition.replace(binding.FragmentL1.id, fragment, "FragmentPerfil")
            fragmentTransition.commit()
        }

}