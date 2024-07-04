package com.pedro.hernandez.buscam.DetalleAnuncio

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pedro.hernandez.buscam.Adaptadores.AdaptadorImgSlider
import com.pedro.hernandez.buscam.Constantes
import com.pedro.hernandez.buscam.Model.ModeloAnuncio
import com.pedro.hernandez.buscam.Model.ModeloImgSlider
import com.pedro.hernandez.buscam.R
import com.pedro.hernandez.buscam.databinding.ActivityDetalleAnuncioBinding
import java.util.HashMap

class DetalleAnuncio : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleAnuncioBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var idAnuncio = ""
    private var anuncioLatitud = 0.0
    private var anuncioLongitud = 0.0

    private var uidVendedor = ""
    private var telVendedor = ""
    private var favorito = false
    private lateinit var imagenSliderArrayList : ArrayList<ModeloImgSlider>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleAnuncioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.IbEditar.visibility = View.GONE
        binding.IbEliminar.visibility = View.GONE
        binding.BtnMapa.visibility = View.GONE
        binding.BtnLlamar.visibility = View.GONE
        binding.BtnSms.visibility = View.GONE
        binding.BtnChat.visibility = View.GONE

        firebaseAuth = FirebaseAuth.getInstance()
        idAnuncio = intent.getStringExtra("idAnuncio").toString()
        cargarInfoAnuncio()
        cargarImgAnuncio()
        }
    private fun cargarInfoAnuncio(){
        var ref = FirebaseDatabase.getInstance().getReference("Anuncios")
        ref.child(idAnuncio)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val modeloAnuncio = snapshot.getValue(ModeloAnuncio::class.java)

                        uidVendedor = "${modeloAnuncio!!.uid}"
                        val titulo = modeloAnuncio.titulo
                        val descripcion = modeloAnuncio.descripcion
                        val direccion = modeloAnuncio.direccion
                        val condicion = modeloAnuncio.condicion
                        val categoria = modeloAnuncio.categoria
                        val precio = modeloAnuncio.precio
                        val estado = modeloAnuncio.estado
                        anuncioLatitud = modeloAnuncio.latitud
                        anuncioLongitud = modeloAnuncio.Longitud
                        val tiempo = modeloAnuncio.tiempo
                        val formatoFecha = Constantes.obtenerFecha(tiempo)

                        if (uidVendedor == firebaseAuth.uid){
                            //Si el usuario que ha realizado la publicación, visualiza
                            //la información del anuncio

                            //SI tendrá disponible
                            binding.IbEditar.visibility = View.VISIBLE
                            binding.IbEliminar.visibility = View.VISIBLE

                            //No tendrá disponible
                            binding.BtnMapa.visibility = View.GONE
                            binding.BtnLlamar.visibility = View.GONE
                            binding.BtnSms.visibility = View.GONE
                            binding.BtnChat.visibility = View.GONE

                            binding.TxtDescrVendedor.visibility = View.GONE
                            binding.perfilVendedor.visibility = View.GONE
                        }else{
                            //NO Tendrá disponible
                            binding.IbEditar.visibility = View.GONE
                            binding.IbEliminar.visibility = View.GONE

                            //SI tendrá disponible
                            binding.BtnMapa.visibility = View.VISIBLE
                            binding.BtnLlamar.visibility = View.VISIBLE
                            binding.BtnSms.visibility = View.VISIBLE
                            binding.BtnChat.visibility = View.VISIBLE

                            binding.TxtDescrVendedor.visibility = View.VISIBLE
                            binding.perfilVendedor.visibility = View.VISIBLE
                        }

                        //Seteamos la información en las vistas
                        binding.TvTitulo.text = titulo
                        binding.TvDescr.text = descripcion
                        binding.TvDireccion.text = direccion
                        binding.TvCondicion.text = condicion
                        binding.TvCat.text = categoria
                        binding.TvPrecio.text = precio
                        binding.TvEstado.text = estado
                        binding.TvFecha.text = formatoFecha

                        if (estado.equals("Disponible")){
                            binding.TvEstado.setTextColor(Color.BLUE)
                        }else{
                            binding.TvEstado.setTextColor(Color.RED)
                        }

                        //Información del vendedor
                        cargarInfoVendedor()


                    }catch (e:Exception){

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }

    private fun cargarInfoVendedor() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uidVendedor)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val telefono = "${snapshot.child("telefono").value}"
                    val codTel = "${snapshot.child("codigoTelefono").value}"
                    val nombres = "${snapshot.child("nombres").value}"
                    val imagenPerfil = "${snapshot.child("urlImagenPerfil").value}"
                    val tiempo_reg = snapshot.child("tiempo").value as Long

                    val for_fecha = Constantes.obtenerFecha(tiempo_reg)

                    telVendedor = "$codTel$telefono"

                    binding.TvNombres.text = nombres
                    binding.TvMiembro.text = for_fecha

                    try {
                        Glide.with(this@DetalleAnuncio)
                            .load(imagenPerfil)
                            .placeholder(R.drawable.perfil)
                            .into(binding.ImgPerfil)
                    }catch (e:Exception){

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })    }
    private fun cargarImgAnuncio(){
        imagenSliderArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
        ref.child(idAnuncio).child("Imagenes")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    imagenSliderArrayList.clear()
                    for (ds in snapshot.children){
                        try {
                            val modeloImgSlider = ds.getValue(ModeloImgSlider::class.java)
                            imagenSliderArrayList.add(modeloImgSlider!!)
                        }catch (e:Exception){

                        }
                    }

                    val adaptadorImgSlider = AdaptadorImgSlider(this@DetalleAnuncio,imagenSliderArrayList)
                    binding.imagenSliderVP.adapter = adaptadorImgSlider

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}
