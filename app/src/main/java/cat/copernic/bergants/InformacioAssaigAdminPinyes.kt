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

class InformacioAssaigAdminPinyes : Fragment() {

    //Atribut de tipus Button per afegir una imatge
    private lateinit var AfegirUnaPinya: Button
    private lateinit var titolAssaig: TextView
    private lateinit var dataAssaig: TextView
    private lateinit var llocAssaig: TextView
    private lateinit var assaig: AssaigModel

    private val args by navArgs<InformacioAssaigAdminPinyesArgs>()

    private lateinit var binding: FragmentInformacioAssaigAdminPinyesBinding

    //Atribut on guardarem l'URI de la imatge que volem afegir. L'inicialitzem com a null, ja que encara no hem seleccionat la imatge
    private var photoSelectedUri: Uri?=null

    //Declarem i incialitzem un atribut de tipus FirebaseStorage, classe on trobarem els mètodes per treballar amb el servei storage de Firebase
    private var storage = FirebaseStorage.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseStorage

    //Atribut on guardarem el resultat de la nostra activitat, en el nostre cas seleccionar la imatge de la galeria.
    private val resultat=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            photoSelectedUri = it.data?.data //Assignem l'URI de la imatge, si l'activitat ha estat exitosa
        }
    }

    /*Declarem i inicialitzem un atribut "storageRef" de tipus referència necessària per poder pujar, llegir, modificar, etc. arxius multimèdia
     *(en el nostre cas imatges) en el servei Storage de Firebase.
     *Una referència és un punter al lloc del núvol on guardarem els nostres fitxers.
     *Si s'instancia amb reference, la referència apuntarà a l'arrel ("image"), és a dir, haurem de fer "private var storageRef = storage.reference".
     *Sí volem apuntar a un directori diferent al de l'arrel, en el nostre cas el subdirectori "imatges" creat per nosaltres, hem de cridar al mètode child
     *al qual li passem com a paràmetre el directori que volem crear (si aquest directori existeix, no es crearà de nou, simplement es situarà en el directori
     *existent per poder guardar un fitxer multimèdia). En el nostre cas, aquest subdirectori es trobarà penjant del directori arrel ("image") i per obtenir-lo
     *, és a dir, per situar-nos en ell per afegir el nou subdirectori, fem servir el mètode getReference() que ens retornarà el directori arrel "image".
     */
    private var storageRef = storage.getReference().child("imatges")

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
        binding = FragmentInformacioAssaigAdminPinyesBinding.inflate(inflater, container, false)
        AfegirUnaPinya = binding.AfegirUnaPinya

        binding.TitolAssaig.setText(args.currentAssaig.titolAssaig)
        binding.ubicacioAssaig.setText(args.currentAssaig.llocAssaig)
        binding.DataAssaig.setText(args.currentAssaig.dataAssaig)

        //Listeners al boto afegir imatge
        AfegirUnaPinya.setOnClickListener {
            //Afegim la imatge mitjançant el mètode afegirImatge creat per nosaltres
            afegirImatge()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditarAssaigBinding = requireView().findViewById<Button>(R.id.botoEditarAssaig)

        btnEditarAssaigBinding.setOnClickListener {

            val localtitle = binding.TitolAssaig.text.toString()
            val localdate = binding.DataAssaig.text.toString()
            val localubi = binding.ubicacioAssaig.text.toString()

            val directions = InformacioAssaigAdminPinyesDirections.actionInformacioAssaigAdminPinyesToEditarAssaig(localtitle, localdate, localubi)

            //findNavController().navigate(R.id.action_informacio_assaig_admin_pinyes_to_editar_assaig)

            findNavController().navigate(directions)
        }
    }
}
