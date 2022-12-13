package cat.copernic.bergants

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class Utilities {
    fun campEsBuit(correu: String, contrasenya: String): Boolean {
        return correu.isNotEmpty() && contrasenya.isNotEmpty()
    }

    fun View.snack(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }

    var email_Param =
        Pattern.compile("^[_a-z0-9]+(.[_a-z0-9]+)*@[a-z0-9]+(.[a-z0-9]+)*(.[a-z]{2,4})\$")

    fun isValidEmail(email: CharSequence?): Boolean {
        return if (email == null) false else email_Param.matcher(email).matches()
    }

    //Funcions de la barra de progrés
    private var progressBar: ProgressBar? = null

    fun setProgressBar(bar: ProgressBar) {
        progressBar = bar
    }

    fun showProgressBar() {
        progressBar!!.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar!!.visibility = View.INVISIBLE
    }
}