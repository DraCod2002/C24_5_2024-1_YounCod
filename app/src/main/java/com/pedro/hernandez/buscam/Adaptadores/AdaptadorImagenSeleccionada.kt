package com.pedro.hernandez.buscam.Adaptadores
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase
import com.pedro.hernandez.buscam.Model.ModeloImagenSeleccionada
import com.pedro.hernandez.buscam.R
import com.pedro.hernandez.buscam.databinding.ItemImagenesSeleccionadaBinding

class AdaptadorImagenSeleccionada (
    private val context : Context,
    private val imagenSelecArrayList : ArrayList<ModeloImagenSeleccionada>,
    private val idAnuncio : String
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
        if(modelo.deInternet){
            /*Haremos lectura de las imagenes traidas desde firebase*/
            try {
                val imagenUrl = modelo.imagenUrl
                Glide.with(context)
                    .load(imagenUrl)
                    .placeholder(R.drawable.item_image)
                    .into(binding.itemImage)
            }catch (e: Exception){
            }
        }else{
            /* Haremos lectura de las imagenes seleccionadas desde la galería o tomadas desde la camara*/
            try {
                val imagenUri = modelo.imagenUri
                Glide.with(context)
                    .load(imagenUri)
                    .placeholder(R.drawable.item_image)
                    .into(holder.item_imagen)
            }catch (e: Exception){

            }
        }

        holder.btn_cerrar.setOnClickListener {
            if (modelo.deInternet) {
                // Declarar las vistas del diseño
                val Btn_si: MaterialButton
                val Btn_no: MaterialButton
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.cuadro_d_eliminar_imagen)
                Btn_si =  dialog.findViewById(R.id.Btn_si)
                Btn_no =  dialog.findViewById(R.id.Btn_no)

                Btn_si.setOnClickListener{
                    eliminarImgFirebase(modelo, holder, position)
                    dialog.dismiss()
                }
                Btn_no.setOnClickListener{
                    dialog.dismiss()
                }
                dialog.show()
                dialog.setCanceledOnTouchOutside(false)

            } else {
                imagenSelecArrayList.remove(modelo)
                notifyDataSetChanged()
            }
        }

    }

    private fun eliminarImgFirebase(modelo: ModeloImagenSeleccionada, holder: AdaptadorImagenSeleccionada.HolderImagenSeleccionada, position: Int) {
        val idImagen = modelo.id
        /*La imagen se eliminara de la base de datos*/
        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
        ref.child(idAnuncio).child("Imagenes").child(idImagen)
            .removeValue()
            .addOnSuccessListener {
                try {
                    imagenSelecArrayList.remove(modelo)
                    eliminarImagenStorage(modelo) /*La imagen tambien se eliminara del storage*/
                    notifyItemRemoved(position)
                }catch (e: Exception){

                }
            }
            .addOnFailureListener{e->
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun eliminarImagenStorage(modelo: ModeloImagenSeleccionada) {
        val rutaImagen = "Anuncios/"+modelo.id;
        val ref = FirebaseDatabase.getInstance().getReference(rutaImagen)
        ref.removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "La imagen se ha eliminado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    inner class HolderImagenSeleccionada(itemView : View) : ViewHolder(itemView){
        var item_imagen = binding.itemImage
        var btn_cerrar = binding.cerraItem
    }


}