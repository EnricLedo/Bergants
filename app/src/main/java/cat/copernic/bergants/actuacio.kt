package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class actuacio : Fragment(R.layout.fragment_actuacio) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddAct = requireView().findViewById<Button>(R.id.botoAfegirActuacio)

        btnAddAct.setOnClickListener{
            findNavController().navigate(R.id.action_actuacions_fragment_to_afegir_actuacio_fragment)
        }
    }
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<CardView>(R.id.cardViewActuacions).setOnClickListener {
            val action = actuacioDirections.actionActuacionsFragmentToInformacioActuacio()
            holder.itemView.findNavController().navigate(action)
        }
    }
}