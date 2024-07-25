package com.pedro.hernandez.buscam.filtro

import android.widget.Filter
import com.pedro.hernandez.buscam.Adaptadores.AdaptadroAnuncio
import com.pedro.hernandez.buscam.Model.ModeloAnuncio
import java.util.Locale

class FiltrarAnuncio (
    private val adaptador : AdaptadroAnuncio,
    private val filtroLista : ArrayList<ModeloAnuncio>
) : Filter(){
    override fun performFiltering(filtro: CharSequence?): FilterResults {
        var filtro = filtro
        val resultado = FilterResults()

        if(!filtro.isNullOrEmpty()){
            filtro = filtro.toString().uppercase(Locale.getDefault())
            val filtradoModelo = ArrayList<ModeloAnuncio>()
            for(i in filtroLista.indices){
                if(filtroLista[i].marca.uppercase(Locale.getDefault()).contains(filtro) ||
                        filtroLista[i].categoria.uppercase(Locale.getDefault()).contains(filtro) ||
                        filtroLista[i].condicion.uppercase(Locale.getDefault()).contains(filtro) ||
                        filtroLista[i].titulo.uppercase(Locale.getDefault()).contains(filtro)){
                    filtradoModelo.add(filtroLista[i])
                }

            }
            resultado.count = filtradoModelo.size
            resultado.values = filtradoModelo
        }else{
            resultado.count = filtroLista.size
            resultado.values = filtroLista
        }
        return resultado
    }

    override fun publishResults(filtro: CharSequence?, resultados: FilterResults) {
        adaptador.anuncioArrayList = resultados.values as ArrayList<ModeloAnuncio>
        adaptador.notifyDataSetChanged()

    }
}
