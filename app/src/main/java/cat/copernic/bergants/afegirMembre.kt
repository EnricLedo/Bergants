package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class afegirMembre : Fragment(R.layout.fragment_afegir_membre) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSaveMem = requireView().findViewById<Button>(R.id.botoGuardarMembre)

        btnSaveMem.setOnClickListener {
            findNavController().navigate(R.id.action_afegirMembre_to_membres_fragment)
        }
    }
}