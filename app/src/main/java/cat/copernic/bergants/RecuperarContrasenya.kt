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
import cat.copernic.bergants.databinding.ActivityLoginBinding
import cat.copernic.bergants.databinding.ActivityMainBinding
import cat.copernic.bergants.databinding.ActivityRecuperarContrasenyaBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RecuperarContrasenya : AppCompatActivity() {
    private lateinit var passwordpage: View

    private lateinit var binding: ActivityRecuperarContrasenyaBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasenya)
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
                Snackbar.make(it,"Introdueix un correu!!!",Snackbar.LENGTH_LONG).show()
            }

        }
    }

    fun restaurarContrasenya(correu: String){

        //Li indiquem al sistema en quin llenguatge ha de ser el correu de restauració de contrasenya que li enviarem a l'usuari. En el nostre cas català ("ca")
        auth.setLanguageCode("ca")

        //Enviem a l'usuari el correu d'autenticació al correu passat per paràmetre. Aquest mètode comprova que el correu sigui el correu d'un dels registres.
        auth.sendPasswordResetEmail(correu).addOnCompleteListener { task ->

            if(task.isSuccessful){
                Snackbar.make(passwordpage,"Contrasenya restaurada amb èxit. Rebràs un correu.",Snackbar.LENGTH_LONG).show()
                startActivity(Intent(this,Login::class.java))
                finish() //Alliberem memòria un cop finalitzada aquesta tasca.
            }else{
                Snackbar.make(passwordpage,"No s'ha pogut restaurar la contrasenya!!",Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
