package com.pedro.hernandez.buscam.chat

import android.app.Dialog
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pedro.hernandez.buscam.Constantes
import com.pedro.hernandez.buscam.R
import com.pedro.hernandez.buscam.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private  lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private  var uidVendedor = "" /*Uid del record */
    private var miUid = "" //Uid del emisor
    private var chatRuta = ""
    private var imagenUri : Uri?= null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_chat)

        firebaseAuth =  FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por fabor")
        progressDialog.setCanceledOnTouchOutside(false)

        uidVendedor = intent.getStringExtra("uidVendedor")!!
        miUid = firebaseAuth.uid!!

        chatRuta = Constantes.rutaChat(uidVendedor, miUid)
        cargarInfoVendedor()
    }

    private fun cargarInfoVendedor(){
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uidVendedor)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val nombres = "${snapshot.child("nombres").value}"
                        val imagen = "${snapshot.child("urlImagenPerfil").value}"
                        binding.TxtNombreVendedorChat.text = nombres
                        try {
                            Glide.with(this@ChatActivity)
                                .load(imagen)
                                .placeholder(R.drawable.perfil)
                                .into(binding.ToolbarIv)
                        }catch (e: Exception){

                        }
                    }catch (e:Exception){

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}