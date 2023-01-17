package cat.copernic.bergants

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Keep
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.plusAssign
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentNoticiaCanviBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.adapter.NoticiaRecyclerAdapter
import cat.copernic.bergants.model.NoticiaModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import hotchemi.android.rate.AppRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.stream.Collectors.toList

@Keep
class noticia_canvi : Fragment() {

    private var list_multable: MutableList<NoticiaModel> = ArrayList()
    private lateinit var binding: FragmentNoticiaCanviBinding
    private val myAdapter: NoticiaRecyclerAdapter = NoticiaRecyclerAdapter()
    private lateinit var auth: FirebaseAuth


    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    //Aquest codi defineix una funció anomenada "setupRecyclerView" que probablement s'utilitza per
    // configurar una vista de reciclador al fragment.
    //Primer comprova si el list_multable està buit o no. Si està buit, crida a la funció
    // mostrarNoticies() que probablement carrega les dades per a la vista del reciclador. Si no està
    // buit, passa a configurar la vista del reciclador.
    //Estableix la vista del reciclador perquè tingui una mida fixa trucant a setHasFixedSize(true) a
    // la vista del reciclador.
    //Estableix el gestor de disseny de la vista del reciclador en un LinearLayoutManager utilitzant
    // el context del fragment.
    //Crea una instància de la classe NoticiesRecyclerAdapter i l'assigna a la vista del reciclador
    // cridant a myAdapter.NoticiesRecyclerAdapter(list_multable, requireActivity()) i l'assigna a
    // la propietat d'adaptador de la vista del reciclador.
    //S'utilitza per configurar la vista del reciclador, com ara crear un adaptador i configurar-lo
    // a la vista del reciclador per mostrar una llista de notícies.

    /**

    Aquesta funció configura el RecyclerView per mostrar la llista de noticies.
    Si la llista de noticies està buida, es crida la funció per mostrar les noticies.
    En cas contrari, es configura el RecyclerView per mostrar-lo en format llista,
    es crea l'adapter i es l'assigna al RecyclerView.
     */
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

    /**

    Aquesta funció s'executa quan la vista es crea. Es crea el binding per al fragment i es retorna la vista arrel.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //Inflem el layout
        binding = FragmentNoticiaCanviBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**

    Aquesta funció s'executa quan la vista s'ha creat. Es configura el RecyclerView per mostrar la llista de noticies.
    Es defineix un diàleg per fer una ressenya de l'aplicació, que es mostra el primer dia d'instal·lació o després de 2 inici de sessió.
    Es defineix un listener per al botó "Afegir noticia", que navega cap al fragment per afegir una noticia.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //iniciem el recycler view
        setupRecyclerView()
        /**Diàleg de ressenya
         * Mostra el diàleg el primer dia d'instal·lació amb setInstallDays(0)
         * Un cop l'usuari obre l'aplicació 2 cops aparèix el diàleg setLaunchTimes(2)
         * Si l'usuari selecciona recordar més tard el diàleg apareixerà el dia següent de seleccionar l'opció.
         */
        AppRate.with(requireActivity()).setInstallDays(0).setLaunchTimes(2).setRemindInterval(1).monitor()
        //Es mostra el diàleg si es compleix alguna de les condicions
        AppRate.showRateDialogIfMeetsConditions(requireActivity())
        //Aquesta linia és només per provar el dialeg
        //Mostra sempre el diàleg quan iniciem l'app, aquesta línia es només per fer proves, serà borrada un cop demostrat un funciomanet correcte.
        //AppRate.with(requireActivity()).showRateDialog(requireActivity())
        //botó que s'encarrega de dirigir-nos al fragment encarregat d'afegir noticies
        binding.botoAfegirNoticia.setOnClickListener {
            val action = noticia_canviDirections.actionNoticiaFragmentToAfegirNoticia()
            findNavController().navigate(action)
        }
    }

    /**

    Aquesta funció mostra les noticies en el RecyclerView. Es mostra una animació de càrrega mentre es carreguen les noticies des de Firestore.
    Utilitza la rutina lifecycleScope.launch per fer una sol·licitud de xarxa a Firestore per obtenir una col·lecció de documents anomenada "Noticies" en un fil de fons.
    L'addOnSuccessListener es crida quan la sol·licitud té èxit i itera els documents retornats i crea un objecte NoticiaModel per a cada document, utilitzant els camps "titolNoticia", "contingutNoticia" i "dataNoticia" del document.
    A continuació, comprova si el list_multable està buit o no. Si està buit, afegeix el wallItem a la llista. Si no està buit, itera per la llista_multable i comprova si ja hi ha una notícia amb el mateix títol, si no, l'afegeix a la llista.
     */
    private fun mostrarNoticies() {
        //Visibilitzem el shimmer per a fer l'animació de carrega abans de mostrar el recycler
        binding.shimmerViewRvNoticies.visibility = View.VISIBLE
        //Desactivem la visibilitat del recyclerNoticies mentre carreguen els items
        binding.recyclerNoticies.visibility = View.GONE
        //Activem el shimmer per l'animació de carregar
        binding.shimmerViewRvNoticies.startShimmer()
        //Aquest codi utilitza la rutina lifecycleScope.launch per fer una sol·licitud de xarxa a
        // Firestore per obtenir una col·lecció de documents anomenada "Noticies" en un fil de fons
        // mitjançant Dispatchers.IO.
        //L'addOnSuccessListener es crida quan la sol·licitud té èxit i itera els documents retornats
        // i crea un objecte NoticiaModel per a cada document, utilitzant els camps "titolNoticia",
        // "contingutNoticia" i "dataNoticia" del document.
        //A continuació, comprova si el list_multable està buit o no. Si està buit, afegeix el wallItem
        // a la llista. Si no està buit, itera per la llista_multable i comprova si ja hi ha una notícia
        // amb el mateix títol, si no, l'afegeix a la llista.
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

                    //ara que han carregat els items del recycler ja podem parar el shimmer
                    binding.shimmerViewRvNoticies.stopShimmer()
                    //també desactivem la visibilitat del shimmer
                    binding.shimmerViewRvNoticies.visibility = View.GONE
                    //activem la visibilitat del recycler, ja que l'haviem desactivat anteriorment
                    binding.recyclerNoticies.visibility = View.VISIBLE
                }
            }
        }
    }
}