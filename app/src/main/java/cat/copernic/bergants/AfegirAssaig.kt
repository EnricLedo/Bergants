package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class AfegirAssaig : Fragment(R.layout.fragment_afegir_assaig) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSaveAss = requireView().findViewById<Button>(R.id.botoGuardarAssaig)

        btnSaveAss.setOnClickListener{
            findNavController().navigate(R.id.action_afegir_assaig_fragment_to_assajos_fragment)
        }
    }
}