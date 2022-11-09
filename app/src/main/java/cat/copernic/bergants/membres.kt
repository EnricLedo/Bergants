package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class membres : Fragment(R.layout.fragment_membres) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddMem = requireView().findViewById<Button>(R.id.botoAfegirMembre)

        btnAddMem.setOnClickListener{
            findNavController().navigate(R.id.action_membres_fragment_to_afegirMembre)
        }
    }
}