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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import cat.copernic.bergants.databinding.FragmentInformacioAssaigAdminPinyesBinding
import com.google.firebase.storage.FirebaseStorage

class InformacioAssaigAdminPinyes : Fragment() {

    //Atribut de tipus Button per afegir una imatge
    private lateinit var AfegirUnaPinya: Button

    private lateinit var binding: FragmentInformacioAssaigAdminPinyesBinding

    //Atribut on guardarem l'URI de la imatge que volem afegir. L'inicialitzem com a null, ja que encara no hem seleccionat la imatge
    private var photoSelectedUri: Uri?=null

    //resultLauncher és l'atribut on guardarem el resultat de la nostra activitat, en el nostre cas obrir la galeria i mitjançant el qual
    //cridarem a l'Intent per obrir-la.
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            photoSelectedUri = it.data?.data //Assignem l'URI de la imatge
        }
    }

    //Declarem i incialitzem un atribut de tipus FirebaseStorage, classe on trobarem els mètodes per treballar amb el servei storage de Firebase
    private var storage = FirebaseStorage.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseStorage

    //Declarem i incialitzem un atribut de tipus referència necessària per poder pujar, llegir, modificar, etc. arxius multimèdia
    //(en el nostre cas imatges) en el servei Storage de Firebase.
    //Una referència és un punter al lloc del núvol on guardarem els nostres fitxers
    //Si s'instància amb reference, la referència apuntarà a l'arrel i si utilitzem el mètode child, podem situarnos en un directori del núvol
    //diferent a l'arrel i que serà el que passem per paràmetre de child.
    private var storageRef = storage.reference.child("image/imatges") //Inicialitzem mitjançant la crida a l'atribut reference de la classe FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inicialitzem el botó per afegir l'imatge
        AfegirUnaPinya = binding.AfegirUnaPinya

        //Listeners al boto afegir imatge
        AfegirUnaPinya.setOnClickListener {

            //Afegim la imatge mitjançant el mètode afegirImatge creat per nosaltres
            afegirImatge()

        }
    }



    private fun afegirImatge(){
        //Obrim la galeria per seleccionar la imatge
        resultLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))

        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let{uri-> //Hem seleccionat una imatge...
            //Afegim (pujem) la imatge que hem seleccionat mitjançant el mètode putFile de la classe FirebasStorage, passant-li com a
            //paràmetre l'URI de la imatge.
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    Toast.makeText(requireActivity(),"La imatge s'ha pujat amb èxit", Toast.LENGTH_LONG).show()
                }
        }
    }
}
