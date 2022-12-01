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
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import cat.copernic.bergants.databinding.FragmentAfegirMembreBinding
import cat.copernic.bergants.model.MembreModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class afegirMembre : Fragment() {
    private lateinit var binding: FragmentAfegirMembreBinding

    //EditText per introduïr les dades del nou membre a afegir
    private lateinit var imgMembre: ImageButton
    private lateinit var nomMembre: EditText
    private lateinit var malnom: EditText
    private lateinit var alcadaEspatlles: EditText
    private lateinit var alcadaMans: EditText
    private lateinit var correuMembre: EditText
    private lateinit var adrecaMembre: EditText
    private lateinit var telefonMembre: EditText
    private lateinit var rolMembre: EditText
    private lateinit var altaMembre: EditText

    //Atribut de tipus Button per afegir un nou membre
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem els membres
    private lateinit var membres: MembreModel

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
    private var storageRef = storage.getReference().child("membres")

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

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
        savedInstanceState: Bundle?): View? {
        binding = FragmentAfegirMembreBinding.inflate(inflater, container, false)
        imgMembre =  binding.imgMembre

        imgMembre.setOnClickListener{
            afegirImatge()
        }

        return binding.root
    }

    fun llegirDades(): MembreModel {
        //Guardem les dades introduïdes per l'usuari
        var nomMembre = nomMembre.text.toString()
        var malnom = malnom.text.toString()
        var alcadaEspatlles = alcadaEspatlles.text.toString()
        var alcadaMans = alcadaMans.text.toString()
        var correuMembre = correuMembre.text.toString()
        var adrecaMembre = adrecaMembre.text.toString()
        var telefonMembre = telefonMembre.text.toString()
        var rolMembre = rolMembre.text.toString()
        var altaMembre = altaMembre.text.toString()

        return MembreModel(nomMembre, malnom, alcadaEspatlles, alcadaMans, correuMembre,
            adrecaMembre, telefonMembre, rolMembre, altaMembre)
    }

    fun afegirMembre(membre: MembreModel) {
        //Seleccionem la col.lecció on volem afegir el Membre mitjançant la funció collection("Membres"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim el membre a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(membre). Si el membre existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Membres").document(nomMembre.text.toString()).set(
            hashMapOf(
                "nomMembre" to nomMembre.text.toString(),
                "malnom" to malnom.text.toString(),
                "alcadaEspatlles" to alcadaEspatlles.text.toString(),
                "alcadaMans" to alcadaMans.text.toString(),
                "correuMembre" to correuMembre.text.toString(),
                "adrecaMembre" to adrecaMembre.text.toString(),
                "telefonMembre" to telefonMembre.text.toString(),
                "rolMembre" to rolMembre.text.toString(),
                "altaMembre" to altaMembre.text.toString()
            )
        )
            .addOnSuccessListener { //S'ha afegir l'assaig...
                Toast.makeText(
                    requireActivity(),
                    "El membre s'ha afegit correctament",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "El membre no s'ha afegit", Toast.LENGTH_LONG)
                    .show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nomMembre = binding.nomMembre
        malnom = binding.malnomMembre
        alcadaEspatlles = binding.espatllesMembre
        alcadaMans = binding.mansMembre
        correuMembre = binding.correuMembre
        adrecaMembre = binding.adrecaMembre
        telefonMembre = binding.telefonMembre
        rolMembre = binding.rolMembre
        altaMembre = binding.altaMembre
        botoAfegir = binding.botoGuardarMembre

        botoAfegir.setOnClickListener {
            llegirDades()
            var membre = llegirDades() //Membre introduit per l'usuari
            if (membre.nomMembre?.isNotEmpty() == true && membre.malnom?.isNotEmpty() == true && membre.alcadaEspatlles?.isNotEmpty() == true
                && membre.alcadaMans?.isNotEmpty() == true && membre.correuMembre?.isNotEmpty() == true
                && membre.adrecaMembre?.isNotEmpty() == true && membre.telefonMembre?.isNotEmpty() == true
                && membre.rolMembre?.isNotEmpty() == true && membre.altaMembre?.isNotEmpty() == true) {
                afegirMembre(membre)
            } else {
                Snackbar.make(it, "Falta indroduir parametres!!!", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}