package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentActuacioBinding
import cat.copernic.bergants.databinding.FragmentAfegirActuacioBinding
import cat.copernic.bergants.databinding.FragmentAfegirAssaigBinding
import cat.copernic.bergants.model.ActuacioModel
import cat.copernic.bergants.model.AssaigModel
import cat.copernic.bergants.model.BusModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

@Keep
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
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()

                notification(titolActuacio.text.toString(), llocActuacio.text.toString())
            }
            .addOnFailureListener {
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
        botoAfegir = binding.botoGuardarActuacio

        //Hi ha dos botons, un amb l'identificador "autocarBoolean" i l'altre "botoAfegir"
        //
        //Quan l'usuari fa clic al botó amb l'identificador "autocarBoolean" navega a un altre fragment
        // anomenat "afegir_actuacio_bus_fragment".
        //Quan l'usuari fa clic al botó "botoAfegir" crida al mètode llegirDades() que llegeix les dades
        // introduïdes per l'usuari. A continuació, comprova si s'han omplert els camps "titolActuacio",
        // "dataActuacio" i "llocActuacio". Si s'han omplert, crida al mètode afegirActuacio(actuacio) i
        // navega a un altre fragment anomenat "actuacions_fragment". Si els camps no s'han omplert,
        // mostra un missatge de Snackbar que diu "paràmetres" que sembla ser un missatge en castellà
        // que indica a l'usuari que els paràmetres no s'han omplert.
        val btnAddBus = requireView().findViewById<Button>(R.id.autocarBoolean)

        btnAddBus.setOnClickListener{
            findNavController().navigate(R.id.action_afegir_actuacio_fragment_to_afegir_actuacio_bus_fragment)
        }

        botoAfegir.setOnClickListener {
            llegirDades()
            var actuacio = llegirDades() //Actuacio introduida per l'usuari
            if (actuacio.titolActuacio?.isNotEmpty() == true && actuacio.dataActuacio?.isNotEmpty() == true && actuacio.llocActuacio?.isNotEmpty() == true) {
                afegirActuacio(actuacio)
                findNavController().navigate(R.id.action_afegir_actuacio_fragment_to_actuacions_fragment)
            } else {
                Snackbar.make(it, getString(R.string.parametres), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun notification(titol:String, contingut:String) {
        val notification = NotificationCompat.Builder(requireContext(),"1").also{ noti ->
            noti.setContentTitle(titol)
            noti.setContentText(contingut)
            noti.setSmallIcon(R.drawable.logo_bergants)
        }.build()
        //Aquest codi està creant un objecte NotificationManagerCompat i utilitza el mètode
        // "from" per inicialitzar-lo amb el context actual. A continuació, utilitza el mètode
        // "notificar" per mostrar una notificació amb un identificador 1 i l'objecte "notificació"
        // com a contingut.
        val notificationManageer = NotificationManagerCompat.from(requireContext())
        notificationManageer.notify(1,notification)
    }
}