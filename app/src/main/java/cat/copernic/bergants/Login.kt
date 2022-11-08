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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Login : AppCompatActivity() {

    /*Declarem els atributs que inicialitzarem més tard (lateinit) per guardar els components del formulari del activity_login.
      És recomanable per seguretat i facilitar-nos la feina que els noms d'aquests atributs siguin els mateixos que els noms dels
      id dels components del fitxer xml*/

    private lateinit var correuLogin:EditText
    private lateinit var contrasenyaLogin:EditText
    private lateinit var textRecuperarContrasenya: TextView
    private lateinit var botoLogin:Button
    private lateinit var loginpage: View

    //Declarem un atribut de tipus FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.supportActionBar!!.hide()
        val user = FirebaseAuth.getInstance().currentUser

        //Inicalitzem els atributs amb els components corresponents a l'id passat per paràmetre
        correuLogin = findViewById(R.id.email)
        contrasenyaLogin = findViewById(R.id.password)
        botoLogin = findViewById(R.id.logIn)
        textRecuperarContrasenya = findViewById(R.id.forgottenPassword)
        loginpage = findViewById(android.R.id.content)


        //Inicialitzem la variable de tipus FirebaseAuth amb una instància d'aquesta classe
        auth= Firebase.auth

        //Implementem els listeners per quan l'usuari cliqui un dels botons

        botoLogin.setOnClickListener {

            //Guardem les dades introduïdes per l'usuari en el formulari mitjançant text i les transformem amb un String (toString())
            val correu = correuLogin.text.toString()
            val contrasenya = contrasenyaLogin.text.toString()

            //Comprovem que els camps no estan buit
            if(campEsBuit(correu, contrasenya)){
                //Loguinem a l'usuari mitjançant la funció loguinar creada per nosaltres
                loguinar(correu, contrasenya)
            }else{ //El login (task) ha fallat...
                //Mostrem un missatge a l'usuari mitjançant un Toast
                Snackbar.make(it,"Introdueix un correu i una contrasenya", Snackbar.LENGTH_LONG).show()
            }
        }
        textRecuperarContrasenya.setOnClickListener {
            startActivity(Intent(this,RecuperarContrasenya::class.java))
            finish() //Alliberem memòria un cop finalitzada aquesta tasca.
        }
    }

    override fun onStart() {
        super.onStart() //Cridem al la funció onStart() perquè ens mostri per pantalla l'activity
        //currentUser és un atribut de la classe FirebaseAuth que guarda l'usuari autenticat. Si aquest no està autenticat, el seu valor serà null.
        val currentUser = auth.currentUser
        if(currentUser != null){ //Sí l'usuari no ha tancat sessió (està autenticat)...
            //Anem al mainActivity des d'aquesta pantalla
            startActivity(Intent(this,MainActivity::class.java))
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

    fun campEsBuit(correu:String,contrasenya:String):Boolean{
        return correu.isNotEmpty()&&contrasenya.isNotEmpty()
    }
}