package cat.copernic.bergants

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Keep
import cat.copernic.bergants.databinding.ActivityLoginBinding
import cat.copernic.bergants.databinding.ActivityMainBinding
import cat.copernic.bergants.databinding.ActivityRecuperarContrasenyaBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Keep
class RecuperarContrasenya : AppCompatActivity() {
    private lateinit var passwordpage: View

    private lateinit var binding: ActivityRecuperarContrasenyaBinding

    private lateinit var auth: FirebaseAuth

    /**

    Aquesta funció s'executa en la creació de l'activity. Es crea el binding per a l'activity i s'assigna la vista.
    Es defineix un listener per al botó "Editar" que recupera la contrasenya de l'usuari. Si l'usuari ha introduït el correu, es fa una crida al mètode restaurarContrasenya, el qual enviarà un missatge de restauració a l'adreça de correu introduïda per l'usuari. Si no s'ha introduït cap correu, mostra una notificació per a que l'usuari introduisca una adreça de correu.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarContrasenyaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = FirebaseAuth.getInstance().currentUser

        passwordpage = findViewById(android.R.id.content)

        auth= Firebase.auth

        binding.editar.setOnClickListener {

            var correu = this.binding.emailContrasenya.text.toString() //Guardem el correu introduït per l'usuari

            if(correu.isNotEmpty()){ //Si l'usuari ha introduït el correu....
                //Restaurem la contrasenya mitjançant el mètode restaurarContrasenya que hem creat, el qual enviarà
                //al correu passat per paràmetre un missatge de restauració. El correu ha d'estar donat d'alta a
                //Authentication, és a dir, ha de ser el correu que s'ha fet servir per registrar a l'usuari que
                //vol restaurar la seva contrasenya.
                restaurarContrasenya(correu)
            }else{
                Snackbar.make(it,getString(R.string.addCorreu),Snackbar.LENGTH_LONG).show()
            }

        }
    }

    /**

    Aquesta funció envia un correu de restabliment de contrasenya a l'adreça de correu passada per paràmetre.

    Utilitza el mètode sendPasswordResetEmail de Firebase Authentication per enviar el correu de restabliment de contrasenya.

    Si la tasca té èxit, mostra un missatge de Snackbar a l'usuari que indica que el restabliment de la contrasenya s'ha realitzat correctament i navega a l'activitat d'inici de sessió.

    Si la tasca no té èxit, mostra un missatge de Snackbar a l'usuari que indica que el restabliment de la contrasenya no ha tingut èxit.
     */
    fun restaurarContrasenya(correu: String){

        //Li indiquem al sistema en quin llenguatge ha de ser el correu de restauració de contrasenya que li enviarem a l'usuari. En el nostre cas català ("ca")
        auth.setLanguageCode("ca")

        //Enviem a l'usuari el correu d'autenticació al correu passat per paràmetre. Aquest mètode comprova que el correu sigui el correu d'un dels registres.
        auth.sendPasswordResetEmail(correu).addOnCompleteListener { task ->

            //Si la tasca té èxit, mostra un missatge de Snackbar a l'usuari que indica que el restabliment
            // de la contrasenya s'ha realitzat correctament i navega a l'activitat d'inici de sessió
            // creant un Intent i trucant a startActivity. També crida al mètode finish() per alliberar
            // la memòria de l'activitat actual.
            //Si la tasca no té èxit, mostra un missatge de Snackbar a l'usuari que indica que el
            // restabliment de la contrasenya no ha tingut èxit.
            if(task.isSuccessful){
                Snackbar.make(passwordpage,getString(R.string.restaurar),Snackbar.LENGTH_LONG).show()
                startActivity(Intent(this,Login::class.java))
                finish() //Alliberem memòria un cop finalitzada aquesta tasca.
            }else{
                Snackbar.make(passwordpage,getString(R.string.noRestaurar),Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
