package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.bergants.databinding.FragmentEditarNoticiaBinding
import cat.copernic.bergants.databinding.FragmentNoticiaCanviBinding
import cat.copernic.bergants.model.NoticiaModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.firestore.FirebaseFirestore

@Keep
class editar_noticia : Fragment() {
    private lateinit var binding: FragmentEditarNoticiaBinding

    //EditText per introduïr les dades de la nova noticia a afegir
    private lateinit var titolNoticia: TextView
    private lateinit var contingutNoticia: EditText
    private lateinit var dataNoticia: EditText
    private lateinit var botoEditarNoticia: Button
    private lateinit var botoEliminarNoticia: Button
    private lateinit var noticia: NoticiaModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd = FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore

    private val args by navArgs<editar_noticiaArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentEditarNoticiaBinding.inflate(inflater, container, false)
        var titulo: TextView = binding.editarTitol
        titulo.text = args.currentNoticia.titolNoticia
        binding.editarNoticia.setText(args.currentNoticia.contingutNoticia)
        binding.editarData.setText(args.currentNoticia.dataNoticia)

        return binding.root
    }

    fun llegirDades(): NoticiaModel {
        //Guardem les dades introduïdes per l'usuari
        var titolNoticia = titolNoticia.text.toString()
        var contingutNoticia = contingutNoticia.text.toString()
        var dataNoticia = dataNoticia.text.toString()

        return NoticiaModel(titolNoticia, contingutNoticia, dataNoticia)
    }
    //Funció que modificarà una noticia amb la noticia passada per paràmetre. Per fer la modificació, l'identificador del document
    //passat per paràmetre ha de coincidir amb un dels identificadors dels documents que ja estan afegits a la col.lecció, si no el crearà de nou.
    //Això ho podem fer servir quan necessitem modificar tots els parells clau-valor d'un document que existeix dins la col.lecció.
    fun modificarNoticia(noticia: NoticiaModel){
        bd.collection("Noticies").document(titolNoticia.text.toString()).set(
            hashMapOf(
                "titolNoticia" to titolNoticia.text.toString(),
                "contingutNoticia" to contingutNoticia.text.toString(),
                "dataNoticia" to dataNoticia.text.toString()
            )
        )
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.editNoticia))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//S'ha afegir la noticia...
            }
            .addOnFailureListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.errorEditNoticia))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
            }
    }

    //Eliminar la noticia corresponent al titol passat com a paràmetre. L'eliminarà si existeix.
    fun eliminarNoticia(titolNoticia:String){
        bd.collection("Noticies").document(titolNoticia)
            .delete()
            .addOnSuccessListener {
                //S'ha modificat la noticia...
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.elimNoticia)+" $titolNoticia")
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
            }
            .addOnFailureListener{
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.noElimNoticia))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//No s'ha modificat la noticia...
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titolNoticia = binding.editarTitol
        contingutNoticia = binding.editarNoticia
        dataNoticia = binding.editarData
        botoEditarNoticia = binding.botoEditarNoticia
        botoEliminarNoticia = binding.botoEliminarNoticia

        //Modifiquem tota la noticia si la noticia existeix en la BBDD.
        botoEditarNoticia.setOnClickListener {

            var noticia = llegirDades() //Noticia introduïda per l'usuari

            if (noticia.contingutNoticia?.isNotEmpty() == true && noticia.dataNoticia?.isNotEmpty() == true) {

                //Modifiquem la noticia mitjançant la funció modificarNoticia creada per nosaltres
                modificarNoticia(noticia)
                findNavController().navigate(R.id.action_editar_noticia_to_noticia_fragment)

            } else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.paramMod))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
                //Mostrem un missatge a l'usuari
            }
        }

        //Eliminem la noticia corresponent al codi passat per paràmetre, si aquest existeix en la col.lecció
        botoEliminarNoticia.setOnClickListener {

            var noticia = llegirDades()

                //Eliminem la noticia mitjançant la funció eliminarNoticia creada per nosaltres
                eliminarNoticia(noticia.titolNoticia!!)
                findNavController().navigate(R.id.action_editar_noticia_to_noticia_fragment)

        }

    }
}
