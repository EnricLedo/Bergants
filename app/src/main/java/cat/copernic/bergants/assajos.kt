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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentViewHolder
import cat.copernic.bergants.adapter.AssaigRecyclerAdapter
import cat.copernic.bergants.databinding.FragmentAssajosBinding
import cat.copernic.bergants.model.Assaig

class assajos : Fragment() {

    private lateinit var binding: FragmentAssajosBinding

    private val myAdapter: AssaigRecyclerAdapter = AssaigRecyclerAdapter()

    private fun setupRecyclerView(){
        binding.recyclerAssajos.setHasFixedSize(true)

        //indiquem que el RV es mostrarà en format llista
        binding.recyclerAssajos.layoutManager = LinearLayoutManager(context)

        //generem el adapter
        myAdapter.AssaigRecyclerAdapter(getAssajos(),requireActivity())
        //assignem el adapter al RV
        binding.recyclerAssajos.adapter = myAdapter
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddAss = requireView().findViewById<Button>(R.id.botoAfegirAssaig)

        btnAddAss.setOnClickListener {
            findNavController().navigate(R.id.action_assajos_fragment_to_afegir_assaig_fragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAssajosBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun getAssajos():MutableList<Assaig>{
        val assajos: MutableList<Assaig> = arrayListOf()
        assajos.add(Assaig("ASSAIG ESPECIAL DE LA MERCÈ","DISSABTE 23 de Setembre del 2022","20:00h Local de la colla","SENSE RESPOSTA"))
        assajos.add(Assaig("ASSAIG GENERAL","DIMECRES 28 de Setembre del 2022","20:00h Local de la colla","SENSE RESPOSTA"))
        assajos.add(Assaig("ASSAIG GENERAL","DIVENDRES 30 de Setembre del 2022","20:00h Local de la colla","SENSE RESPOSTA"))
        assajos.add(Assaig("ASSAIG GENERAL","DILLUNS 3 d'Octubre del 2022","20:00h Local de la colla","SENSE RESPOSTA"))

        return assajos
    }
}