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
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentAfegirActuacioBusBinding
import cat.copernic.bergants.model.ActuacioBusModel
import cat.copernic.bergants.model.BusModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

@Keep
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

    /**
     *
     * Aquesta funció es crida quan es crea la vista del fragment. Infla el disseny
     * i el defineix a l'objecte d'enllaç de visualització.
     *
     * @param inflater : l'objecte LayoutInflater que es pot utilitzar per inflar qualsevol vista del fragment.
     * @param container : la vista principal a la qual s'ha d'adjuntar la interfície d'usuari del fragment.
     * @param savedInstanceState : aquest és l'estat de la instància desada del fragment.
     *
     * @return La vista inflada.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentAfegirActuacioBusBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * Aquesta funció llegeix les dades introduïdes per l'usuari des de la IU i retorna un objecte de
     * ActuacioBusModel que conté les dades introduïdes.
     *
     * @return un objecte d'ActuacioBusModel que conté les dades introduïdes.
     */
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

    /**
     *
     * Aquesta funció afegeix l'objecte ActuacioBusModel passat a la col·lecció "Actuacions" de Firebase Firestore
     * i crea una subcol·lecció "Autocar" on s'afegeix l'objecte BusModel. També mostra un diàleg d'alerta
     * amb un missatge que indica l'èxit o el fracàs de l'operació.
     *
     * @param actuacio : l'objecte ActuacioBusModel que s'afegirà a la col·lecció Firestore.
     * @param bd : aquesta és la instància de Firebase Firestore.
     *
     */
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
            //Aquest codi està configurant un OnFailureListener per a alguna tasca.
            // Quan la tasca falla, es crea un diàleg d'alerta amb un missatge del recurs
            // de cadena "actuacioWrong" i un botó "Acceptar". A continuació, es mostra el
            // diàleg a la pantalla.
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

    /**
     * Aquesta classe es crida quan es crea la vista del fragment.
     * Es vinculen les variables dels diferents elements del layout amb les variables de la classe.
     * A més, es configura un botó per afegir les dades introduides per l'usuari a la base de dades i un altre per anar a un altre fragment.
     * Si algun dels camps estan buits, mostra un Snackbar amb un missatge d'error.
     *
     * @param view : Vista del fragment.
     * @param savedInstanceState : estat guardat de l'instancia del fragment.
     * @param binding : Objecte de l'enllaç de la vista del fragment.
     * @param titolActuacio : variable que guarda el valor del camp títol de l'actuació.
     * @param dataActuacio : variable que guarda el valor del camp data de l'actuació.
     * @param llocActuacio : variable que guarda el valor del camp lloc de l'actuació.
     * @param ubicacioBus : variable que guarda el valor del camp ubicació de l'autocar.
     * @param horariBus : variable que guarda el valor del camp horari de l'autocar.
     * @param placesBus : variable que guarda el valor del camp places de l'autocar.
     * @param botoAfegir : variable que guarda el botó d'afegir.
     * @param autocar : Llista on es guarden les dades de l'autocar.
     * @param bd : Instancia de Firebase Firestore.
     *
     */
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

        //Aquest codi configura dos oients de clic per a dos botons diferents en un fragment.
        // El primer botó, "btnNoBus", quan es fa clic, navega a l'usuari a un fragment diferent
        // mitjançant el component de navegació. El segon botó, "botoAfegir", quan es fa clic, primer
        // crida a la funció "llegirDades()" que llegeix algunes dades i les emmagatzema a la variable
        // "actuacio". A continuació, comprova si els camps de títol, data i ubicació de l'objecte
        // "actuacio" no estan buits. Si no ho són, crida a la funció "afegirActuacio(actuacio)" que
        // afegeix l'actuacio al repositori i després navega l'usuari a un altre fragment. Si algun dels
        // camps està buit, mostra un Snackbar amb un missatge del recurs de cadena "paràmetres".
        val btnNoBus = requireView().findViewById<Button>(R.id.autocarBoolean)

        btnNoBus.setOnClickListener{
            findNavController().navigate(R.id.action_afegir_actuacio_bus_fragment_to_afegir_actuacio_fragment)
        }

        botoAfegir.setOnClickListener {
            llegirDades()
            var actuacio = llegirDades() //Actuacio introduida per l'usuari
            if (actuacio.titolActuacio?.isNotEmpty() == true && actuacio.dataActuacio?.isNotEmpty() == true && actuacio.llocActuacio?.isNotEmpty() == true) {
                afegirActuacio(actuacio)
                findNavController().navigate(R.id.action_afegir_actuacio_bus_fragment_to_actuacions_fragment)
            } else {
                Snackbar.make(it, getString(R.string.parametres), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}