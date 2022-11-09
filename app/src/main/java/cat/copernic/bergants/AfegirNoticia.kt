package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class AfegirNoticia : Fragment(R.layout.fragment_afegir_noticia) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSaveNot = requireView().findViewById<Button>(R.id.botoGuardarNoticia)

        btnSaveNot.setOnClickListener{
            findNavController().navigate(R.id.action_afegirNoticia_to_noticia_fragment)
        }
    }
}