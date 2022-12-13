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
import cat.copernic.bergants.adapter.MembreRecyclerAdapter
import cat.copernic.bergants.databinding.FragmentMembresBinding
import cat.copernic.bergants.databinding.FragmentNoticiaCanviBinding
import cat.copernic.bergants.model.AssaigModel
import cat.copernic.bergants.model.MembreModel
import cat.copernic.bergants.model.NoticiaModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class membres : Fragment() {
    private lateinit var binding: FragmentMembresBinding
    private var list_multable: MutableList<MembreModel> = ArrayList()

    private val myAdapter: MembreRecyclerAdapter = MembreRecyclerAdapter()
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    private fun setupRecyclerView() {
        /*if (getMembres().isEmpty()) {
            mostrarMembres()
        }else {*/
        binding.recyclerMembres.setHasFixedSize(true)


        //indiquem que el RV es mostrarà en format llista
        binding.recyclerMembres.layoutManager = LinearLayoutManager(context)

        //generem el adapter
        myAdapter.MembreRecyclerAdapter(getMembres(),requireActivity())
        //assignem el adapter al RV
        binding.recyclerMembres.adapter = myAdapter
        //}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddMem = requireView().findViewById<Button>(R.id.botoAfegirMembre)

        btnAddMem.setOnClickListener{
            findNavController().navigate(R.id.action_membres_fragment_to_afegirMembre)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMembresBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun getMembres():MutableList<Membre>{
        val membres: MutableList<Membre> = arrayListOf()
        membres.add(Membre("Enric Ledo Muntal", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Marc Fernández González", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fbelenesteban%40gmail.com?alt=media&token=3b793527-300d-4ed5-8989-981b9d596ff7"))
        membres.add(Membre("Joan Galindo Palacio", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fmarcfernandez%40gmail.com?alt=media&token=ec1dc7f0-f1b3-4047-aa1d-e49353347353"))
        membres.add(Membre("Belen Esteban", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fjoangalindo250%40gmail.com?alt=media&token=4a7ffd25-1a03-42ee-a359-f9bb024f8f3f"))
        membres.add(Membre("Mila Ximenez", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Pablo Motos", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fricard%40gmail.com?alt=media&token=1e47679d-2f0b-4188-bf3b-fa514d0130bb"))
        membres.add(Membre("Alfons Lopez Navarro", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Adri Navarro González", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fmarcfernandez%40gmail.com?alt=media&token=ec1dc7f0-f1b3-4047-aa1d-e49353347353"))

        return membres

    }

    private fun mostrarMembres() {
        /**lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Membres").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val wallItem = MembreModel(
                            nomMembre = document["nomMembre"].toString(),
                            malnom = document["nomMembre"].toString(),
                            alcadaEspatlles = document["nomMembre"].toString(),
                            alcadaMans = document["nomMembre"].toString(),
                            correuMembre = document["nomMembre"].toString(),
                            adrecaMembre = document["nomMembre"].toString(),
                            telefonMembre = document["nomMembre"].toString(),
                            rolMembre = document["nomMembre"].toString(),
                            altaMembre = document["nomMembre"].toString()
                        )
                        if (list_multable.isEmpty()) {
                            list_multable.add(wallItem)
                        } else {
                            var contador = 0
                            for (i in list_multable) {
                                if (wallItem.nomMembre == i.nomMembre) {
                                    contador++
                                }
                            }
                            if(contador <1){
                                list_multable.add(wallItem)
                            }
                        }
                    }
                    //indiquem que el RV es mostrarà en format llista
                    binding.recyclerMembres.layoutManager = LinearLayoutManager(context)

                    //generem el adapter
                    myAdapter.MembreRecyclerAdapter(list_multable,requireActivity())
                    //assignem el adapter al RV
                    binding.recyclerMembres.adapter = myAdapter
                }
            }
        }*/

        /**lifecycleScope.launch {
            bd.collection("Noticies").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val wallItem = MembreModel(
                        nomMembre = document["nomMembre"].toString(),
                        malnom = document["malnom"].toString(),
                        alcadaEspatlles = document["alcadaEspatlles"].toString(),
                        alcadaMans = document["alcadaMans"].toString(),
                        correuMembre = document["correuMembre"].toString(),
                        adrecaMembre = document["adrecaMembre"].toString(),
                        telefonMembre = document["telefonMembre"].toString(),
                        rolMembre = document["rolMembre"].toString()
                    )
                    if (getMembres().isEmpty()) {
                        getMembres().add(wallItem)
                    } else {
                        for (i in getMembres()) {
                            if (wallItem.nomMembre != i.nomMembre) {
                                getMembres().add(wallItem)
                            }
                        }
                    }
                }
                //indiquem que el RV es mostrarà en format llista
                binding.recyclerMembres.layoutManager = LinearLayoutManager(context)

                //generem el adapter
                myAdapter.MembreRecyclerAdapter(getMembres(),requireActivity())
                //assignem el adapter al RV
                binding.recyclerMembres.adapter = myAdapter
            }
        }*/
    }

}