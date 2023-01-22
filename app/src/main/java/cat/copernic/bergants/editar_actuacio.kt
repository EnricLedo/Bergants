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
import cat.copernic.bergants.databinding.FragmentEditarActuacioBinding
import cat.copernic.bergants.model.ActuacioModel
import com.google.firebase.firestore.FirebaseFirestore

@Keep
class editar_actuacio : Fragment() {
    private lateinit var binding: FragmentEditarActuacioBinding

    //EditText per introduïr les dades de la nova noticia a afegir
    private lateinit var titolActuacio: TextView
    private lateinit var dataActuacio: EditText
    private lateinit var llocActuacio: EditText
    private lateinit var botoEditarActuacio: Button
    private lateinit var botoEliminarActuacio: Button
    private lateinit var actuacio: ActuacioModel

    //Declarem i incialitzem un atribut de tipus FirebaseFirestore, classe on trobarem els mètodes per treballar amb la base de dades Firestore
    private var bd =
        FirebaseFirestore.getInstance() //Inicialitzem mitjançant el mètode getInstance() de FirebaseFirestore


    /**

    Aquest mètode s'executa quan la vista del fragment es crea. S'utilitza per inflar el layout associat al fragment
    i assignar-lo a una variable de binding.
    @param inflater: LayoutInflater per inflar el layout.
    @param container: Contenidor del layout.
    @param savedInstanceState: Bundle amb l'estat guardat anteriorment de la vista.
    @return Retorna la vista del layout inflada.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditarActuacioBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**

    Aquesta funció recull les dades introduïdes per l'usuari en un formulari i crea una instància de la classe "ActuacioModel".
    @return Retorna un objecte de la classe ActuacioModel amb les dades introduïdes per l'usuari.
     */
    fun llegirDades(): ActuacioModel {
        //Guardem les dades introduïdes per l'usuari
        var titolActuacio = titolActuacio.text.toString()
        var dataActuacio = dataActuacio.text.toString()
        var llocActuacio = llocActuacio.text.toString()

        return ActuacioModel(titolActuacio, dataActuacio, llocActuacio)
    }

    //Funció que modificarà una actuacio amb l'actuacio passada per paràmetre. Per fer la modificació, l'identificador del document
    //passat per paràmetre ha de coincidir amb un dels identificadors dels documents que ja estan afegits a la col.lecció, si no el crearà de nou.
    //Això ho podem fer servir quan necessitem modificar tots els parells clau-valor d'un document que existeix dins la col.lecció.

    /**

    Aquesta funció modifica una actuació existent a la base de dades Firestore.
    Utilitza les dades introduïdes per l'usuari per crear un nou objecte de la classe "ActuacioModel"
    i sobreescriu la informació de l'actuació existent amb aquestes dades.
    @param actuacio: Objecte de la classe ActuacioModel amb les dades introduïdes per l'usuari.
     */
    fun modificarActuacio(acutacio: ActuacioModel){
        bd.collection("Actuacions").document(titolActuacio.text.toString()).set(
            hashMapOf(
                "titolActuacio" to titolActuacio.text.toString(),
                "dataActuacio" to dataActuacio.text.toString(),
                "llocActuacio" to llocActuacio.text.toString()
            )
        )
            //Aquest codi està afegint oients d'èxit i fracàs a una tasca asíncrona. Si la tasca té
            // èxit, crea un AlertDialog amb un missatge "assaigCorrect" que té un sol botó "Acceptar"
            // i el mostra. A continuació, crida al mètode de notificació. Si la tasca no té èxit,
            // crea un AlertDialog amb un missatge que té un sol botó "Acceptar" i el mostra.
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.actuacioEdit))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//S'ha afegit l'actuacio...
            }
            .addOnFailureListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.actuacioNoEdit))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
            }
    }

    //Eliminar l'actuacio corresponent al titol passat com a paràmetre. L'eliminarà si existeix.
    /**

    Aquesta funció elimina una actuació de la base de dades a partir del seu títol.
    @param titolActuacio Títol de l'actuació a eliminar.
     */
    fun eliminarActuacio(titolActuacio:String){
        bd.collection("Actuacions").document(titolActuacio)
            .delete()
            //Aquest codi està afegint oients d'èxit i fracàs a una tasca asíncrona. Si la tasca té
            // èxit, crea un AlertDialog amb un missatge "actuacioCorrect" que té un sol botó "Acceptar"
            // i el mostra. A continuació, crida al mètode de notificació. Si la tasca no té èxit,
            // crea un AlertDialog amb un missatge que té un sol botó "Acceptar" i el mostra.
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.eliminarActuacio)+" $titolActuacio")
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//S'ha modificat l'actuacio...
            }
            .addOnFailureListener{
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.eliminarNoActuacio))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//No s'ha modificat l'actuacio...
            }
    }

    /**

    Aquesta classe extén de Fragment i s'encarrega de mostrar la vista d'edició d'una actuació
    i de fer les operacions necessàries per a modificar o eliminar una actuació de la base de dades.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titolActuacio = binding.titolActuacio
        dataActuacio = binding.dataActuacio
        llocActuacio = binding.llocActuacio
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
        binding.llocActuacio.setText(args.ubicacioActuacio)

        //Modifiquem tota l'actuacio si l'actuacio existeix en la BBDD.
        botoEditarActuacio.setOnClickListener {

            var actuacio = llegirDades() //Actuacio introduïda per l'usuari

            if (actuacio.dataActuacio?.isNotEmpty() == true && actuacio.llocActuacio?.isNotEmpty() == true) {

                //Modifiquem l'assaig mitjançant la funció modificarActuacio creada per nosaltres
                modificarActuacio(actuacio)
                findNavController().navigate(R.id.action_editar_actuacio_to_actuacions_fragment)

            } else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.paramMod))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
                //Mostrem un missatge a l'usuari
            }
        }

        //Eliminem l'actuacio corresponent al codi passat per paràmetre, si aquest existeix en la col.lecció
        botoEliminarActuacio.setOnClickListener {

            var actuacio = llegirDades()

            //Eliminem l'actuacio mitjançant la funció eliminarActuacio creada per nosaltres
                eliminarActuacio(actuacio.titolActuacio!!)
                findNavController().navigate(R.id.action_editar_actuacio_to_actuacions_fragment)

        }
    }
}
