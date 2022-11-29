package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.bergants.adapter.ActuacioRecyclerAdapter
import cat.copernic.bergants.databinding.FragmentActuacioBinding
import cat.copernic.bergants.model.ActuacioModel

class actuacio : Fragment() {

    private lateinit var binding: FragmentActuacioBinding

    private val myAdapter: ActuacioRecyclerAdapter = ActuacioRecyclerAdapter()

    private fun setupRecyclerView(){
        binding.recyclerActuacions.setHasFixedSize(true)

        //indiquem que el RV es mostrarà en format llista
        binding.recyclerActuacions.layoutManager = LinearLayoutManager(context)

        //generem el adapter
        myAdapter.ActuacioRecyclerAdapter(getActuacions(),requireActivity())
        //assignem el adapter al RV
        binding.recyclerActuacions.adapter = myAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddAct = requireView().findViewById<Button>(R.id.botoAfegirActuacio)

        btnAddAct.setOnClickListener{
            findNavController().navigate(R.id.action_actuacions_fragment_to_afegir_actuacio_fragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentActuacioBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun getActuacions():MutableList<ActuacioModel>{
        val actuacions: MutableList<ActuacioModel> = arrayListOf()
        actuacions.add(ActuacioModel("FM DE FOSTON","DISSABTE 24 de Setembre de 2022 18:00h","Plaça de l'Ajuntament - Calella"))
        actuacions.add(ActuacioModel("LA MERCÈ","DISSABTE 25 de Setembre 18:00h","Pl. Sant Jaume"))
        actuacions.add(ActuacioModel("ACTUACIÓ COMERCIAL HOTEL LA MOLA","DIJOUS 29 de Setembre del 2022 18:00h","Hotel la Mola"))

        return actuacions
    }
}