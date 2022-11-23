package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentAfegirAssaigBinding
import cat.copernic.bergants.databinding.FragmentAfegirNoticiaBinding
import cat.copernic.bergants.model.AssaigModel
import cat.copernic.bergants.model.NoticiaModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class AfegirAssaig : Fragment() {
    private lateinit var binding: FragmentAfegirAssaigBinding

    //EditText per introduïr les dades de la nova noticia a afegir
    private lateinit var titolAssaig: EditText
    private lateinit var dataAssaig: EditText
    private lateinit var llocAssaig: EditText

    //Atribut de tipus Button per afegir una nova noticia
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem les noticies
    private lateinit var assajos: AssaigModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentAfegirAssaigBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun llegirDades(): AssaigModel {
        //Guardem les dades introduïdes per l'usuari
        var titolAssaig = titolAssaig.text.toString()
        var dataAssaig = dataAssaig.text.toString()
        var llocAssaig = llocAssaig.text.toString()

        return AssaigModel(titolAssaig, dataAssaig, llocAssaig)
    }

    fun afegirAssaig(assaig: AssaigModel) {
        //Seleccionem la col.lecció on volem afegir la notícia mitjançant la funció collection("Noticies"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim la notícia a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(departament). Si el departament existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Assajos").add(assaig)
            .addOnSuccessListener { //S'ha afegir la noticia...
                Toast.makeText(
                    requireActivity(),
                    "La notícia s'ha afegit correctament",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "La notícia no s'ha afegit", Toast.LENGTH_LONG)
                    .show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titolAssaig = binding.titolAssaig
        dataAssaig = binding.dataAssaig
        llocAssaig = binding.ubicacioAssaig
        botoAfegir = binding.botoGuardarAssaig

        botoAfegir.setOnClickListener {
            llegirDades()
            var assaig = llegirDades() //Noticia introduida per l'usuari
            if (assaig.titolAssaig?.isNotEmpty() == true && assaig.dataAssaig?.isNotEmpty() == true && assaig.llocAssaig?.isNotEmpty() == true) {
                afegirAssaig(assaig)
            } else {
                Snackbar.make(it, "Falta indroduir parametres!!!", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}