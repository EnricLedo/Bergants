package cat.copernic.bergants

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import cat.copernic.bergants.databinding.FragmentEditarAssaigBinding
import cat.copernic.bergants.databinding.FragmentEditarNoticiaBinding
import cat.copernic.bergants.databinding.FragmentInformacioAssaigBinding
import cat.copernic.bergants.model.AssaigModel
import cat.copernic.bergants.model.NoticiaModel
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

    fun llegirDades(): AssaigModel {
        //Guardem les dades introduïdes per l'usuari
        var titolAssaig = titolAssaig.text.toString()
        var dataAssaig = dataAssaig.text.toString()
        var ubicacioAssaig = ubicacioAssaig.text.toString()

        return AssaigModel(titolAssaig, dataAssaig, ubicacioAssaig)
    }

    //Funció que modificarà un assaig amb l'assaig passat per paràmetre. Per fer la modificació, l'identificador del document
    //passat per paràmetre ha de coincidir amb un dels identificadors dels documents que ja estan afegits a la col.lecció, si no el crearà de nou.
    //Això ho podem fer servir quan necessitem modificar tots els parells clau-valor d'un document que existeix dins la col.lecció.
    fun modificarAssaig(assaig: AssaigModel){
        bd.collection("Assajos").document(titolAssaig.text.toString()).set(
            hashMapOf(
                "titolAssaig" to titolAssaig.text.toString(),
                "dataAssaig" to dataAssaig.text.toString(),
                "ubicacioAssaig" to ubicacioAssaig.text.toString()
            )
        )
            .addOnSuccessListener { //S'ha afegit l'assaig...
                Toast.makeText(
                    requireActivity(),
                    "L'assaig s'ha afegit correctament",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "L'assaig no s'ha afegit", Toast.LENGTH_LONG)
                    .show()
            }
    }

    //Eliminar l'assaig corresponent al titol passat com a paràmetre. L'eliminarà si existeix.
    fun eliminarAssaig(titolAssaig:String){
        bd.collection("Assajos").document(titolAssaig)
            .delete()
            .addOnSuccessListener { //S'ha modificat l'assaig...
                Toast.makeText(requireActivity(),"S'ha eliminat l'assaig amb titol $titolAssaig", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{ //No s'ha modificat l'assaig...
                Toast.makeText(requireActivity(),"No s'ha eliminat l'assaig", Toast.LENGTH_LONG).show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titolAssaig = binding.titolAssaig
        dataAssaig = binding.dataAssaig
        ubicacioAssaig = binding.ubicacioAssaig
        botoEditarAssaig = binding.botoEditarAssaig
        botoEliminarAssaig = binding.botoEliminarAssaig

        //creem el bundle
        val bundle = arguments

        if(bundle == null){
            Log.d("Confirmation","Fragment 2 didn't receive info")
            return
        }

        //extraiem els args des del bundle

        val args = editar_assaigArgs.fromBundle(bundle)

        binding.titolAssaig.setText(args.titolAssaig)
        binding.dataAssaig.setText(args.dataAssaig)
        binding.ubicacioAssaig.setText(args.ubicacioAssaig)

        //Modifiquem tot l'assaig si l'assaig existeix en la BBDD.
        botoEditarAssaig.setOnClickListener {

            var assaig = llegirDades() //Assaig introduït per l'usuari

            if (assaig.titolAssaig?.isNotEmpty() == true && assaig.dataAssaig?.isNotEmpty() == true && assaig.llocAssaig?.isNotEmpty() == true) {

                //Modifiquem l'assaig mitjançant la funció modificarAssaig creada per nosaltres
                modificarAssaig(assaig)

            } else {
                //Mostrem un missatge a l'usuari mitjançant un Toast
                Toast.makeText(requireActivity(),"Cal introduïr els paramatres a modificar",Toast.LENGTH_LONG).show()
            }
        }

        //Eliminem l'assaig corresponent al codi passat per paràmetre, si aquest existeix en la col.lecció
        botoEliminarAssaig.setOnClickListener {

            var assaig = llegirDades()

            if (assaig.titolAssaig?.isNotEmpty() == true) {
                //Eliminem l'assaig mitjançant la funció eliminarAssaig creada per nosaltres
                eliminarAssaig(assaig.titolAssaig!!)
            }else{
                //Mostrem un missatge a l'usuari mitjançant un Toast
                Toast.makeText(requireActivity(),"Cal introduïr un titol de l'assaig que volem eliminar",Toast.LENGTH_LONG).show()
            }
        }
    }
}
