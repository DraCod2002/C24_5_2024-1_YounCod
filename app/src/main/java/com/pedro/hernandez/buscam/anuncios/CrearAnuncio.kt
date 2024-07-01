package com.pedro.hernandez.buscam.anuncios

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.pedro.hernandez.buscam.Adaptadores.AdaptadorImagenSeleccionada
import com.pedro.hernandez.buscam.Constantes
import com.pedro.hernandez.buscam.Model.ModeloImagenSeleccionada
import com.pedro.hernandez.buscam.R
import com.pedro.hernandez.buscam.SeleccionarUbicacion
import com.pedro.hernandez.buscam.databinding.ActivityCrearAnuncioBinding


class CrearAnuncio : AppCompatActivity() {

    private lateinit var binding : ActivityCrearAnuncioBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private var imagenUri : Uri?=null
    private lateinit var imagenSelecArrayList: ArrayList<ModeloImagenSeleccionada>
    private lateinit var adaptadorImagensel: AdaptadorImagenSeleccionada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearAnuncioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog =  ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        val adaptadorCat = ArrayAdapter(this, R.layout.item_categoria, Constantes.categorias)
        binding.Categoria.setAdapter(adaptadorCat)

        val adaptadorCon = ArrayAdapter(this, R.layout.item_condicion, Constantes.condiciones)
        binding.Condicion.setAdapter(adaptadorCon)

        imagenSelecArrayList = ArrayList()
        cargarImagenes()
        binding.agregarImg.setOnClickListener{
            mostrarOpciones()
        }
        binding.Locacion.setOnClickListener{
            val intent = Intent(this, SeleccionarUbicacion::class.java)
            seleccionarUbicacion_ARL.launch(intent)
        }
        binding.BtnCrearAnuncio.setOnClickListener{
            validarDatos()
        }

    }
    private var marca = ""
    private var categoria = ""
    private var condicion = ""
    private var direccion = ""
    private var precio = ""
    private var titulo = ""
    private var descripcion = ""
    private var latitud = 0.0
    private var longitud = 0.0
    private fun validarDatos(){
        marca = binding.ETMarca.text.toString().trim()
        categoria = binding.Categoria.text.toString().trim()
        condicion = binding.Condicion.text.toString().trim()
        direccion = binding.Locacion.text.toString().trim()
        precio = binding.EtPrecio.text.toString().trim()
        titulo = binding.EtTitulo.text.toString().trim()
        descripcion = binding.EtDescripcion.text.toString().trim()

        if(marca.isEmpty()){
            binding.ETMarca.error = "Ingrese una marca"
            binding.ETMarca.requestFocus()
        }else if (categoria.isEmpty()){
            binding.Categoria.error = "Ingrese una categoria"
            binding.Categoria.requestFocus()
        }
        else if (condicion.isEmpty()) {
            binding.Condicion.error = "Ingrese una condicion"
            binding.Condicion.requestFocus()
        }
        else if (direccion.isEmpty()){
            binding.Locacion.error = "Ingrese una Locacion"
            binding.Locacion.requestFocus()
        }
        else if (precio.isEmpty()) {
            binding.EtPrecio.error = "Ingrese un precio"
            binding.EtPrecio.requestFocus()
        }
        else if (titulo.isEmpty()) {
            binding.EtTitulo.error = "Ingrese un titulo"
            binding.EtTitulo.requestFocus()
        }
        else if (descripcion.isEmpty()) {
            binding.EtDescripcion.error = "Ingrese una condicion"
            binding.Condicion.requestFocus()
        }
        else if (imagenUri == null){
           Toast.makeText(this, "Agregue almenos una imagen",
            Toast.LENGTH_SHORT).show()
        }else{
            agregarAnuncio()
        }

    }

    private val seleccionarUbicacion_ARL =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                if (data != null){
                    latitud = data.getDoubleExtra("latitud", 0.0)
                    longitud = data.getDoubleExtra("longitud", 0.0)
                    direccion = data.getStringExtra("direccion") ?: ""
                    binding.Locacion.setText(direccion)
                }
            }else{
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            }
        }

    private fun agregarAnuncio() {
        progressDialog.setMessage("Agregando anuncio")
        progressDialog.show()

        val tiempo =  Constantes.ontenerTiempoDis()

        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")

        val keyId = ref.push().key

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "${keyId}"
        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["marca"] = "${marca}"
        hashMap["categoria"] = "${categoria}"
        hashMap["condicion"] = "${condicion}"
        hashMap["direccion"] = "${direccion}"
        hashMap["precio"] = "${precio}"
        hashMap["titulo"] = "${titulo}"
        hashMap["descripcion"] = "${descripcion}"
        hashMap["estado"] = "${Constantes.anuncio_disponible}"
        hashMap["tiempo"] = tiempo
        hashMap["latitud"] = latitud
        hashMap["longitud"] = longitud
        ref.child(keyId!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                cargarImagenesStorage(keyId)
            }
            .addOnFailureListener{e->
                Toast.makeText(
                    this, "${e.message}", Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun cargarImagenesStorage(keyId : String){
        for (i in imagenSelecArrayList.indices){
            val modeloImagenSel = imagenSelecArrayList[i]
            val nombreImagen = modeloImagenSel.id
            val rutaNombreImagen = "Anuncios/$nombreImagen"

            val storageReference = FirebaseStorage.getInstance().getReference(rutaNombreImagen)
            storageReference.putFile(modeloImagenSel.imagenUri!!)
                .addOnSuccessListener { taskSnaphot->
                    val uriTask = taskSnaphot.storage.downloadUrl
                    while (!uriTask.isSuccessful);
                    val urlImgCargada = uriTask.result

                    if(uriTask.isSuccessful){
                        val hasMap = HashMap<String, Any>()
                        hasMap["imagenUrl"] = "$urlImgCargada"

                        val ref =  FirebaseDatabase.getInstance().getReference("Anuncios")
                        ref.child(keyId).child("Imagenes")
                            .child(nombreImagen)
                            .updateChildren(hasMap)
                    }
                    progressDialog.dismiss()

                    Toast.makeText(this,
                        "Se publico su anuncio",
                        Toast.LENGTH_SHORT).show()
                    limpiarCampos()

                }
                .addOnFailureListener{e->
                    Toast.makeText(
                        this, "${e.message}", Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
    private fun limpiarCampos(){
        imagenSelecArrayList.clear()
        adaptadorImagensel.notifyDataSetChanged()
        binding.ETMarca.setText("")
        binding.Categoria.setText("")
        binding.Condicion.setText("")
        binding.Locacion.setText("")
        binding.EtPrecio.setText("")
        binding.EtTitulo.setText("")
        binding.EtDescripcion.setText("")
    }
    private fun mostrarOpciones() {
        val popuMenu = PopupMenu(this, binding.agregarImg)

        popuMenu.menu.add(Menu.NONE, 1, 1, "Cámara")
        popuMenu.menu.add(Menu.NONE, 2, 2, "Galería")

        popuMenu.show()
        popuMenu.setOnMenuItemClickListener { item->
            val itemId = item.itemId
            if (itemId ==1){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    solicitarPermisoCamara.launch(arrayOf(android.Manifest.permission.CAMERA))
                }else{
                    solicitarPermisoCamara.launch(arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ))
                }
            }else if (itemId == 2){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    imagenGaleria()
                }else{
                    solicitarPermisoAlmacenamiento.launch(

                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }
            }
            true
        }
    }

    private val solicitarPermisoAlmacenamiento = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){esConcedido->
        if(esConcedido){
            imagenGaleria()
        }else{
            Toast.makeText(
                this,
                "El permiso de almacenamiento ha sido denegado, o ambas fueron denegadas",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    private fun imagenGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultadoGaleria_ARL.launch(intent)
    }

    private val resultadoGaleria_ARL =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado->
            if(resultado.resultCode == Activity.RESULT_OK){
                val data = resultado.data
                imagenUri = data!!.data
                val tiempo = "${Constantes.ontenerTiempoDis()}"
                val modeloImgSel = ModeloImagenSeleccionada(
                    tiempo, imagenUri, null, false
                )
                imagenSelecArrayList.add(modeloImgSel)
                cargarImagenes()
            }else{
                Toast.makeText(
                    this,
                    "Cancelado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val solicitarPermisoCamara = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){resultado->
        var todosConcedidos = true
        for(esConcedido in resultado.values){
            todosConcedidos = todosConcedidos && esConcedido
        }
        if(todosConcedidos){
            imagenCamara()
        }else{
            Toast.makeText(
                this,
                "El permiso de la camara o almacenamiento ha sido denegado, o ambas fueron denegadas",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun imagenCamara() {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Titulo_imagen")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Descripcion_imagen")
        imagenUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri)
        resultadoCamara_ARL.launch(intent)
    }

    private val resultadoCamara_ARL =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado->
            if(resultado.resultCode == Activity.RESULT_OK){
                val tiempo = "${Constantes.ontenerTiempoDis()}"
                val modeloImgSel = ModeloImagenSeleccionada(
                    tiempo, imagenUri, null, false
                )
                imagenSelecArrayList.add(modeloImgSel)
                cargarImagenes()
            }else{
                Toast.makeText(
                    this,
                    "Cancelado",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    private fun cargarImagenes() {
        adaptadorImagensel = AdaptadorImagenSeleccionada(this, imagenSelecArrayList)
        binding.RVImagenes.adapter = adaptadorImagensel
    }
}