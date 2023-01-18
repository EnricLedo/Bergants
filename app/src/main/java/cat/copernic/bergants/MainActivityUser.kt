package cat.copernic.bergants

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import cat.copernic.bergants.R
import cat.copernic.bergants.databinding.ActivityMainBinding
import cat.copernic.bergants.databinding.ActivityMainUserBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivityUser : AppCompatActivity() {
    //Atribut de tipus Button per tancar la sessió
    //private lateinit var botoLogout: Button
    private lateinit var binding: ActivityMainUserBinding
    //Declarem un atribut de tipus FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    //El codi està configurant un calaix de navegació per a l'activitat.
    //
    //Primer infla un objecte d'enllaç de vista anomenat ActivityMainBinding i estableix la vista de
    // contingut a l'arrel de l'objecte d'enllaç.
    //
    //A continuació, configura una barra d'eines trucant al mètode findViewById a la vista passat a
    // setContentView per obtenir una referència a la vista de la barra d'eines i estableix la barra
    // d'eines com a barra d'acció de suport.
    //
    //A continuació, obté referències a la disposició del calaix i a la vista de navegació.
    //
    //Obté una referència al NavController trucant a findNavController a la vista amb l'identificador
    // "fragmentContainerView" i crea un objecte AppBarConfiguration que pren un conjunt d'ID de destinació
    // i la disposició del calaix.
    //
    //Configura la barra d'acció amb el NavController i la disposició del calaix trucant a
    // setupActionBarWithNavController i configura la vista de navegació amb el NavController trucant a
    // setupWithNavController a la vista de navegació.
    /**

    Aquesta funció es crida quan l'activitat es crea. S'infla la vista, es configura la Toolbar i es configura el comportament del Navigation Drawer i el NavigationView per tal de navegar per les diferents opcions de l'aplicació.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.myToolbarUser)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main_activity_user)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main_activity_user)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}