package cat.copernic.bergants

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.bergants.databinding.FragmentInformacioAssaigAdminPinyesBinding
import cat.copernic.bergants.databinding.FragmentInformacioAssaigBinding
import cat.copernic.bergants.model.AssaigModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

@Keep
class InformacioAssaigAdminPinyes : Fragment() {

    //Atribut de tipus Button per afegir una imatge
    private lateinit var titolAssaig: TextView
    private lateinit var dataAssaig: TextView
    private lateinit var llocAssaig: TextView
    private lateinit var assaig: AssaigModel

    private val args by navArgs<InformacioAssaigAdminPinyesArgs>()

    private lateinit var binding: FragmentInformacioAssaigAdminPinyesBinding

    /**

    Aquesta classe és responsable de mostrar la vista per al administrador per afegir informació sobre les pinyes d'un assaig.
    Utilitza un binding per associar les dades de l'assaig amb les vistes del layout.
    També conté un listener per afegir imatges a l'assaig.
    @param inflater inflater per crear la vista
    @param container container on s'inflarà la vista
    @param savedInstanceState bundle per guardar l'estat de la instància
    @return retorna la vista inflada i configurada
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformacioAssaigAdminPinyesBinding.inflate(inflater, container, false)

        binding.TitolAssaig.setText(args.currentAssaig.titolAssaig)
        binding.ubicacioAssaig.setText(args.currentAssaig.llocAssaig)
        binding.DataAssaig.setText(args.currentAssaig.dataAssaig)

        return binding.root
    }

    //Primer crida al mètode onViewCreated de la superclasse, després recupera una referència d'un botó
    // en el disseny cridant al mètode findViewById a la vista que retorna la funció requireView() i
    // l'assigna a la variable btnEditarAssaigBinding.
    //Estableix un onClickListener per al botó, quan es fa clic, recuperarà el text dels camps de text
    // del disseny TitolAssaig, DataAssaig, ubicacioAssaig, i l'assignarà a localtitle, localdate i
    // localubi respectivament. A continuació, crea una instància de la classe
    // "InformacioAssaigAdminPinyesDirections" cridant al mètode
    // "actionInformacioAssaigAdminPinyesToEditarAssaig" que pren els localtitle, localdate i localubi
    // com a arguments. A continuació, crida al mètode de navegació a findNavController() amb les
    // indicacions com a argument. Això desplaçarà l'usuari al fragment EditarAssaig.

    /**

    Aquesta funció és cridada quan la vista del fragment és creada. Es configura el comportament del botó "Editar assaig"
    per tal que quan es premi, es navegui a la pàgina d'edició d'assaig passant-li les dades del títol, la data i la ubicació de l'assaig.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditarAssaigBinding = requireView().findViewById<Button>(R.id.botoEditarAssaig)

        btnEditarAssaigBinding.setOnClickListener {

            val localtitle = binding.TitolAssaig.text.toString()
            val localdate = binding.DataAssaig.text.toString()
            val localubi = binding.ubicacioAssaig.text.toString()

            val directions = InformacioAssaigAdminPinyesDirections.actionInformacioAssaigAdminPinyesToEditarAssaig(localtitle, localdate, localubi)

            findNavController().navigate(directions)
        }
    }
}
