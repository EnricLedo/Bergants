package cat.copernic.bergants

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.adapter.NoticiaAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //Atribut de tipus Button per tancar la sessió
    private lateinit var botoLogout: Button

    //Declarem un atribut de tipus FirebaseAuth
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initRecyclerView()
        //Inicialitzem l'atribut botologout amb el component de l'XML corresponent
        botoLogout = findViewById(R.id.botoLogout)

        //Inicialitzem la variable de tipus FirebaseAuth amb una instància d'aquesta classe
        auth= Firebase.auth

        //Listener per quan l'usuari cliqui el botó de tancar sessió
        botoLogout.setOnClickListener {

            //Tanquem la sessió mitjançant el mètode signOut() de la classe FirebaseAuth
            auth.signOut()
            //Tornem a la pàgina de loguin
            startActivity(Intent(this,Login::class.java))
            //Alliberem memòria
            finish()
        }
    }

    /**
    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerNoticies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NoticiaAdapter(NoticiaProvider.noticiaList)
    }
    */

}

