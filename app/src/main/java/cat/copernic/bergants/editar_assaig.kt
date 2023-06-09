package cat.copernic.bergants

import android.os.Bundle
import android.util.Log
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
import cat.copernic.bergants.databinding.FragmentEditarAssaigBinding
import cat.copernic.bergants.databinding.FragmentEditarNoticiaBinding
import cat.copernic.bergants.databinding.FragmentInformacioAssaigBinding
import cat.copernic.bergants.model.AssaigModel
import cat.copernic.bergants.model.NoticiaModel
import com.google.firebase.firestore.FirebaseFirestore

@Keep
class editar_assaig : Fragment() {
    private lateinit var binding: FragmentEditarAssaigBinding

    //EditText per introduïr les dades de la nova noticia a afegir
    private lateinit var titolAssaig: TextView
    private lateinit var dataAssaig: EditText
    private lateinit var llocAssaig: EditText
    private lateinit var botoEditarAssaig: Button
    private lateinit var botoEliminarAssaig: Button
    private lateinit var assaig: AssaigModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore


    /**

    Aquesta funció infla el layout associat a aquest fragment i el retorna com a View.
    @param inflater inflador de layout
    @param container contenidor del layout
    @param savedInstanceState estat de l'instancia guardada
    @return Retorna la vista del layout associat a aquest fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditarAssaigBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**

    Aquesta funció llegeix les dades introduides per l'usuari i les retorna en un objecte AssaigModel.
    @return Retorna un objecte AssaigModel amb les dades introduides per l'usuari.
     */
    fun llegirDades(): AssaigModel {
        //Guardem les dades introduïdes per l'usuari
        var titolAssaig = titolAssaig.text.toString()
        var dataAssaig = dataAssaig.text.toString()
        var llocAssaig = llocAssaig.text.toString()

        return AssaigModel(titolAssaig, dataAssaig, llocAssaig)
    }

    //Funció que modificarà un assaig amb l'assaig passat per paràmetre. Per fer la modificació, l'identificador del document
    //passat per paràmetre ha de coincidir amb un dels identificadors dels documents que ja estan afegits a la col.lecció, si no el crearà de nou.
    //Això ho podem fer servir quan necessitem modificar tots els parells clau-valor d'un document que existeix dins la col.lecció.

    /**

    Aquesta classe modifica un assaig existent en la base de dades amb un nou títol, data i lloc.
    @param assaig L'{@link AssaigModel} que es vol modificar.
     */
    fun modificarAssaig(assaig: AssaigModel){
        bd.collection("Assajos").document(titolAssaig.text.toString()).set(
            hashMapOf(
                "titolAssaig" to titolAssaig.text.toString(),
                "dataAssaig" to dataAssaig.text.toString(),
                "llocAssaig" to llocAssaig.text.toString()
            )
        )
            //Aquest codi està afegint oients d'èxit i fracàs a una tasca asíncrona. Si la tasca té
            // èxit, crea un AlertDialog amb un missatge "assaigCorrect" que té un sol botó "Acceptar"
            // i el mostra. A continuació, crida al mètode de notificació. Si la tasca no té èxit,
            // crea un AlertDialog amb un missatge que té un sol botó "Acceptar" i el mostra.
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.assaigEdit))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//S'ha afegit l'assaig...
            }
            .addOnFailureListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.assaigNoEdit))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
            }
    }

    //Eliminar l'assaig corresponent al titol passat com a paràmetre. L'eliminarà si existeix.
    /**

    Aquesta funció elimina un assaig de la base de dades a través del seu títol.
    @param titolAssaig Títol de l'assaig a eliminar.
     */
    fun eliminarAssaig(titolAssaig:String){
        bd.collection("Assajos").document(titolAssaig)
            .delete()
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.titolElim)+" $titolAssaig")
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//S'ha modificat l'assaig...
            }
            .addOnFailureListener{
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.assaigNoElim4))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//No s'ha modificat l'assaig...
            }
    }

    /**

    Aquest mètode és cridat quan s'ha creat la vista del fragment.
    Inicialitza les variables de la classe, obté els arguments passats al fragment i configura el comportament dels botons d'editar i eliminar.
    @param view Vista del fragment
    @param savedInstanceState Bundle amb dades de l'estat anterior del fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titolAssaig = binding.titolAssaig
        dataAssaig = binding.dataAssaig
        llocAssaig = binding.ubicacioAssaig
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
                findNavController().navigate(R.id.action_editar_assaig_to_assajos_fragment)

            } else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.paramMod))
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show() //Mostrem un missatge a l'usuari
            }
        }

        //Eliminem l'assaig corresponent al codi passat per paràmetre, si aquest existeix en la col.lecció
        botoEliminarAssaig.setOnClickListener {

            var assaig = llegirDades()

            if (assaig.titolAssaig?.isNotEmpty() == true) {
                //Eliminem l'assaig mitjançant la funció eliminarAssaig creada per nosaltres
                eliminarAssaig(assaig.titolAssaig!!)
                findNavController().navigate(R.id.action_editar_assaig_to_assajos_fragment)

            }else{
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.titolElimAssaig))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                //Mostrem un missatge a l'usuari
            }
        }
    }
}
