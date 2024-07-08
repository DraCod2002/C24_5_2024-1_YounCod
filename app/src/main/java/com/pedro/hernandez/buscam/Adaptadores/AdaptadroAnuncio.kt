package com.pedro.hernandez.buscam.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pedro.hernandez.buscam.Constantes
import com.pedro.hernandez.buscam.Model.ModeloAnuncio
import com.pedro.hernandez.buscam.R
import com.pedro.hernandez.buscam.databinding.ItemAnuncioBinding
import com.pedro.hernandez.buscam.filtro.FiltrarAnuncio

class AdaptadroAnuncio : RecyclerView.Adapter<AdaptadroAnuncio.HolderAnuncio>, Filterable {
    private lateinit var binding : ItemAnuncioBinding
    private var context : Context
    var anuncioArrayList : ArrayList<ModeloAnuncio>
    private var firebaseAuth : FirebaseAuth
    private var filtroLista :ArrayList<ModeloAnuncio>
    private var filtro : FiltrarAnuncio? = null
    constructor(context: Context, anuncioArrayList: ArrayList<ModeloAnuncio>) {
        this.context = context
        this.anuncioArrayList = anuncioArrayList
        firebaseAuth = FirebaseAuth.getInstance()
        this.filtroLista = anuncioArrayList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderAnuncio {
        binding = ItemAnuncioBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderAnuncio(binding.root)
    }

    override fun getItemCount(): Int {
        return anuncioArrayList.size
    }

    override fun onBindViewHolder(holder: HolderAnuncio, position: Int) {
       val modeloAnuncio = anuncioArrayList[position]
        val titulo = modeloAnuncio.titulo
        val descripcion = modeloAnuncio.descripcion
        val direccion = modeloAnuncio.direccion
        val condicion = modeloAnuncio.condicion
        val precio = modeloAnuncio.precio
        val tiempo = modeloAnuncio.tiempo

        val formatoFecha = Constantes.obtenerFecha(tiempo)

        cargarPrimeraImgAnuncio(modeloAnuncio, holder)
        holder.Tv_titulo.text = titulo
        holder.Tv_descripcion.text = descripcion
        holder.Tv_direccion.text = direccion
        holder.Tv_condicion.text = condicion
        holder. Tv_precio.text = precio
        holder.Tv_fecha.text = formatoFecha
    }

    private fun cargarPrimeraImgAnuncio(modeloAnuncio: ModeloAnuncio, holder: AdaptadroAnuncio.HolderAnuncio) {
        val idAnuncio = modeloAnuncio.id
        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
        ref.child(idAnuncio).child("Imagenes").limitToFirst(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        val imagenUrl = "${ds.child("imagenUrl").value}"
                        try {
                            Glide.with(context)
                                .load(imagenUrl)
                                .placeholder(R.drawable.ic_imagen)
                                .into(holder.imahenIv)
                        }catch (e:Exception){

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    inner class HolderAnuncio(itemView : View) :RecyclerView.ViewHolder(itemView){
        var imahenIv = binding.imagenIv
        var Tv_titulo = binding.TvTitulo
        var Tv_descripcion = binding.TvDescripcion
        var Tv_direccion = binding.TvDireccion
        var Tv_condicion = binding.TvCondicion
        var Tv_precio = binding.TvPrecio
        var Tv_fecha = binding.TvFecha
        var Ib_fav = binding.IbFav
    }

    override fun getFilter(): Filter {
        if (filtro == null){
            filtro = FiltrarAnuncio(this, filtroLista)
        }
        return filtro as FiltrarAnuncio
    }


}