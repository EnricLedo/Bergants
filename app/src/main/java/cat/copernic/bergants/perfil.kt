package cat.copernic.bergants

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import cat.copernic.bergants.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File


@Keep
class perfil : Fragment() {
    private lateinit var binding: FragmentPerfilBinding

    //EditText per introduïr les dades de la nova noticia a afegir
    private lateinit var nomPerfil: TextView
    private lateinit var malnomenric: TextView
    private lateinit var rolenric: TextView
    private lateinit var dataenric: TextView
    private lateinit var fotoEnric: ImageView

    private var bd =
        FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth

    private var storage = FirebaseStorage.getInstance()
    private var storageRef = storage.getReference().child("imatge/membre/")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)



        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nomPerfil = binding.nomPerfil
        malnomenric = binding.malnomenric
        rolenric = binding.rolenric
        dataenric = binding.dataenric
        fotoEnric = binding.fotoEnric

        carregarImatge()
        val currentUser = auth.currentUser!!.email.toString()
        val database = FirebaseFirestore.getInstance()
        val collectionReference = database.collection("Membres")
        val documentReference = collectionReference.document(currentUser)
        documentReference.get().addOnSuccessListener { documentSnapshot ->
            val field = documentSnapshot.getString("nomMembre")
            nomPerfil.setText(field)
            val field2 = documentSnapshot.getString("rolMembre")
            rolenric.setText(field2)
            val field3 = documentSnapshot.getString("altaMembre")
            dataenric.setText(field3)
            val field4 = documentSnapshot.getString("malnom")
            malnomenric.setText(field4)
        }
    }

    private fun carregarImatge(){
        auth = Firebase.auth
        val currentUser = auth.currentUser!!.email
        var adrecaImatge = storageRef.child(currentUser!!.toString())
        var fitxerTemporal = File.createTempFile("temp", null)
        adrecaImatge.getFile(fitxerTemporal).addOnSuccessListener{
            val mapaBits = BitmapFactory.decodeFile(fitxerTemporal.absolutePath)
            binding.fotoEnric.setImageBitmap(mapaBits)
        }
    }
}