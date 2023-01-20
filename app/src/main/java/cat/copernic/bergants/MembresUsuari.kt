package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Keep
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.bergants.adapter.MembreRecyclerAdapter
import cat.copernic.bergants.databinding.FragmentMembresBinding
import cat.copernic.bergants.databinding.FragmentMembresUsuariBinding
import cat.copernic.bergants.model.MembreModel
import com.google.firebase.firestore.FirebaseFirestore

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
        /*if (getMembres().isEmpty()) {
            mostrarMembres()
        }else {*/
        binding.recyclerMembres.setHasFixedSize(true)


        //indiquem que el RV es mostrarà en format llista
        binding.recyclerMembres.layoutManager = LinearLayoutManager(context)

        //generem el adapter
        myAdapter.MembreRecyclerAdapter(list_multable,requireActivity())
        //assignem el adapter al RV
        binding.recyclerMembres.adapter = myAdapter
        //}
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
}