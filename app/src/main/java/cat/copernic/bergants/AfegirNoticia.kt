package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentAfegirNoticiaBinding
import cat.copernic.bergants.databinding.FragmentNoticiaCanviBinding
import cat.copernic.bergants.model.Noticia
import com.google.firebase.firestore.FirebaseFirestore
import org.checkerframework.checker.units.qual.s

class AfegirNoticia : Fragment() {
    private lateinit var binding: FragmentAfegirNoticiaBinding

    //EditText per introduïr les dades de la nova noticia a afegir
    private lateinit var titolNoticia:EditText
    private lateinit var contingutNoticia:EditText
    private lateinit var dataNoticia:EditText

    //Atribut de tipus Button per afegir una nova noticia
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem les noticies
    private lateinit var noticies: Noticia

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd = FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAfegirNoticiaBinding.inflate(inflater, container, false)

        //Inicialitzem els atributs del xml
        titolNoticia = binding.titolNoticia
        contingutNoticia = binding.contingutNoticia
        dataNoticia = binding.dataNoticia
        botoAfegir = binding.botoGuardarNoticia

        botoAfegir.setOnClickListener {
            var noticia = llegirDades() //Noticia introduida per l'usuari

            afegirNoticia(noticia)
            //En el nostre cas, els camps cal que tinguin contingut
        /**    if(noticia.titolNoticia?.isNotEmpty() == true){
                if(noticia.contingutNoticia?.isNotEmpty() == true){
                    if(noticia.dataNoticia?.isNotEmpty() == true){
                        afegirNoticia(noticia)
                    } else{
                        //Mostrem un missatge a l'usuari mitjançant un Toast
                        Toast.makeText(requireActivity(),"Cal introduïr una data per la nova notícia",
                            Toast.LENGTH_LONG).show()
                    }
                } else{
                    //Mostrem un missatge a l'usuari mitjançant un Toast
                    Toast.makeText(requireActivity(),"Cal introduïr contingut per la nova notícia",
                        Toast.LENGTH_LONG).show()
                }
            } else{
                //Mostrem un missatge a l'usuari mitjançant un Toast
                Toast.makeText(requireActivity(),"Cal introduïr un títol per la nova notícia",
                    Toast.LENGTH_LONG).show()
            }
        */
        }



        return binding.root
    }

    fun llegirDades():Noticia{
        //Guardem les dades introduïdes per l'usuari
        var titolNoticia = titolNoticia.text.toString()
        var contingutNoticia = contingutNoticia.text.toString()
        var dataNoticia = dataNoticia.text.toString()

        return Noticia(titolNoticia, contingutNoticia, dataNoticia)
    }

    fun afegirNoticia(noticia: Noticia){
        //Seleccionem la col.lecció on volem afegir la notícia mitjançant la funció collection("Noticies"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim la notícia a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(departament). Si el departament existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Notícies").add(noticia)
            .addOnSuccessListener { //S'ha afegir la noticia...
                Toast.makeText(requireActivity(),"La notícia s'ha afegit correctament", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(requireActivity(),"La notícia no s'ha afegit", Toast.LENGTH_LONG).show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSaveNot = requireView().findViewById<Button>(R.id.botoGuardarNoticia)

        btnSaveNot.setOnClickListener{
        }
    }
}