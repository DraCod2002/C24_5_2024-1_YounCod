package com.pedro.hernandez.buscam.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.pedro.hernandez.buscam.Model.ModeloImagenSeleccionada
import com.pedro.hernandez.buscam.R
import com.pedro.hernandez.buscam.databinding.ItemImagenesSeleccionadaBinding

class AdaptadorImagenSeleccionada (
    private val context : Context,
    private val imagenSelecArrayList : ArrayList<ModeloImagenSeleccionada>
): Adapter<AdaptadorImagenSeleccionada.HolderImagenSeleccionada>(){
    private lateinit var binding : ItemImagenesSeleccionadaBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImagenSeleccionada {
        binding = ItemImagenesSeleccionadaBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderImagenSeleccionada(binding.root)
    }

    override fun getItemCount(): Int {
        return imagenSelecArrayList.size
    }

    override fun onBindViewHolder(holder: HolderImagenSeleccionada, position: Int) {
        val modelo = imagenSelecArrayList[position]

        val imagenUri = modelo.imagenUri

        try {
            Glide.with(context)
                .load(imagenUri)
                .placeholder(R.drawable.item_image)
                .into(holder.item_imagen)
        }catch (e: Exception){

        }
        holder.btn_cerrar.setOnClickListener{
            imagenSelecArrayList.remove(modelo)
            notifyDataSetChanged()
        }
    }

    inner class HolderImagenSeleccionada(itemView : View) : ViewHolder(itemView){
        var item_imagen = binding.itemImage
        var btn_cerrar = binding.cerraItem
    }


}