package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.bergants.adapter.ActuacioRecyclerAdapter
import cat.copernic.bergants.adapter.ActuacioRecyclerAdapterUser
import cat.copernic.bergants.databinding.FragmentActuacioBinding
import cat.copernic.bergants.databinding.FragmentActuacioUsuariBinding
import cat.copernic.bergants.model.ActuacioModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Keep
class ActuacioUsuari : Fragment() {
    private var list_multable: MutableList<ActuacioModel> = ArrayList()

    private lateinit var binding: FragmentActuacioUsuariBinding

    private val myAdapter: ActuacioRecyclerAdapterUser = ActuacioRecyclerAdapterUser()

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    /**
     * La funció setupRecyclerView és responsable de configurar el RecyclerView.
     *
     * - si la llista "list_multable" està buida, es crida la funció "mostrarActuacions()".
     * - si la llista no està buida, el disseny de RecyclerView s'estableix en un LinearLayoutManager,
     * es crea un adaptador, "myAdapter", cridant a la funció "ActuacioRecyclerAdapter".
     * i passant el "list_multable" i l'activitat actual, i s'assigna al RecyclerView.
     * @param Cap
     * @return Cap
     */
    private fun setupRecyclerView() {
        if (list_multable.isEmpty()) {
            mostrarActuacions() //Executem la funció de suspensió
        } else {
            binding.recyclerActuacionsUsuari!!.setHasFixedSize(true)

            //indiquem que el RV es mostrarà en format llista
            binding.recyclerActuacionsUsuari!!.layoutManager = LinearLayoutManager(context)

            //generem el adapter
            myAdapter.ActuacioRecyclerAdapterUser(list_multable, requireActivity())
            //assignem el adapter al RV
            binding.recyclerActuacionsUsuari!!.adapter = myAdapter
        }
    }

    /**
     * Aquesta classe es crida quan es crea la vista del fragment.
     * Es configura el recyclerview i es configura un botó per anar a un altre fragment per afegir actuacions.
     *
     * @param view : Vista del fragment.
     * @param savedInstanceState : estat guardat de l'instancia del fragment.
     * @param binding : Objecte de l'enllaç de la vista del fragment.
     * @param setupRecyclerView : funció que s'encarrega de configurar el recyclerview.
     * @param botoAfegirActuacio : variable que guarda el botó d'afegir actuacions.
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //iniciem el recycler view
        setupRecyclerView()
    }

    /**
     * Aquesta classe es crida quan es crea la vista del fragment.
     * Es fa l'inflado del layout del fragment i es vincula el binding amb el layout.
     *
     * @param inflater : Objecte utilitzat per inflar la vista del fragment.
     * @param container : Contenidor on es posarà la vista del fragment.
     * @param savedInstanceState : estat guardat de l'instancia del fragment.
     * @param binding : Objecte de l'enllaç de la vista del fragment.
     *
     * @return Retorna la vista del fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //Inflem el layout
        binding = FragmentActuacioUsuariBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     *
     * Aquesta funció s'utilitza per mostrar les actuacions a la vista del reciclador.
     * Comença mostrant l'animació brillant per indicar que les dades s'estan carregant.
     * Després recupera les dades de la col·lecció Firebase Firestore anomenada "Actuacions" i
     * crea un objecte de la classe ActuacioModel amb el títol, la data i el lloc de l'actuació.
     * També comprova si el títol de l'actuació ja existeix a la llista, si no, l'afegeix a la llista.
     * Les dades es mostren al recyclerview un cop es carreguen.
     *
     * @param list_multable : Aquesta és la llista que s'utilitza per emmagatzemar els objectes de rendiment.
     * @param bd : aquesta és la instància de Firebase Firestore.
     * @param context : aquest és el context de l'activitat
     * @param myAdapter : aquest és l'adaptador per a la vista del reciclador
     * @param binding : aquest és l'objecte d'enllaç de vista per al fragment.
     *
     */

    private fun mostrarActuacions() {
        //Visibilitzem el shimmer per a fer l'animació de carrega abans de mostrar el recycler
        binding.shimmerViewRvActuacions.visibility = View.VISIBLE
        //Desactivem la visibilitat del recyclerNoticies mentre carreguen els items
        binding.recyclerActuacionsUsuari!!.visibility = View.GONE
        //Activem el shimmer per l'animació de carregar
        binding.shimmerViewRvActuacions.startShimmer()
        //Aquest codi utilitza la base de dades Firebase Firestore per recuperar una col·lecció de
        // documents anomenada "Actuacions". Per a cada document de la col·lecció, crea un objecte
        // de la classe ActuacioModel i assigna els valors dels camps "titolActuacio", "dataActuacio
        // " i "llocActuacio" a les propietats corresponents de l'objecte. A continuació, comprova si
        // la llista anomenada "list_multable" està buida. Si és així, afegeix l'objecte ActuacioModel
        // a la llista. Si no està buit, comprova si el títol de l'objecte ActuacioModel ja existeix a
        // la llista. Si no ho fa, s'afegeix a la llista, en cas contrari no s'afegeix. L'operació es fa
        // dins del IO Dispatcher i lifecycleScope.launch.
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
                    binding.recyclerActuacionsUsuari!!.layoutManager = LinearLayoutManager(context)
                    //generem el adapter
                    myAdapter.ActuacioRecyclerAdapterUser(list_multable, requireActivity())
                    //assignem el adapter al RV
                    binding.recyclerActuacionsUsuari!!.adapter = myAdapter
                    //ara que han carregat els items del recycler ja podem parar el shimmer
                    binding.shimmerViewRvActuacions.stopShimmer()
                    //també desactivem la visibilitat del shimmer
                    binding.shimmerViewRvActuacions.visibility = View.GONE
                    //activem la visibilitat del recycler, ja que l'haviem desactivat anteriorment
                    binding.recyclerActuacionsUsuari!!.visibility = View.VISIBLE
                }
            }
        }
    }
}