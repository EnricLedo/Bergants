package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cat.copernic.bergants.databinding.FragmentAfegirMembreBinding
import cat.copernic.bergants.model.MembreModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class afegirMembre : Fragment() {
    private lateinit var binding: FragmentAfegirMembreBinding

    //EditText per introduïr les dades del nou membre a afegir
    private lateinit var nomMembre: EditText
    private lateinit var malnom: EditText
    private lateinit var alcadaEspatlles: EditText
    private lateinit var alcadaMans: EditText
    private lateinit var correuMembre: EditText
    private lateinit var adrecaMembre: EditText
    private lateinit var telefonMembre: EditText
    private lateinit var rolMembre: EditText

    //Atribut de tipus Button per afegir un nou membre
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem els membres
    private lateinit var membres: MembreModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentAfegirMembreBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun llegirDades(): MembreModel {
        //Guardem les dades introduïdes per l'usuari
        var nomMembre = nomMembre.text.toString()
        var malnom = malnom.text.toString()
        var alcadaEspatlles = alcadaEspatlles.text.toString()
        var alcadaMans = alcadaMans.text.toString()
        var correuMembre = correuMembre.text.toString()
        var adrecaMembre = adrecaMembre.text.toString()
        var telefonMembre = telefonMembre.text.toString()
        var rolMembre = rolMembre.text.toString()

        return MembreModel(nomMembre, malnom, alcadaEspatlles, alcadaMans, correuMembre,
            adrecaMembre, telefonMembre, rolMembre)
    }

    fun afegirMembre(membre: MembreModel) {
        //Seleccionem la col.lecció on volem afegir el Membre mitjançant la funció collection("Membres"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim el membre a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(membre). Si el membre existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Membres").document(nomMembre.text.toString()).set(
            hashMapOf(
                "nomMembre" to nomMembre.text.toString(),
                "malnom" to malnom.text.toString(),
                "alcadaEspatlles" to alcadaEspatlles.text.toString(),
                "alcadaMans" to alcadaMans.text.toString(),
                "correuMembre" to correuMembre.text.toString(),
                "adrecaMembre" to adrecaMembre.text.toString(),
                "telefonMembre" to telefonMembre.text.toString(),
                "rolMembre" to rolMembre.text.toString()
            )
        )
            .addOnSuccessListener { //S'ha afegir l'assaig...
                Toast.makeText(
                    requireActivity(),
                    "El membre s'ha afegit correctament",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "El membre no s'ha afegit", Toast.LENGTH_LONG)
                    .show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nomMembre = binding.nomMembre
        malnom = binding.malnomMembre
        alcadaEspatlles = binding.espatllesMembre
        alcadaMans = binding.mansMembre
        correuMembre = binding.correuMembre
        adrecaMembre = binding.adrecaMembre
        telefonMembre = binding.telefonMembre
        rolMembre = binding.rolMembre
        botoAfegir = binding.botoGuardarMembre

        botoAfegir.setOnClickListener {
            llegirDades()
            var membre = llegirDades() //Membre introduit per l'usuari
            if (membre.nomMembre?.isNotEmpty() == true && membre.malnom?.isNotEmpty() == true && membre.alcadaEspatlles?.isNotEmpty() == true
                && membre.alcadaMans?.isNotEmpty() == true && membre.correuMembre?.isNotEmpty() == true
                && membre.adrecaMembre?.isNotEmpty() == true && membre.telefonMembre?.isNotEmpty() == true
                && membre.rolMembre?.isNotEmpty() == true ) {
                afegirMembre(membre)
            } else {
                Snackbar.make(it, "Falta indroduir parametres!!!", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}