package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentAfegirMembreBinding

class afegirMembre : Fragment() {
    private lateinit var binding: FragmentAfegirMembreBinding

    //EditText per introduïr les dades del nou membre a afegir
    private lateinit var nomMembre: EditText
    private lateinit var contingutNoticia: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSaveMem = requireView().findViewById<Button>(R.id.botoGuardarMembre)

        btnSaveMem.setOnClickListener {
            findNavController().navigate(R.id.action_afegirMembre_to_membres_fragment)
        }
    }
}