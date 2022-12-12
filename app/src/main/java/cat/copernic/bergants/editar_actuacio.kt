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
import cat.copernic.bergants.databinding.FragmentEditarActuacioBinding
import cat.copernic.bergants.databinding.FragmentEditarAssaigBinding
import cat.copernic.bergants.model.ActuacioModel
import cat.copernic.bergants.model.AssaigModel
import com.google.firebase.firestore.FirebaseFirestore

class editar_actuacio : Fragment() {
    private lateinit var binding: FragmentEditarActuacioBinding

    //EditText per introduïr les dades de la nova noticia a afegir
    private lateinit var titolActuacio: EditText
    private lateinit var dataActuacio: EditText
    private lateinit var ubicacioActuacio: EditText
    private lateinit var botoEditarActuacio: Button
    private lateinit var botoEliminarActuacio: Button
    private lateinit var actuacio: ActuacioModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditarActuacioBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun llegirDades(): ActuacioModel {
        //Guardem les dades introduïdes per l'usuari
        var titolActuacio = titolActuacio.text.toString()
        var dataActuacio = dataActuacio.text.toString()
        var ubicacioActuacio = ubicacioActuacio.text.toString()

        return ActuacioModel(titolActuacio, dataActuacio, ubicacioActuacio)
    }

    //Funció que modificarà una actuacio amb l'actuacio passada per paràmetre. Per fer la modificació, l'identificador del document
    //passat per paràmetre ha de coincidir amb un dels identificadors dels documents que ja estan afegits a la col.lecció, si no el crearà de nou.
    //Això ho podem fer servir quan necessitem modificar tots els parells clau-valor d'un document que existeix dins la col.lecció.
    fun modificarActuacio(acutacio: ActuacioModel){
        bd.collection("Actuacions").document(titolActuacio.text.toString()).set(
            hashMapOf(
                "titolActuacio" to titolActuacio.text.toString(),
                "dataActuacio" to dataActuacio.text.toString(),
                "ubicacioActuacio" to ubicacioActuacio.text.toString()
            )
        )
            .addOnSuccessListener { //S'ha afegit l'actuacio...
                Toast.makeText(
                    requireActivity(),
                    "L'actuacio s'ha afegit correctament",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "L'actuacio no s'ha afegit", Toast.LENGTH_LONG)
                    .show()
            }
    }

    //Eliminar l'actuacio corresponent al titol passat com a paràmetre. L'eliminarà si existeix.
    fun eliminarActuacio(titolActuacio:String){
        bd.collection("Actuacions").document(titolActuacio)
            .delete()
            .addOnSuccessListener { //S'ha modificat l'actuacio...
                Toast.makeText(requireActivity(),"S'ha eliminat l'actuacio amb titol $titolActuacio", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{ //No s'ha modificat l'actuacio...
                Toast.makeText(requireActivity(),"No s'ha eliminat l'actuacio", Toast.LENGTH_LONG).show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titolActuacio = binding.titolActuacio
        dataActuacio = binding.dataActuacio
        ubicacioActuacio = binding.llocActuacio
        botoEditarActuacio = binding.botoEditarActuacio
        botoEliminarActuacio = binding.botoEliminarActuacio

        //creem el bundle
        val bundle = arguments

        if(bundle == null){
            Log.d("Confirmation","Fragment 2 didn't receive info")
            return
        }

        //extraiem els args des del bundle

        val args = editar_actuacioArgs.fromBundle(bundle)

        binding.titolActuacio.setText(args.titolActuacio)
        binding.dataActuacio.setText(args.dataActuacio)
        binding.ubicacioActuacio.setText(args.ubicacioActuacio)

        //Modifiquem tota l'actuacio si l'actuacio existeix en la BBDD.
        botoEditarActuacio.setOnClickListener {

            var actuacio = llegirDades() //Actuacio introduïda per l'usuari

            if (actuacio.titolActuacio?.isNotEmpty() == true && actuacio.dataActuacio?.isNotEmpty() == true && actuacio.llocActuacio?.isNotEmpty() == true) {

                //Modifiquem l'assaig mitjançant la funció modificarActuacio creada per nosaltres
                modificarActuacio(actuacio)

            } else {
                //Mostrem un missatge a l'usuari mitjançant un Toast
                Toast.makeText(requireActivity(),"Cal introduïr els paramatres a modificar",Toast.LENGTH_LONG).show()
            }
        }

        //Eliminem l'actuacio corresponent al codi passat per paràmetre, si aquest existeix en la col.lecció
        botoEliminarActuacio.setOnClickListener {

            var actuacio = llegirDades()

            if (actuacio.titolActuacio?.isNotEmpty() == true) {
                //Eliminem l'actuacio mitjançant la funció eliminarActuacio creada per nosaltres
                eliminarActuacio(actuacio.titolActuacio!!)
            }else{
                //Mostrem un missatge a l'usuari mitjançant un Toast
                Toast.makeText(requireActivity(),"Cal introduïr un titol de l'actuacio que volem eliminar",Toast.LENGTH_LONG).show()
            }
        }
    }
}
