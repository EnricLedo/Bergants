package cat.copernic.bergants

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.bergants.databinding.FragmentInformacioActuacioBinding
import cat.copernic.bergants.databinding.FragmentInformacioAssaigBinding
import cat.copernic.bergants.model.ActuacioModel
import cat.copernic.bergants.model.AssaigModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@Keep
class InformacioActuacio : Fragment() {
    private lateinit var binding: FragmentInformacioActuacioBinding

    private lateinit var titolActuacio: TextView
    private lateinit var dataActuacio: TextView
    private lateinit var ubicacioActuacio: TextView
    private lateinit var llocActuacio: TextView
    private lateinit var actuacio: ActuacioModel
    private lateinit var maps_exemple: ImageButton

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd = FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    private val args by navArgs<InformacioActuacioArgs>()


    /**

    Aquesta classe és la encarregada de mostrar la informació de l'actuació seleccionada en la pantalla d'informació de l'actuació.
    Utilitza un binding per associar les dades de l'actuació amb els elements de la vista.
    @param inflater permet inflar la vista
    @param container el container on es mostrarà la vista
    @param savedInstanceState estat guardat anteriorment
    @return la vista inflada i amb les dades associades
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformacioActuacioBinding.inflate(inflater, container, false)
        binding.TitolActuacio.setText(args.currentActuacio.titolActuacio)
        binding.ubicacioActuacio.setText(args.currentActuacio.llocActuacio)
        binding.DataActuacio.setText(args.currentActuacio.dataActuacio)
        return binding.root
    }

    //Primer crida al mètode onViewCreated de la superclasse, després recupera una referència d'un botó
    // en el disseny cridant al mètode findViewById a la vista que retorna la funció requireView() i
    // l'assigna a la variable btnEditarActuacioBinding.
    //Estableix un onClickListener per al botó, quan es fa clic, recuperarà el text dels camps de text
    // del disseny TitolActuacio, DataActuacio, ubicacioActuacio, i l'assignarà a localtitle, localdate
    // i localubi respectivament. A continuació, crea una instància de la classe
    // "InformacioActuacioDirections" cridant al mètode "actionInformacioActuacioFragmentToEditarActuacio"
    // que pren els localtitle, localdate i localubi com a arguments. A continuació, crida al mètode de
    // navegació a findNavController() amb les indicacions com a argument. Això desplaçarà l'usuari al
    // fragment EditarActuacio.

    /**

    Aquest mètode és cridat quan la vista de l'Fragment ha estat creata.
    @param view La vista associada a l'Fragment
    @param savedInstanceState L'estat anteriorment desat de l'Fragment
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditarActuacioBinding = requireView().findViewById<Button>(R.id.botoEditarActuacio)

        btnEditarActuacioBinding.setOnClickListener{

            val localtitle = binding.TitolActuacio.text.toString()
            val localdate = binding.DataActuacio.text.toString()
            val localubi = binding.ubicacioActuacio.text.toString()

            val directions = InformacioActuacioDirections.actionInformacioActuacioFragmentToEditarActuacio(localtitle, localdate, localubi)
            findNavController().navigate(directions)
        }
    }
}