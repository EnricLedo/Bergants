package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.plusAssign
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentNoticiaCanviBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.adapter.NoticiaRecyclerAdapter
import cat.copernic.bergants.model.NoticiaModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.stream.Collectors.toList


class noticia_canvi : Fragment() {

    private var list_multable: MutableList<NoticiaModel> = ArrayList()

    private lateinit var binding: FragmentNoticiaCanviBinding

    private val myAdapter: NoticiaRecyclerAdapter = NoticiaRecyclerAdapter()
    private lateinit var auth: FirebaseAuth


    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    private fun setupRecyclerView() {

        if (list_multable.isEmpty()) {
            mostrarNoticies() //Executem la funció de suspensió

        } else {
            binding.recyclerNoticies.setHasFixedSize(true)
            //indiquem que el RV es mostrarà en format llista
            binding.recyclerNoticies.layoutManager = LinearLayoutManager(context)
            //generem el adapter
            myAdapter.NoticiesRecyclerAdapter(list_multable, requireActivity())
            //assignem el adapter al RV
            binding.recyclerNoticies.adapter = myAdapter
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddNot = requireView().findViewById<Button>(R.id.botoAfegirNoticia)
        mostrarNoticies()
        setupRecyclerView()

        btnAddNot.setOnClickListener {
            findNavController().navigate(R.id.action_noticia_fragment_to_afegirNoticia)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoticiaCanviBinding.inflate(inflater, container, false)

        return binding.root
    }


    private fun mostrarNoticies() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Noticies").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val wallItem = NoticiaModel(
                            title = document["titolNoticia"].toString(),
                            content = document["contingutNoticia"].toString(),
                            date = document["dataNoticia"].toString()
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
                    binding.recyclerNoticies.layoutManager = LinearLayoutManager(context)
                    //generem el adapter
                    myAdapter.NoticiesRecyclerAdapter(list_multable, requireActivity())
                    //assignem el adapter al RV
                    binding.recyclerNoticies.adapter = myAdapter
                }
            }
        }
    }
}