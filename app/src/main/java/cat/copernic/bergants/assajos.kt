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
import cat.copernic.bergants.adapter.AssaigRecyclerAdapter
import cat.copernic.bergants.databinding.FragmentAssajosBinding
import cat.copernic.bergants.model.ActuacioModel
import cat.copernic.bergants.model.AssaigModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Keep
class assajos : Fragment() {

    private lateinit var binding: FragmentAssajosBinding

    private var list_multable: MutableList<AssaigModel> = ArrayList()

    private val myAdapter: AssaigRecyclerAdapter = AssaigRecyclerAdapter()

    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    /**

    Aquesta funció configura i mostra un RecyclerView amb una llista d'assajos. Si la llista està buida, crida a la funció "mostrarAssajos()"
    per obtenir les dades i actualitzar la llista. Utilitza un adapter personalitzat per mostrar les dades de manera eficient.
     */
    private fun setupRecyclerView() {
        if (list_multable.isEmpty()) {
            mostrarAssajos() //Executem la funció de suspensió
        } else {
            binding.recyclerAssajos.setHasFixedSize(true)

            //indiquem que el RV es mostrarà en format llista
            binding.recyclerAssajos.layoutManager = LinearLayoutManager(context)

            //generem el adapter
            myAdapter.AssaigRecyclerAdapter(list_multable, requireActivity())
            //assignem el adapter al RV
            binding.recyclerAssajos.adapter = myAdapter
        }
    }

    /**

    Aquest mètode s'executa quan la vista s'ha creat. Inicia el RecyclerView mitjançant la funció "setupRecyclerView()"
    i configura un onClickListener per al botó "botoAfegirAssaig", que navega l'usuari al fragment encarregat d'afegir assajos.
    @param view: Vista associada al fragment.
    @param savedInstanceState: Bundle amb l'estat guardat anteriorment de la vista.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //iniciem el recycler view
        setupRecyclerView()
        //botó que s'encarrega de dirigir-nos al fragment encarregat d'afegir assajos
        binding.botoAfegirAssaig.setOnClickListener {
            val action = assajosDirections.actionAssajosFragmentToAfegirAssaigFragment()
            findNavController().navigate(action)
        }
    }

    /**

    Aquest mètode s'executa quan la vista del fragment es crea. S'utilitza per inflar el layout associat al fragment
    i assignar-lo a una variable de binding.
    @param inflater: LayoutInflater per inflar el layout.
    @param container: Contenidor del layout.
    @param savedInstanceState: Bundle amb l'estat guardat anteriorment de la vista.
    @return Retorna la vista del layout inflada.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //Inflem el layout
        binding = FragmentAssajosBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**

    Aquesta funció es connecta a la base de dades Firestore, recupera les dades dels assajos i les mostra a un RecyclerView mitjançant un adapter personalitzat.
    Mostra una animació de carrega mentre les dades es recuperen de la base de dades.
     */
    private fun mostrarAssajos() {
        //Visibilitzem el shimmer per a fer l'animació de carrega abans de mostrar el recycler
        binding.shimmerViewRvAssajos.visibility = View.VISIBLE
        //Desactivem la visibilitat del recyclerNoticies mentre carreguen els items
        binding.recyclerAssajos.visibility = View.GONE
        //Activem el shimmer per l'animació de carregar
        binding.shimmerViewRvAssajos.startShimmer()
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                bd.collection("Assajos").get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val wallItem = AssaigModel(
                            title = document["titolAssaig"].toString(),
                            data = document["dataAssaig"].toString(),
                            lloc = document["llocAssaig"].toString()
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
                    //indiquem que el RV es mostrarà en format llista
                    binding.recyclerAssajos.layoutManager = LinearLayoutManager(context)

                    //generem el adapter
                    myAdapter.AssaigRecyclerAdapter(list_multable, requireActivity())
                    //assignem el adapter al RV
                    binding.recyclerAssajos.adapter = myAdapter
                    //ara que han carregat els items del recycler ja podem parar el shimmer
                    binding.shimmerViewRvAssajos.stopShimmer()
                    //també desactivem la visibilitat del shimmer
                    binding.shimmerViewRvAssajos.visibility = View.GONE
                    //activem la visibilitat del recycler, ja que l'haviem desactivat anteriorment
                    binding.recyclerAssajos.visibility = View.VISIBLE
                }
            }
        }
    }
}