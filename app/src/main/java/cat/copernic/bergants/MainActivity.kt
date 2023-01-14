package cat.copernic.bergants

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.Keep
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cat.copernic.bergants.databinding.ActivityMainBinding
import cat.copernic.bergants.databinding.MytoolbarBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.checkerframework.checker.units.qual.m

@Keep
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

        toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)

        drawerLayout = binding.drawer
        navigationView = binding.navigationView

        navController = findNavController(R.id.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.actuacions_fragment, R.id.assajos_fragment, R.id.noticia_fragment, R.id.membres_fragment, R.id.perfil_fragment, R.id.configuracio_fragment), drawerLayout)
        setupActionBarWithNavController(navController, drawerLayout)
        navigationView.setupWithNavController(navController)

    }

    /**
     * onSupportNavigateUp serveix per administrar el comportament del botó Navigation a la part
     * superior esquerra de l'àrea de visualització de la app. El comportament del botó
     * Navigation canvia si l'usuari és en una destinació de nivell superior.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}

