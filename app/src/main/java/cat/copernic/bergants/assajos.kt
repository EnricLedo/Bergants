package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentViewHolder

class assajos : Fragment(R.layout.fragment_assajos) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddAss = requireView().findViewById<Button>(R.id.botoAfegirAssaig)
        val imgInfoAss = requireView().findViewById<CardView>(R.id.cardViewAssajosInterior)

        btnAddAss.setOnClickListener{
            findNavController().navigate(R.id.action_assajos_fragment_to_afegir_assaig_fragment)
        }

        /**
        imgInfoAss.setOnClickListener{
            findNavController().navigate(R.id.action_assajos_fragment_to_informacioAssaig)
        }
        */

        fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.findViewById<CardView>(R.id.cardViewAssajos).setOnClickListener {
                val action = assajosDirections.actionAssajosFragmentToInformacioAssaig()
                holder.itemView.findNavController().navigate(action)
            }
        }

    }
}