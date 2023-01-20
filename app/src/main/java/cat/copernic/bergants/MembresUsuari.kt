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
import cat.copernic.bergants.databinding.FragmentMembresUsuariBinding
import cat.copernic.bergants.model.MembreModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Keep
class MembresUsuari : Fragment() {
    private lateinit var binding: FragmentMembresUsuariBinding
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
        binding = FragmentMembresUsuariBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun mostrarMembres() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Membres").get().addOnSuccessListener { documents ->
                    for (document in documents) {
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
                            foto= "https://firebasestorage.googleapis.com/v0/b/bergants-dam.appspot.com/o/imatge%2Fmembre%2Fadmin%40gmail.com?alt=media&token=fa014a1f-1fc5-4d67-9531-b7412a906b1a"
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