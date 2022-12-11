package cat.copernic.bergants

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import cat.copernic.bergants.databinding.FragmentEditarAssaigBinding
import cat.copernic.bergants.databinding.FragmentEditarNoticiaBinding
import cat.copernic.bergants.databinding.FragmentInformacioAssaigBinding
import cat.copernic.bergants.model.AssaigModel
import com.google.firebase.firestore.FirebaseFirestore

class editar_assaig : Fragment() {
    private lateinit var binding: FragmentEditarAssaigBinding

    //EditText per introduïr les dades de la nova noticia a afegir
    private lateinit var titolAssaig: EditText
    private lateinit var dataAssaig: EditText
    private lateinit var ubicacioAssaig: EditText
    private lateinit var botoEditarAssaig: Button
    private lateinit var botoEliminarAssaig: Button
    private lateinit var assaig: AssaigModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditarAssaigBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //create bundle
        val bundle = arguments

        if(bundle == null){
            Log.d("Confirmation","Fragment 2 didn't receive info")
            return
        }

        //extract args from bundle

        val args = editar_assaigArgs.fromBundle(bundle)

        binding.titolAssaig.setText(args.titolAssaig)
        binding.dataAssaig.setText(args.dataAssaig)
        binding.ubicacioAssaig.setText(args.ubicacioAssaig)
    }
}
