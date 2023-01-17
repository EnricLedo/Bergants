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
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentAfegirMembreBinding
import cat.copernic.bergants.model.MembreModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

@Keep
class afegirMembre : Fragment() {
    private lateinit var binding: FragmentAfegirMembreBinding

    //EditText per introduïr les dades del nou membre a afegir
    private lateinit var imgMembre: ImageButton
    private lateinit var nomMembre: EditText
    private lateinit var malnom: EditText
    private lateinit var alcadaEspatlles: EditText
    private lateinit var alcadaMans: EditText
    private lateinit var correuMembre: EditText
    private lateinit var passwordOkMembre: EditText
    private lateinit var adrecaMembre: EditText
    private lateinit var telefonMembre: EditText
    private lateinit var rolMembre: EditText
    private lateinit var altaMembre: EditText

    //Atribut de tipus Button per afegir un nou membre
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem els membres
    private lateinit var membres: MembreModel
    private lateinit var auth: FirebaseAuth

    //Atribut on guardarem l'URI de la imatge que volem afegir. L'inicialitzem com a null, ja que encara no hem seleccionat la imatge
    private var photoSelectedUri: Uri?=null

    //Declarem i incialitzem un atribut de tipus FirebaseStorage, classe on trobarem els mètodes per treballar amb el servei storage de Firebase
    private var storage = FirebaseStorage.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseStorage

    /*Declarem i inicialitzem un atribut "storageRef" de tipus referència necessària per poder pujar, llegir, modificar, etc. arxius multimèdia
     *(en el nostre cas imatges) en el servei Storage de Firebase.
     *Una referència és un punter al lloc del núvol on guardarem els nostres fitxers.
     *Si s'instancia amb reference, la referència apuntarà a l'arrel ("imatge"), és a dir, haurem de fer "private var storageRef = storage.reference".
     *Sí volem apuntar a un directori diferent al de l'arrel, en el nostre cas el subdirectori "imatges" creat per nosaltres, hem de cridar al mètode child
     *al qual li passem com a paràmetre el directori que volem crear (si aquest directori existeix, no es crearà de nou, simplement es situarà en el directori
     *existent per poder guardar un fitxer multimèdia). En el nostre cas, aquest subdirectori es trobarà penjant del directori arrel ("image") i per obtenir-lo
     *, és a dir, per situar-nos en ell per afegir el nou subdirectori, fem servir el mètode getReference() que ens retornarà el directori arrel "image".
     */
    private var storageRef = storage.getReference().child("imatge/membre/")

    //Atribut on guardarem el resultat de la nostra activitat, en el nostre cas seleccionar la imatge de la galeria.
    private val guardarImatge = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            photoSelectedUri = result.data?.data //Assignem l'URI de la imatge
        }
    }

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    private fun afegirImatge(){

        //Obrim la galeria
        guardarImatge.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        storageRef = storage.reference.child("imatge/membre/").child(correuMembre.text.toString()) //si el user no ecribe ningun correo petara ya que no tiene id
        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let { uri ->
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    Toast.makeText(context, "La imatge s'ha pujat amb èxit", Toast.LENGTH_LONG)
                        .show()
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentAfegirMembreBinding.inflate(inflater, container, false)
        //Aquest codi està configurant un onClickListener per a una visualització d'imatge (imgMembre)
        // a Kotlin. OnClickListener s'està configurant en una funció anomenada "afegirImatge()",
        // que s'executarà quan es faci clic a la vista de la imatge. La funció "afegirImatge()" és
        // l'encarregada d'obrir la biblioteca d'imatges o la càmera del dispositiu per permetre a
        // l'usuari seleccionar o capturar una imatge.
        imgMembre =  binding.imgMembre
        imgMembre.setOnClickListener{
            afegirImatge()
        }
        return binding.root
    }

    //Aquest codi és una funció anomenada "llegirDades()" a Kotlin que probablement s'utilitza per
    // recollir l'entrada de l'usuari d'un formulari i crear una instància de la classe "MembreModel".
    // Primer crea diverses variables (nomMembre, malnom, alcadaEspatlles, alcadaMans, telefonMembre,
    // rolMembre, altaMembre, correuMembre, adrecaMembre) obtenint el text de les vistes EditText
    // corresponents i convertint-lo en una cadena. A continuació, crea una instància de "MembreModel"
    // passant les variables com a arguments i la retorna.
    fun llegirDades(): MembreModel {
        //Guardem les dades introduïdes per l'usuari
        var nomMembre = nomMembre.text.toString()
        var malnom = malnom.text.toString()
        var alcadaEspatlles = alcadaEspatlles.text.toString()
        var alcadaMans = alcadaMans.text.toString()
        var telefonMembre = telefonMembre.text.toString()
        var rolMembre = rolMembre.text.toString()
        var altaMembre = altaMembre.text.toString()
        var correuMembre = correuMembre.text.toString()
        var adrecaMembre = adrecaMembre.text.toString()

        return MembreModel(nomMembre, malnom, alcadaEspatlles, alcadaMans, correuMembre,
            adrecaMembre, telefonMembre, rolMembre, altaMembre)
    }

    fun afegirMembre(membre: MembreModel) {
        //Seleccionem la col.lecció on volem afegir el Membre mitjançant la funció collection("Membres"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim el membre a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(membre). Si el membre existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Membres").document(correuMembre.text.toString()).set(
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
            //Aquest codi està afegint oients d'èxit i fracàs a una tasca asíncrona. Si la tasca té
            // èxit, crea un AlertDialog amb un missatge "assaigCorrect" que té un sol botó "Acceptar"
            // i el mostra. A continuació, crida al mètode de notificació amb els valors de "titolAssaig"
            // i "llocAssaig" com a arguments. Si la tasca no té èxit, crea un AlertDialog amb un missatge
            // "assaigWrong" que té un sol botó "Acceptar" i el mostra.
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.membreCorredct))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
            }
            .addOnFailureListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.membreWrong))
                builder.setPositiveButton(getString(R.string.aceptar) , null)
                val dialog = builder.create()
                dialog.show()
            }
    }

    fun registrar(correu: String, contrasenya: String){
        //Regsitrem a un usuari mitjançant el seu correu i contrasenya amb el mètode d'Authentication createUserWithEmailAndPassword passant com
        //a paràmetres el seu correu i contrasenya.
        //El registre d'un usuari és una tasca asincrona. El mètode addOnCompleteListener ens permet controlar quan finalitza el registre i com finalitza
        auth.createUserWithEmailAndPassword(correu,contrasenya)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth= Firebase.auth
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
        passwordOkMembre = binding.passwordOkMembre

        //Aquest codi està configurant un onClickListener per a un botó (botoAfegir) a Kotlin. Quan es
        // fa clic al botó, el codi executarà primer la funció "llegirDades()" que recull l'entrada de
        // l'usuari d'un formulari i crea una instància de la classe "MembreModel".
        //Aleshores comprovarà que els camps de correu electrònic i contrasenya no estiguin buits, si
        // és així, cridarà a una funció "registrar(correuMembre, passwordOkMembre)". Si els camps estan
        // buits, mostrarà un missatge de Snackbar amb la cadena "correuMal", que és un missatge d'error.
        //Aleshores comprovarà que tots els camps obligatoris del MembreModel no estiguin buits, si és
        // així cridarà a la funció "afegirMembre(membre)" i navegarà l'usuari a la pantalla
        // "membres_fragment". Si algun dels camps està buit, mostrarà un missatge de Snackbar amb la
        // cadena "paràmetres", que és  un missatge d'error indicant a l'usuari que ompli
        // els camps obligatoris.
        botoAfegir.setOnClickListener {
            llegirDades()
            var correuMembre = correuMembre.text.toString()
            var passwordOkMembre = passwordOkMembre.text.toString()
            if (correuMembre.isNotEmpty()&&passwordOkMembre.isNotEmpty()){
                registrar(correuMembre, passwordOkMembre,)
            }else {
                Snackbar.make(it, getString(R.string.correuMal), Snackbar.LENGTH_LONG).show()
            }

            var membre = llegirDades() //Membre introduit per l'usuari
            if (membre.nomMembre?.isNotEmpty() == true && membre.malnom?.isNotEmpty() == true && membre.alcadaEspatlles?.isNotEmpty() == true
                && membre.alcadaMans?.isNotEmpty() == true && membre.correuMembre?.isNotEmpty() == true
                && membre.adrecaMembre?.isNotEmpty() == true && membre.telefonMembre?.isNotEmpty() == true
                && membre.rolMembre?.isNotEmpty() == true && membre.altaMembre?.isNotEmpty() == true) {
                afegirMembre(membre)
                findNavController().navigate(R.id.action_afegirMembre_to_membres_fragment)

            } else {
                Snackbar.make(it, getString(R.string.parametres), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}