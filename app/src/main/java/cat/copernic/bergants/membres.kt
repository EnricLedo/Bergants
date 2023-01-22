package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Keep
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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Keep
class membres : Fragment() {
    private lateinit var binding: FragmentMembresBinding
    private var list_multable: MutableList<MembreModel> = ArrayList()

    private val myAdapter: MembreRecyclerAdapter = MembreRecyclerAdapter()
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    /**

    Aquesta funció configura el RecyclerView per mostrar la llista de membres.
    Si la llista de membres està buida, es crida la funció per mostrar els membres.
    En cas contrari, es configura el RecyclerView per mostrar-lo en format llista,
    es crea l'adapter i es l'assigna al RecyclerView.
     */
    private fun setupRecyclerView() {
        if (list_multable.isEmpty()) {
            mostrarMembres() //Executem la funció de suspensió
        } else {
            binding.recyclerMembres.setHasFixedSize(true)

            //indiquem que el RV es mostrarà en format llista
            binding.recyclerMembres.layoutManager = LinearLayoutManager(context)

            //generem el adapter
            myAdapter.MembreRecyclerAdapter(list_multable,requireActivity())
            //assignem el adapter al RV
            binding.recyclerMembres.adapter = myAdapter
            //}

        }
    }

    //Aquest codi es troba en el mètode onViewCreated d'un fragment a Kotlin.
    //Primer crida al mètode onViewCreated de la superclasse, després recupera una referència d'un
    // botó en el disseny trucant al mètode findViewById a la vista que retorna la funció requireView()
    // i l'assigna a la variable btnAddMem.
    //Estableix un onClickListener per al botó, quan es fa clic, navegarà a un fragment diferent
    // trucant al mètode de navegació a findNavController() amb l'argument
    // action_membres_fragment_to_afegirMembre. Això navegarà l'usuari fins al fragment AfegirMembre.

    /**

    Aquesta funció s'executa quan la vista s'ha creat. Es defineix un listener per al botó "Afegir membre",
    que navega cap a la pantalla per afegir un membre.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddMem = requireView().findViewById<Button>(R.id.botoAfegirMembre)

        btnAddMem.setOnClickListener{
            findNavController().navigate(R.id.action_membres_fragment_to_afegirMembre)
        }
    }

    //Aquest codi es troba al mètode onCreateView d'un fragment a Kotlin.
    //Primer crea una instància de la classe FragmentMembresBinding cridant-hi el mètode inflate,
    // passant com a arguments el disseny inflater, container i false.
    //A continuació, crida al mètode setupRecyclerView(), aquest mètode probablement configura la
    // vista del reciclador amb les dades, com ara crear un adaptador i configurar-lo a la vista del
    // reciclador.
    //Retorna la vista arrel de l'objecte d'enllaç.
    //S'utilitza per inflar el disseny del fragment i configurar la vista del reciclador per mostrar
    // una llista de membres.

    /**

    Aquesta funció s'executa quan la vista es crea. Crea el binding per al fragment i configura el RecyclerView per mostrar la llista de membres.
    Es retorna la vista arrel.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMembresBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun getMembres():MutableList<Membre>{
        val membres: MutableList<Membre> = arrayListOf()
        //membres.add(Membre("Enric Ledo Muntal", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        //membres.add(Membre("Marc Fernández González", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fbelenesteban%40gmail.com?alt=media&token=3b793527-300d-4ed5-8989-981b9d596ff7"))
        //membres.add(Membre("Joan Galindo Palacio", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fmarcfernandez%40gmail.com?alt=media&token=ec1dc7f0-f1b3-4047-aa1d-e49353347353"))
        //membres.add(Membre("Belen Esteban", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fjoangalindo250%40gmail.com?alt=media&token=4a7ffd25-1a03-42ee-a359-f9bb024f8f3f"))
        //membres.add(Membre("Mila Ximenez", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        //membres.add(Membre("Pablo Motos", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fricard%40gmail.com?alt=media&token=1e47679d-2f0b-4188-bf3b-fa514d0130bb"))
        //membres.add(Membre("Alfons Lopez Navarro", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        //membres.add(Membre("Adri Navarro González", "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fmarcfernandez%40gmail.com?alt=media&token=ec1dc7f0-f1b3-4047-aa1d-e49353347353"))

        return membres

    }

    private fun mostrarMembres() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Membres").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val storageRef = Firebase.storage.reference.child("imatge/membre/"+document["correuMembre"].toString())
                        val urlTask = storageRef.downloadUrl
                        urlTask.addOnSuccessListener { url ->
                            // Do something with the download URL
                            val wallItem = MembreModel(
                                name = document["nomMembre"].toString(),
                                malname = document["malnom"].toString(),
                                espatlles = document["alcadaEspatlles"].toString(),
                                mans = document["alcadaMans"].toString(),
                                email = document["correuMembre"].toString(),
                                adress = document["adrecaMembre"].toString(),
                                telefon = document["telefonMembre"].toString(),
                                rol = document["rolMembre"].toString(),
                                date = document["altaMembre"].toString(),
                                admin= true,
                                foto= url.toString()
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
        }
    }
    }
}