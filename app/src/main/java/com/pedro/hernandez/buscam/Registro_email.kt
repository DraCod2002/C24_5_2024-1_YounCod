package com.pedro.hernandez.buscam

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pedro.hernandez.buscam.Opciones_login.Login_email
import com.pedro.hernandez.buscam.databinding.ActivityRegistroEmailBinding
import org.intellij.lang.annotations.Pattern


class Registro_email : AppCompatActivity() {
    private lateinit var binding : ActivityRegistroEmailBinding

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var progreDoalog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.TxtIngresar.setOnClickListener{
            startActivity(Intent(this@Registro_email, Login_email::class.java))
        }
        firebaseAuth = FirebaseAuth.getInstance()
        progreDoalog = ProgressDialog(this)
        progreDoalog.setTitle("Espere por favor")
        progreDoalog.setCanceledOnTouchOutside(false)

        binding.BtnRegistrar.setOnClickListener{
            validarInfo()
        }
    }
    private var email = ""
    private var password = ""
    private var r_password = ""
    private fun validarInfo() {
        email = binding.TxtEmail.text.toString().trim()
        password = binding.TxtPassword.text.toString().trim()
        r_password = binding.TxtRPassword.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.TxtEmail.error = "Email inválido"
            binding.TxtEmail.requestFocus()
        }
        else if(email.isEmpty()){
            binding.TxtEmail.error = "Ingrese email"
            binding.TxtEmail.requestFocus()
        }
        else if(password.isEmpty()){
            binding.TxtPassword.error = "Ingrese contraseña"
            binding.TxtPassword.requestFocus()
        }
        else if(r_password.isEmpty()){
            binding.TxtRPassword.error = "Repite la contraseña"
            binding.TxtRPassword.requestFocus()
        }
        else if(password != r_password){
            binding.TxtRPassword.error = "No coincide"
            binding.TxtRPassword.requestFocus()
        }
        else{
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        progreDoalog.setMessage("Creando cuenta")
        progreDoalog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                llenarInfoBD()
            }
            .addOnFailureListener{e->
                progreDoalog.dismiss()
                Toast.makeText(
                    this,
                    "No se registro el usuario debido a ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }

    private fun llenarInfoBD(){
        progreDoalog.setMessage("Guardando información")
        val tiempo = Constantes.ontenerTiempoDis()
        val emailUsuario = firebaseAuth.currentUser!!.email
        val uidUsuario = firebaseAuth.uid

        val hashMap = HashMap<String, Any>()
        hashMap["nombres"] = ""
        hashMap["codigoTelefono"] = ""
        hashMap["telefono"] = ""
        hashMap["urlImagenPerfil"] = ""
        hashMap["proveedor"] = "Email"
        hashMap["escribiendo"] = ""
        hashMap["tiempo"] = tiempo
        hashMap["online"] =  true
        hashMap["email"] = "${emailUsuario}"
        hashMap["uid"] = "${uidUsuario}"
        hashMap["fecha_nac"] = ""

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uidUsuario!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progreDoalog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener{e->
                progreDoalog.dismiss()
                Toast.makeText(
                    this,
                    "No se registró debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}