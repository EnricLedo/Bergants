package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cat.copernic.bergants.databinding.FragmentAfegirNoticiaBinding
import cat.copernic.bergants.model.NoticiaModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class AfegirNoticia : Fragment() {
    private lateinit var binding: FragmentAfegirNoticiaBinding

    //EditText per introduïr les dades de la nova noticia a afegir
    private lateinit var titolNoticia: EditText
    private lateinit var contingutNoticia: EditText
    private lateinit var dataNoticia: EditText

    //Atribut de tipus Button per afegir una nova noticia
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem les noticies
    private lateinit var noticies: NoticiaModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAfegirNoticiaBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun llegirDades(): NoticiaModel {
        //Guardem les dades introduïdes per l'usuari
        var titolNoticia = titolNoticia.text.toString()
        var contingutNoticia = contingutNoticia.text.toString()
        var dataNoticia = dataNoticia.text.toString()

        return NoticiaModel(titolNoticia, contingutNoticia, dataNoticia)
    }

    fun afegirNoticia(noticia: NoticiaModel) {
        //Seleccionem la col.lecció on volem afegir la notícia mitjançant la funció collection("Noticies"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim la notícia a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(noticia). Si la notícia existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Noticies").add(noticia)
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

        titolNoticia = binding.titolNoticia
        contingutNoticia = binding.contingutNoticia
        dataNoticia = binding.dataNoticia
        botoAfegir = binding.botoGuardarNoticia

        botoAfegir.setOnClickListener {
            llegirDades()
            var noticia = llegirDades() //Noticia introduida per l'usuari
            if (noticia.titolNoticia?.isNotEmpty() == true && noticia.contingutNoticia?.isNotEmpty() == true && noticia.dataNoticia?.isNotEmpty() == true) {
                afegirNoticia(noticia)
            } else {
                Snackbar.make(it, "Falta indroduir parametres!!!", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}