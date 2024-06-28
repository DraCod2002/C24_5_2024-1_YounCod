package com.pedro.hernandez.buscam.Opciones_login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.pedro.hernandez.buscam.MainActivity
import com.pedro.hernandez.buscam.R
import com.pedro.hernandez.buscam.Registro_email
import com.pedro.hernandez.buscam.databinding.ActivityLoginEmailBinding

class Login_email : AppCompatActivity() {
    private lateinit var binding : ActivityLoginEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por gavor")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.BtnIngresar.setOnClickListener{
            validarInfo()
        }
        binding.TxtRegistrarme.setOnClickListener{
            startActivity(Intent(this@Login_email, Registro_email::class.java))
        }
    }
    private var email = ""
    private var password = ""
    private fun validarInfo() {
        email = binding.TxtEmail.text.toString().trim()
        password = binding.TxtPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.TxtEmail.error = "Correo inválido"
            binding.TxtEmail.requestFocus()
        }
        else if(password.isEmpty()){
            binding.TxtPassword.error = "Ingrese contraseña"
            binding.TxtPassword.requestFocus()
        } else{
            loginUsuario()
        }
    }

    private fun loginUsuario() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
                Toast.makeText(
                    this,
                    "Bienvenido(a)",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se údo iniciar sesión debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}