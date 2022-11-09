package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class AfegirActuacio : Fragment(R.layout.fragment_afegir_actuacio) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSaveAct = requireView().findViewById<Button>(R.id.botoGuardarActuacio)

        btnSaveAct.setOnClickListener {
            findNavController().navigate(R.id.action_afegir_actuacio_fragment_to_actuacions_fragment)
        }
    }
}