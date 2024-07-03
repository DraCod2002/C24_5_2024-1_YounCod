package com.pedro.hernandez.buscam.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pedro.hernandez.buscam.Model.ModeloCategoria
import com.pedro.hernandez.buscam.RVListenerCategoria
import com.pedro.hernandez.buscam.databinding.ItemCategoriaInicioBinding


class AdaptadorCategoria(
    private val context : Context,
    private val categoriaArryList : ArrayList<ModeloCategoria>,
    private val rvListenerCategoria: RVListenerCategoria
): Adapter<AdaptadorCategoria.HolderCategoria>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategoria {
        binding = ItemCategoriaInicioBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderCategoria(binding.root)
    }

    override fun getItemCount(): Int {
        return categoriaArryList.size
    }

    override fun onBindViewHolder(holder: HolderCategoria, position: Int) {
        val modeloCategoria = categoriaArryList[position]
        val icono = modeloCategoria.icon
        val categoria = modeloCategoria.categoria

        holder.categoriaIconoIv.setImageResource(icono)
        holder.categoriaTv.text = categoria

        holder.itemView.setOnClickListener{
            rvListenerCategoria.onCategoriaClick(modeloCategoria)
        }
    }
    private lateinit var binding: ItemCategoriaInicioBinding

    inner class HolderCategoria (itemView: View) : ViewHolder(itemView){
        var categoriaIconoIv = binding.categoriaIconoIv
        var categoriaTv = binding.TvCategoria
    }


}