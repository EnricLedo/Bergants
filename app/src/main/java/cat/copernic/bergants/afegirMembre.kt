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
import cat.copernic.bergants.model.ActuacioModel

class afegirMembre : Fragment() {
    private lateinit var binding: FragmentAfegirMembreBinding

    //EditText per introduïr les dades del nou membre a afegir
    private lateinit var nomMembre: EditText
    private lateinit var malnom: EditText
    private lateinit var alcadaEspatlles: EditText
    private lateinit var AlcadaMans: EditText
    private lateinit var correuMembre: EditText
    private lateinit var adrecaMembre: EditText
    private lateinit var telefonMembre: EditText
    private lateinit var rolMembre: EditText

    //Atribut de tipus Button per afegir un nou membre
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem les actuacions
    private lateinit var actuacions: ActuacioModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSaveMem = requireView().findViewById<Button>(R.id.botoGuardarMembre)

        btnSaveMem.setOnClickListener {
            findNavController().navigate(R.id.action_afegirMembre_to_membres_fragment)
        }
    }
}