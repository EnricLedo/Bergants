package cat.copernic.bergants

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
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
        this.supportActionBar!!.hide()
        /**
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
         */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_desplegable_noticies, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actuacions_fragment-> Toast.makeText(this, "Actuacions", Toast.LENGTH_SHORT).show()
            R.id.assajos_fragment-> Toast.makeText(this, "Assajos", Toast.LENGTH_SHORT).show()
            R.id.noticia_fragment-> Toast.makeText(this, "Noticies", Toast.LENGTH_SHORT).show()
            R.id.membres_fragment-> Toast.makeText(this, "Membres", Toast.LENGTH_SHORT).show()
            R.id.perfil_fragment-> Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
            R.id.configuracio_fragment-> Toast.makeText(this, "Configuracio", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
    /**
    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerNoticies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NoticiaAdapter(NoticiaProvider.noticiaList)
    }
    */

}

