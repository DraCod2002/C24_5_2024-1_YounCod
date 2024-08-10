package com.pedro.hernandez.buscam

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.pedro.hernandez.buscam.Fragmentos.FragmentAnuncios
import com.pedro.hernandez.buscam.Fragmentos.FragmentChats
import com.pedro.hernandez.buscam.Fragmentos.FragmentInicio
import com.pedro.hernandez.buscam.Fragmentos.FragmentPerfil
import com.pedro.hernandez.buscam.anuncios.CrearAnuncio
import com.pedro.hernandez.buscam.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firebaseStorage: FirebaseStorage
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
        binding.FAB.setOnClickListener{
            val intent = Intent(this, CrearAnuncio::class.java)
            intent.putExtra("Edicion", false)
            startActivity(intent)
        }
    }
    private fun agregarFcmToken(){
        val miUid = "${firebaseAuth.uid}"

        FirebaseMessaging.getInstance().token
            .addOnSuccessListener {fcmToken->
                val hashMap = HashMap<String, Any>()
                hashMap["fcmToken"] = "$fcmToken"
                val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
                ref.child(miUid)
                    .updateChildren(hashMap)
                    .addOnSuccessListener {
                        //El token se agregó con éxito
                    }
                    .addOnFailureListener {e->
                        Toast.makeText(
                            this,
                            "${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            .addOnFailureListener {e->
                Toast.makeText(
                    this,
                    "${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

        private fun comprobarSesion(){
            if(firebaseAuth.currentUser == null){
                startActivity(Intent(this, OpcionesLogin::class.java))
                finishAffinity()
            }else{
                agregarFcmToken()
                solicitarPermisoNotificacion()
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

    private fun solicitarPermisoNotificacion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_DENIED){
                permisoNotificacion.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    private val permisoNotificacion =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ esConcedido->
            //Aqui se concede el permiso
        }
    private fun actualizarEstado(estado : String){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null){
            val ref = FirebaseDatabase.getInstance().reference.child("Usuarios").child(firebaseAuth.uid!!)
            val hashMap = HashMap<String, Any>()
            hashMap["estado"] = estado
            ref!!.updateChildren(hashMap)
        }

    }
    override fun onResume() {
        super.onResume()
        actualizarEstado("online")
    }

    override fun onPause() {
        super.onPause()
        actualizarEstado("offline")
    }
}