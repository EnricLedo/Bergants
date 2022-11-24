package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentActuacioBinding
import cat.copernic.bergants.databinding.FragmentAfegirActuacioBinding
import cat.copernic.bergants.databinding.FragmentAfegirAssaigBinding
import cat.copernic.bergants.model.ActuacioModel
import cat.copernic.bergants.model.AssaigModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class AfegirActuacio : Fragment() {
    private lateinit var binding: FragmentAfegirActuacioBinding

    //EditText per introduïr les dades de la nova actuacio a afegir
    private lateinit var titolActuacio: EditText
    private lateinit var dataActuacio: EditText
    private lateinit var llocActuacio: EditText

    //Atribut de tipus Button per afegir una nova actuacio
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem les actuacions
    private lateinit var actuacions: ActuacioModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentAfegirActuacioBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun llegirDades(): ActuacioModel {
        //Guardem les dades introduïdes per l'usuari
        var titolActuacio = titolActuacio.text.toString()
        var dataActuacio = dataActuacio.text.toString()
        var llocActuacio = llocActuacio.text.toString()

        return ActuacioModel(titolActuacio, dataActuacio, llocActuacio)
    }

    fun afegirActuacio(actuacio: ActuacioModel) {
        //Seleccionem la col.lecció on volem afegir l'actuació mitjançant la funció collection("Actuacions"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim l'actuació a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(actuacio). Si l'actuació existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Actuacions").add(actuacio)
            .addOnSuccessListener { //S'ha afegir l'actuació...
                Toast.makeText(
                    requireActivity(),
                    "L'actuació s'ha afegit correctament",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "L'actuació no s'ha afegit", Toast.LENGTH_LONG)
                    .show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titolActuacio = binding.titolActuacio
        dataActuacio = binding.dataActuacio
        llocActuacio = binding.llocActuacio
        botoAfegir = binding.botoGuardarActuacio

        botoAfegir.setOnClickListener {
            llegirDades()
            var actuacio = llegirDades() //Actuacio introduida per l'usuari
            if (actuacio.titolActuacio?.isNotEmpty() == true && actuacio.dataActuacio?.isNotEmpty() == true && actuacio.llocActuacio?.isNotEmpty() == true) {
                afegirActuacio(actuacio)
            } else {
                Snackbar.make(it, "Falta indroduir parametres!!!", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}