package cat.copernic.bergants

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.adapter.NoticiaAdapter
import cat.copernic.bergants.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    //Atribut de tipus Button per tancar la sessió
    //private lateinit var botoLogout: Button

    //Declarem un atribut de tipus FirebaseAuth
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment
        navController =navHostFragment.navController

        setupActionBarWithNavController(navController)
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerNoticies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NoticiaAdapter(NoticiaProvider.noticiaList)
    }
    */

}

