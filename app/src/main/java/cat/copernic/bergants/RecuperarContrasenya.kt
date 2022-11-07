package cat.copernic.bergants

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RecuperarContrasenya : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var correuRecuperar: EditText
    private lateinit var recuperarContrasenya: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasenya)

        correuRecuperar = findViewById(R.id.emailContrasenya)
        recuperarContrasenya = findViewById(R.id.editar)
        val correu = correuRecuperar.text.toString()


        recuperarContrasenya.setOnClickListener {

            //Guardem les dades introduïdes per l'usuari en el formulari mitjançant text i les transformem amb un String (toString())

            //Comprovem que els camps no estan buit
            if(correu.isNotEmpty()){
                resetPassword(correu)
            }else{
                Toast.makeText(applicationContext,"Introdueix un correu!", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun resetPassword(correu: String){
        auth.sendPasswordResetEmail(correu)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"Revisa el teu correu per a recuperar la contrasenya.", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,Login::class.java))
                    finish()
                }else{
                    Toast.makeText(applicationContext,"El reseteig ha fallat", Toast.LENGTH_LONG).show()
                }
            }
    }
}