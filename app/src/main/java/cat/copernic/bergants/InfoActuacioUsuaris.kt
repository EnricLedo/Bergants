package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Keep
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.bergants.databinding.FragmentInfoActuacioUsuarisBinding
import cat.copernic.bergants.model.ActuacioModel
import com.google.firebase.firestore.FirebaseFirestore

@Keep
class InfoActuacioUsuaris : Fragment() {
    private lateinit var binding: FragmentInfoActuacioUsuarisBinding

    private lateinit var titolActuacio: TextView
    private lateinit var dataActuacio: TextView
    private lateinit var ubicacioActuacio: TextView
    private lateinit var llocActuacio: TextView
    private lateinit var actuacio: ActuacioModel

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
        binding = FragmentInfoActuacioUsuarisBinding.inflate(inflater, container, false)
        binding.TitolActuacio.setText(args.currentActuacio.titolActuacio)
        binding.ubicacioActuacio.setText(args.currentActuacio.llocActuacio)
        binding.DataActuacio.setText(args.currentActuacio.dataActuacio)
        return binding.root
    }
}