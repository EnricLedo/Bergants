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
import cat.copernic.bergants.adapter.ActuacioRecyclerAdapter
import cat.copernic.bergants.databinding.FragmentActuacioBinding
import cat.copernic.bergants.model.ActuacioModel
import cat.copernic.bergants.model.AssaigModel
import cat.copernic.bergants.model.NoticiaModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class actuacio : Fragment() {

    private var list_multable: MutableList<ActuacioModel> = ArrayList()

    private lateinit var binding: FragmentActuacioBinding

    private val myAdapter: ActuacioRecyclerAdapter = ActuacioRecyclerAdapter()

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    private fun setupRecyclerView() {
        if (list_multable.isEmpty()) {
            mostrarActuacions()
        } else {
            binding.recyclerActuacions.setHasFixedSize(true)

            //indiquem que el RV es mostrarà en format llista
            binding.recyclerActuacions.layoutManager = LinearLayoutManager(context)

            //generem el adapter
            myAdapter.ActuacioRecyclerAdapter(list_multable, requireActivity())
            //assignem el adapter al RV
            binding.recyclerActuacions.adapter = myAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddAct = requireView().findViewById<Button>(R.id.botoAfegirActuacio)

        btnAddAct.setOnClickListener {
            findNavController().navigate(R.id.action_actuacions_fragment_to_afegir_actuacio_fragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActuacioBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }


    private fun mostrarActuacions() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Actuacions").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val wallItem = ActuacioModel(
                            title = document["titolActuacio"].toString(),
                            data = document["dataActuacio"].toString(),
                            lloc = document["llocActuacio"].toString()
                        )
                        if (list_multable.isEmpty()) {
                            list_multable.add(wallItem)
                        } else {
                            var contador = 0
                            for (i in list_multable) {
                                if (wallItem.title == i.title) {
                                    contador++
                                }
                            }
                            if(contador <1){
                                list_multable.add(wallItem)
                            }
                        }
                    }
                    binding.recyclerActuacions.layoutManager = LinearLayoutManager(context)
                    //generem el adapter
                    myAdapter.ActuacioRecyclerAdapter(list_multable, requireActivity())
                    //assignem el adapter al RV
                    binding.recyclerActuacions.adapter = myAdapter
                }
            }
        }
    }
}