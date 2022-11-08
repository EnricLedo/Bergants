package cat.copernic.bergants

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import cat.copernic.bergants.databinding.ActivityLoginBinding
import cat.copernic.bergants.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Login : AppCompatActivity() {

    /*Declarem els atributs que inicialitzarem més tard (lateinit) per guardar els components del formulari del activity_login.
      És recomanable per seguretat i facilitar-nos la feina que els noms d'aquests atributs siguin els mateixos que els noms dels
      id dels components del fitxer xml*/

    private lateinit var loginpage: View
    private lateinit var binding: ActivityLoginBinding

    //Declarem un atribut de tipus FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.supportActionBar!!.hide()
        val user = FirebaseAuth.getInstance().currentUser

        //Inicalitzem els atributs amb els components corresponents a l'id passat per paràmetre

        loginpage = findViewById(android.R.id.content)


        //Inicialitzem la variable de tipus FirebaseAuth amb una instància d'aquesta classe
        auth= Firebase.auth

        //Implementem els listeners per quan l'usuari cliqui un dels botons

        binding.logIn.setOnClickListener {

            //Guardem les dades introduïdes per l'usuari en el formulari mitjançant text i les transformem amb un String (toString())
            val correu = binding.email.text.toString()
            val contrasenya = binding.password.text.toString()

            //Comprovem que els camps no estan buit
            if(correu.isNotEmpty()&&contrasenya.isNotEmpty()){
                //Loguinem a l'usuari mitjançant la funció loguinar creada per nosaltres
                loguinar(correu, contrasenya)
            }else{ //El login (task) ha fallat...
                //Mostrem un missatge a l'usuari mitjançant un Toast
                Snackbar.make(it,"Introdueix un correu i una contrasenya", Snackbar.LENGTH_LONG).show()
            }
        }
        binding.forgottenPassword.setOnClickListener {
            startActivity(Intent(this,RecuperarContrasenya::class.java))
            finish() //Alliberem memòria un cop finalitzada aquesta tasca.
        }
    }

    //Funció per loginar a un usuari mitjançant Firebase Authentication
    private fun loguinar(correu: String, contrasenya: String){
        //Loginem a l'usuari
        auth.signInWithEmailAndPassword(correu,contrasenya)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){ //El loguin (task) s'ha completat amb exit...
                    //Anem al mainActivity des d'aquesta pantalla
                    startActivity(Intent(this,MainActivity::class.java))
                    finish() //Alliberem memòria un cop finalitzada aquesta tasca.
                }else{ //El login (task) ha fallat...
                    //Mostrem un missatge a l'usuari mitjançant un Toast
                    Snackbar.make(loginpage,"ERROR! El login ha fallat!", Snackbar.LENGTH_LONG).show()
                }
            }
    }
}