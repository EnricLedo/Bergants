package cat.copernic.bergants

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import cat.copernic.bergants.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Keep
class Login : AppCompatActivity() {

    /*Declarem els atributs que inicialitzarem més tard (lateinit) per guardar els components del formulari del activity_login.
      És recomanable per seguretat i facilitar-nos la feina que els noms d'aquests atributs siguin els mateixos que els noms dels
      id dels components del fitxer xml*/

    private lateinit var loginpage: View
    private lateinit var binding: ActivityLoginBinding

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    //Declarem un atribut de tipus FirebaseAuth
    private lateinit var auth: FirebaseAuth

    /**

    Aquesta funció és cridada quan l'activitat és creada. Es configura la vista del layout, es crea un canal de notificació, s'amaga la ActionBar i es comprova si l'usuari ja està connectat. També es configura el comportament dels botons de log in i recuperar contrasenya.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        createNotificationChannel()
        setContentView(R.layout.activity_login)
        this.supportActionBar!!.hide()
        val user = FirebaseAuth.getInstance().currentUser

        //Inicalitzem els atributs amb els components corresponents a l'id passat per paràmetre
        loginpage = findViewById(android.R.id.content)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Inicialitzem la variable de tipus FirebaseAuth amb una instància d'aquesta classe
        auth= Firebase.auth

        //Implementem els listeners per quan l'usuari cliqui un dels botons
        binding.logIn.setOnClickListener {

            //Guardem les dades introduïdes per l'usuari en el formulari mitjançant text i les transformem amb un String (toString())
            val correu = binding.email.text.toString()
            val contrasenya = binding.password.text.toString()

            //Comprovem que els camps no estan buit
            if(campEsBuit(correu, contrasenya)){
                //Loguinem a l'usuari mitjançant la funció loguinar creada per nosaltres
                loguinar(correu, contrasenya)
            }else{ //El login (task) ha fallat...
                //Mostrem un missatge a l'usuari mitjançant un Toast
                Snackbar.make(it,getString(R.string.correuContra), Snackbar.LENGTH_LONG).show()
            }
        }
        //Quan es fa clic a la vista, el codi iniciarà una activitat anomenada "RecuperarContrasenya"
        // mitjançant la classe Intent i el mètode startActivity. La paraula clau this s'utilitza per
        // passar el context actual.
        binding.forgottenPassword.setOnClickListener {
            startActivity(Intent(this,RecuperarContrasenya::class.java))
            finish() //Alliberem memòria un cop finalitzada aquesta tasca.
        }
    }

    /**

    Aquesta funció és cridada quan l'activitat comença. Es comprova si l'usuari ja està autenticat i si ho està, es mostra la pantalla principal de l'aplicació.
     */

    override fun onStart() {
        super.onStart() //Cridem al la funció onStart() perquè ens mostri per pantalla l'activity
        //currentUser és un atribut de la classe FirebaseAuth que guarda l'usuari autenticat. Si aquest no està autenticat, el seu valor serà null.
        val currentUser = auth.currentUser
        val intentAdmin = Intent(this,MainActivity::class.java)
        val intent = Intent(this,MainActivityUser::class.java)
        if(currentUser != null){
        //Sí l'usuari no ha tancat sessió (està autenticat)...
            lifecycleScope.launch{
                val queryAdmin = bd.collection("Membres").document(currentUser.email!!).get().await()

                if(queryAdmin.getBoolean("adminMembre") == true){
                    startActivity(intentAdmin)
                    finish()
                } else{
                    startActivity(intent)
                    finish()
                }
            }
        }
    }



    //Funció per loginar a un usuari mitjançant Firebase Authentication
    /**

    Aquesta funció s'encarrega de loguear a l'usuari amb les dades del correu i contrasenya proporcionades. Si el logueig es completa amb èxit, es mostra la pantalla principal de l'aplicació, en cas contrari es mostra un missatge d'error.
     */
    private fun loguinar(correu: String, contrasenya: String){
        //Loginem a l'usuari
        val intentAdmin = Intent(this,MainActivity::class.java)
        val intent = Intent(this,MainActivityUser::class.java)
        auth.signInWithEmailAndPassword(correu,contrasenya)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){ //El loguin (task) s'ha completat amb exit...
                    lifecycleScope.launch{
                        val queryAdmin = bd.collection("Membres").document(correu).get().await()

                        if (queryAdmin.getBoolean("adminMembre")== true){
                            //Anem al mainActivity des d'aquesta pantalla
                            startActivity(intentAdmin)
                            finish() //Alliberem memòria un cop finalitzada aquesta tasca.
                        } else{
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{ //El login (task) ha fallat...
                    //Mostrem un missatge a l'usuari mitjançant un Toast
                    Snackbar.make(loginpage,getString(R.string.errorLogin), Snackbar.LENGTH_LONG).show()
                }
            }
    }


    //Aquest codi defineix una funció anomenada "campEsBuit" en Kotlin que pren dos arguments, correu i
    // contrasenya, que són cadenes que representen camps de correu electrònic i contrasenya.
    //Aleshores, la funció comprova si els dos camps no estan buits cridant a la funció isNotEmpty() a
    // cada camp i retorna el resultat de l'operació lògica AND entre ells.
    //Retorna true si els dos camps no estan buits i fals en cas contrari, probablement s'utilitza per
    // comprovar si l'usuari omple els camps de correu electrònic i contrasenya.
    /**

    Aquesta funció comprova si els camps del correu i contrasenya són buits o no.
    @param correu correu de l'usuari introduit per l'usuari
    @param contrasenya contrasenya introduida per l'usuari
    @return Retorna true si els camps no estan buits, false en cas contrari.
     */
    fun campEsBuit(correu:String,contrasenya:String):Boolean{
        return correu.isNotEmpty()&&contrasenya.isNotEmpty()
    }

    //Aquest codi defineix una funció anomenada "createNotificationChannel" a Kotlin, que s'utilitza
    //per crear un canal de notificació en dispositius amb el nivell 26 de l'API d'Android o superior.
    //La funció primer comprova la versió de l'SDK que està executant el dispositiu, si és superior o
    // igual a Android 8.0 (nivell API 26), després crea un canal de notificació amb l'ID "1", un nom,
    // una descripció i un nivell d'importància. El nivell d'importància s'estableix en
    // "IMPORTANCE_DEFAULT", el que significa que el canal s'utilitza per a notificacions que no són
    // urgents però que s'han de mostrar a l'usuari. També registra el canal amb el sistema mitjançant
    // la classe NotificationManager.
    //Aquesta funció s'utilitza per configurar un canal de notificacions per a l'aplicació, d'aquesta
    // manera l'aplicació pot enviar notificacions a l'usuari.
    /**

    Aquesta funció crea un canal de notificació per a l'aplicació, però només en dispositius amb API 26 o superior.
     */
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "descripcion"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
