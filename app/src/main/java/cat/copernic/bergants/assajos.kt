package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.bergants.adapter.AssaigRecyclerAdapter
import cat.copernic.bergants.databinding.FragmentAssajosBinding
import cat.copernic.bergants.model.ActuacioModel
import cat.copernic.bergants.model.AssaigModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class assajos : Fragment() {

    private lateinit var binding: FragmentAssajosBinding

    private val myAdapter: AssaigRecyclerAdapter = AssaigRecyclerAdapter()

    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    private fun setupRecyclerView(){
        if (getAssajos().isEmpty()) {
            mostrarAssajos()
        }else {
        binding.recyclerAssajos.setHasFixedSize(true)

        //indiquem que el RV es mostrarà en format llista
        binding.recyclerAssajos.layoutManager = LinearLayoutManager(context)

        //generem el adapter
        myAdapter.AssaigRecyclerAdapter(getAssajos(),requireActivity())
        //assignem el adapter al RV
        binding.recyclerAssajos.adapter = myAdapter
        }
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

    private fun getAssajos():MutableList<AssaigModel>{
        val assajos: MutableList<AssaigModel> = arrayListOf()
        assajos.add(AssaigModel("ASSAIG ESPECIAL DE LA MERCÈ","DISSABTE 23 de Setembre del 2022 20:00h","Local de la colla"))
        assajos.add(AssaigModel("ASSAIG GENERAL","DIMECRES 28 de Setembre del 2022 20:00h","Local de la colla"))
        assajos.add(AssaigModel("ASSAIG GENERAL","DIVENDRES 30 de Setembre del 2022 20:00h","Local de la colla"))
        assajos.add(AssaigModel("ASSAIG GENERAL","DILLUNS 3 d'Octubre del 2022 20:00h","Local de la colla"))

        return assajos
    }

    private fun mostrarAssajos() {

        lifecycleScope.launch {
            bd.collection("Noticies").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val wallItem = AssaigModel(
                        title = document["titolAssaig"].toString(),
                        data = document["dataAssaig"].toString(),
                        lloc = document["llocAssaig"].toString()
                    )
                    if (getAssajos().isEmpty()) {
                        getAssajos().add(wallItem)
                    } else {
                        for (i in getAssajos()) {
                            if (wallItem.titolAssaig != i.titolAssaig) {
                                getAssajos().add(wallItem)
                            }
                        }
                    }
                }
                //indiquem que el RV es mostrarà en format llista
                binding.recyclerAssajos.layoutManager = LinearLayoutManager(context)

                //generem el adapter
                myAdapter.AssaigRecyclerAdapter(getAssajos(),requireActivity())
                //assignem el adapter al RV
                binding.recyclerAssajos.adapter = myAdapter
            }
        }
    }
}