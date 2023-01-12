package cat.copernic.bergants

import android.os.Bundle
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentAfegirActuacioBusBinding
import cat.copernic.bergants.model.ActuacioBusModel
import cat.copernic.bergants.model.BusModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class afegir_actuacio_bus : Fragment() {
    private lateinit var binding: FragmentAfegirActuacioBusBinding

    //EditText per introduïr les dades de la nova actuacio a afegir
    private lateinit var titolActuacio: EditText
    private lateinit var dataActuacio: EditText
    private lateinit var llocActuacio: EditText
    private lateinit var ubicacioBus: EditText
    private lateinit var horariBus: EditText
    private lateinit var placesBus: EditText

    //Atribut de tipus Button per afegir una nova actuacio
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem les actuacions i els autocars
    private lateinit var actuacions: ActuacioBusModel
    private lateinit var autocar: ArrayList<BusModel>

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd = FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentAfegirActuacioBusBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun llegirDades(): ActuacioBusModel {
        //Guardem les dades introduïdes per l'usuari
        var titolActuacio = titolActuacio.text.toString()
        var dataActuacio = dataActuacio.text.toString()
        var llocActuacio = llocActuacio.text.toString()
        var ubicacioBus = ubicacioBus.text.toString()
        var horariBus = horariBus.text.toString()
        var placesBus = placesBus.text.toString()

        //Afegim l'autocar introduït per l'usuari a l'atribut autocar
        autocar.add(BusModel(ubicacioBus, horariBus, placesBus))

        return ActuacioBusModel(titolActuacio, dataActuacio, llocActuacio, autocar)
    }

    fun afegirActuacio(actuacio: ActuacioBusModel) {
        //Seleccionem la col.lecció on volem afegir l'actuació mitjançant la funció collection("Actuacions"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim l'actuació a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(actuacio). Si l'actuació existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Actuacions").document(titolActuacio.text.toString()).set(
            hashMapOf(
                "titolActuacio" to titolActuacio.text.toString(),
                "dataActuacio" to dataActuacio.text.toString(), //Atribut dataActuacio amb el valor introduït per l'usuari
                "llocActuacio" to llocActuacio.text.toString() //Atribut llocActuacio amb el valor introduït per l'usuari
            )
        )
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.actuacioCorrect))
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show()//S'ha afegir l'actuació...
            }
            .addOnFailureListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.actuacioWrong))
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show()
            }
        bd.collection("Actuacions").document(actuacio.titolActuacio).collection("Autocar")//Col.lecció
            .document(actuacio.autocar.get(+1).ubicacioBus).
            set(hashMapOf("ubicacioBus" to actuacio.autocar.get(+1).ubicacioBus,
                "horariBus" to actuacio.autocar.get(+1).horariBus,
                "placesBus" to actuacio.autocar.get(+1).placesBus)) //Subcol.lecció
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.actuacioCorrect))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//S'ha afegir l'actuació...
            }
            .addOnFailureListener{ //No s'ha afegit l'actuacio...
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.actuacioWrong))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titolActuacio = binding.titolActuacio
        dataActuacio = binding.dataActuacio
        llocActuacio = binding.llocActuacio
        ubicacioBus = binding.ubicacioAutocar
        horariBus = binding.horaAutocar
        placesBus = binding.placesAutocar
        botoAfegir = binding.botoGuardarActuacio

        autocar = arrayListOf()

        val btnNoBus = requireView().findViewById<Button>(R.id.autocarBoolean)

        btnNoBus.setOnClickListener{
            findNavController().navigate(R.id.action_afegir_actuacio_bus_fragment_to_afegir_actuacio_fragment)
        }

        botoAfegir.setOnClickListener {
            llegirDades()
            var actuacio = llegirDades() //Actuacio introduida per l'usuari
            if (actuacio.titolActuacio?.isNotEmpty() == true && actuacio.dataActuacio?.isNotEmpty() == true && actuacio.llocActuacio?.isNotEmpty() == true) {
                afegirActuacio(actuacio)
            } else {
                Snackbar.make(it, getString(R.string.parametres), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}