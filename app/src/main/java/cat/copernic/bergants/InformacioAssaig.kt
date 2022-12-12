package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.bergants.databinding.FragmentAfegirNoticiaBinding
import cat.copernic.bergants.databinding.FragmentInformacioAssaigBinding
import cat.copernic.bergants.model.AssaigModel
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class InformacioAssaig : Fragment() {
    private lateinit var binding: FragmentInformacioAssaigBinding

    private lateinit var titolAssaig: TextView
    private lateinit var dataAssaig: TextView
    private lateinit var llocAssaig: TextView
    private lateinit var assaig: AssaigModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd = FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    //private val args by navArgs<InformacioAssaigArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformacioAssaigBinding.inflate(inflater, container, false)
/*
        binding.TitolAssaig.setText(args.currentAssaig.titolAssaig)
        binding.ubicacioAssaig.setText(args.currentAssaig.llocAssaig)
        binding.dataAssaig.setText(args.currentAssaig.dataAssaig)

 */
        return binding.root
    }
}