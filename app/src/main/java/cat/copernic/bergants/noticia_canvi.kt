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
import cat.copernic.bergants.DataLists.NoticiaModelList
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

    private lateinit var binding: FragmentNoticiaCanviBinding

    private val myAdapter: NoticiaRecyclerAdapter = NoticiaRecyclerAdapter()


    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    private fun setupRecyclerView() {

        if (NoticiaModelList.NoticiaRV.isEmpty()) {
            lifecycleScope.launch{ //mentres duri el cicle de vida de l'Activity s'executarà...
                /*ESTEM AL FIL PRINCIPAL*/
                //Mostrem el resultat en el TextView departaments, assignant aquest resultat al TextView on el volem mostrar
                //(departaments.text).
                //Com mostrarDepartaments() és una consulta a una base de dades, canviarem de fil, i l'executarem en el fil IO (withContext(Dispatchers.IO)),
                //quedant bloquejades les operacions d'entrades i sortides fins que no finalitzi l'execució de la corrutina, però d'aquesta manera el fil principal
                //quedarà lliure perquè continuï executant altres tasques.
                mostrarNoticies() //Executem la funció de suspensió
            }

        } else {
            binding.recyclerNoticies.setHasFixedSize(true)
            //indiquem que el RV es mostrarà en format llista
            binding.recyclerNoticies.layoutManager = LinearLayoutManager(context)
            //generem el adapter
            myAdapter.NoticiesRecyclerAdapter(NoticiaModelList.NoticiaRV, requireActivity())
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

    private var ArrayListOnGuardarLesNoticies = ArrayList<NoticiaModel>()

    private fun mostrarNoticies() {
            bd.collection("Noticies").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val wallItem = NoticiaModel(
                        titolNoticia = document["titolNoticia"].toString(),
                        contingutNoticia = document["contingutNoticia"].toString(),
                        dataNoticia = document["dateNoticia"].toString()
                    )
                    if (NoticiaModelList.NoticiaRV.isEmpty()) {
                        NoticiaModelList.NoticiaRV.add(wallItem)
                    } else {
                        for (i in NoticiaModelList.NoticiaRV) {
                            if (wallItem.titolNoticia != i.titolNoticia) {
                                ArrayListOnGuardarLesNoticies.add(wallItem)
                            }
                        }
                    }
                }
                //indiquem que el RV es mostrarà en format llista
                binding.recyclerNoticies.layoutManager = LinearLayoutManager(context)

                //generem l'adapter
                myAdapter.NoticiesRecyclerAdapter(ArrayListOnGuardarLesNoticies, requireActivity())
                //assignem el adapter al RV
                binding.recyclerNoticies.adapter = myAdapter
            }
    }

}