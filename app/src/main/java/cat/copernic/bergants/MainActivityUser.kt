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
import cat.copernic.bergants.databinding.ActivityMainUserBinding
import cat.copernic.bergants.databinding.MytoolbarBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.checkerframework.checker.units.qual.m

@Keep
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

        drawerLayout = binding.drawerUser
        navigationView = binding.navigationViewUser

        navController = findNavController(R.id.fragmentContainerViewUser)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.id_actuacio_usuari_fragment, R.id.id_assaig_usuari_fragment, R.id.id_noticies_usuari_fragment, R.id.id_membres_usuari_fragment, R.id.id_perfil_fragment, R.id.id_configuracio_fragment), drawerLayout)
        setupActionBarWithNavController(navController, drawerLayout)
        navigationView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerViewUser)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}