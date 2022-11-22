package cat.copernic.bergants

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.databinding.ActivityMainBinding
import cat.copernic.bergants.model.Noticia
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //Atribut de tipus Button per tancar la sessió
    //private lateinit var botoLogout: Button
    private lateinit var binding: ActivityMainBinding
    //Declarem un atribut de tipus FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setupRecyclerView()

        toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigationView)

        navController = findNavController(R.id.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.actuacions_fragment, R.id.assajos_fragment, R.id.noticia_fragment, R.id.membres_fragment, R.id.perfil_fragment, R.id.configuracio_fragment), drawerLayout)
        setupActionBarWithNavController(navController, drawerLayout)
        navigationView.setupWithNavController(navController)



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
/**
    private fun setupRecyclerView(){
        //Especifiquem que els fills del RV seran del mateix tamany i així optimitzem la seva creació
        binding.recyclerNoticies.setHasFixedSize(true)

        //indiquem que el RV es mostrarà en format llista
        binding.recyclerNoticies.layoutManager = LinearLayoutManager(this)

        //generem el adapter
        myAdapter.NoticiesRecyclerAdapter(getNoticies(),this)

        //assignem el adapter al RV
        binding.recyclerNoticies.adapter = myAdapter
    }
    */

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
    private fun getNoticies():MutableList<Noticia>{
        val noticies: MutableList<Noticia> = arrayListOf()
        noticies.add(Noticia("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(Noticia("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(Noticia("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(Noticia("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(Noticia("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(Noticia("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(Noticia("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))

        return noticies

    }
    */

    /**
    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerNoticies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NoticiaAdapter(NoticiaProvider.noticiaList)
    }
    */

}

