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
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.bergants.databinding.FragmentInformacioActuacioBinding
import cat.copernic.bergants.databinding.FragmentInformacioAssaigBinding
import cat.copernic.bergants.model.ActuacioModel
import cat.copernic.bergants.model.AssaigModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

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

    //Atribut on guardarem l'URI de la imatge que volem afegir. L'inicialitzem com a null, ja que encara no hem seleccionat la imatge
    private var photoSelectedUri: Uri?=null

    //Declarem i incialitzem un atribut de tipus FirebaseStorage, classe on trobarem els mètodes per treballar amb el servei storage de Firebase
    private var storage = FirebaseStorage.getInstance()

    private var storageRef = storage.getReference().child("imatges")

    private val resultat=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            photoSelectedUri = it.data?.data //Assignem l'URI de la imatge, si l'activitat ha estat exitosa
        }
    }

    private fun afegirImatge(){

        //Obrim la galeria
        resultat.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))

        //adrecaFitxer varaible a la qual li assignarem la refèrencia del fitxer que volem guardar a Storage.
        //A l'atribut storageRef li hem assignat la referència al subdirectori on es guardarà la imatge. El nom de la imatge, el creem de nou
        //amb el mètode child al qual li passem com a paràmetre el nom de la imatge, que en el nostre cas serà, el mateix nom que té la imatge
        //en la galeria del mòbil, és a dir, a photoSelectedUri tenim l'adreça de la galeria on està guardada la imatge (URI) i amb lastPathSegment,
        //obtenim l'últim segment de l'adreça què és justament el nom de la imatge. Una adreça (URI) no és un String, per tant l'hem de convertir a
        //String, en el nostre cas, mitjançant el mètode toString()
        var adrecaFitxer = storageRef.child((photoSelectedUri?.lastPathSegment).toString());

        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let{uri-> //Hem seleccionat una imatge. A la variable uri guardem l'URI de la imatge
            //Afegim (pujem) la imatge que hem seleccionat mitjançant el mètode putFile de la classe FirebasStorage, passant-li com a
            //paràmetre l'URI de la imatge. Aquest mètode carrega la imatge de manera asíncrona.
            adrecaFitxer.putFile(uri).addOnSuccessListener {
                Toast.makeText(requireActivity(),"La imatge s'ha pujat amb èxit", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformacioActuacioBinding.inflate(inflater, container, false)
        maps_exemple = binding.mapsExemple
        maps_exemple.setOnClickListener{
            afegirImatge()
        }
        binding.TitolActuacio.setText(args.currentActuacio.titolActuacio)
        binding.ubicacioActuacio.setText(args.currentActuacio.llocActuacio)
        binding.DataActuacio.setText(args.currentActuacio.dataActuacio)
        return binding.root
    }

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