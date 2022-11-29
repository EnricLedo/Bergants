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
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.stream.Collectors.toList


class noticia_canvi : Fragment() {

    private lateinit var binding: FragmentNoticiaCanviBinding

    private val myAdapter: NoticiaRecyclerAdapter = NoticiaRecyclerAdapter()

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    private fun setupRecyclerView() {
        binding.recyclerNoticies.setHasFixedSize(true)


        //indiquem que el RV es mostrarà en format llista
        binding.recyclerNoticies.layoutManager = LinearLayoutManager(context)

        //generem el adapter
        myAdapter.NoticiesRecyclerAdapter(getNoticies(),requireActivity())
        //assignem el adapter al RV
        binding.recyclerNoticies.adapter = myAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddNot = requireView().findViewById<Button>(R.id.botoAfegirNoticia)
        mostrarNoticies()
        btnAddNot.setOnClickListener{
            findNavController().navigate(R.id.action_noticia_fragment_to_afegirNoticia)
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNoticiaCanviBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }


    private fun getNoticies():MutableList<NoticiaModel>{
        val noticies: MutableList<NoticiaModel> = arrayListOf()

        return noticies
    }

    private fun mostrarNoticies() {

        lifecycleScope.launch {
            bd.collection("Noticies").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val wallItem = NoticiaModel(
                        title = document["titolNoticia"].toString(),
                        content = document["contingutNoticia"].toString(),
                        date = document["dateNoticia"].toString()
                    )
                    if (getNoticies().isEmpty()) {
                        getNoticies().add(wallItem)
                    } else {
                        for (i in getNoticies()) {
                            if (wallItem.titolNoticia != i.titolNoticia) {
                                getNoticies().add(wallItem)
                            }
                        }
                    }
                }
            }
        }
    }

}