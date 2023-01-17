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
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentAfegirAssaigBinding
import cat.copernic.bergants.databinding.FragmentAfegirNoticiaBinding
import cat.copernic.bergants.model.AssaigModel
import cat.copernic.bergants.model.NoticiaModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

@Keep
class AfegirAssaig : Fragment() {
    private lateinit var binding: FragmentAfegirAssaigBinding

    //EditText per introduïr les dades del nou assaig a afegir
    private lateinit var titolAssaig: EditText
    private lateinit var dataAssaig: EditText
    private lateinit var llocAssaig: EditText

    //Atribut de tipus Button per afegir un nou assaig
    private lateinit var botoAfegir: Button

    //Declarem els atributs on guardarem els assajos
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
        //Seleccionem la col.lecció on volem afegir l'assaig mitjançant la funció collection("Assajos"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim l'assaig a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(assaig). Si l'assaig existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Assajos").document(titolAssaig.text.toString()).set(
            hashMapOf(
                "titolAssaig" to titolAssaig.text.toString(),
                "dataAssaig" to dataAssaig.text.toString(), //Atribut dataAssaig amb el valor introduït per l'usuari
                "llocAssaig" to llocAssaig.text.toString() //Atribut llocAssaig amb el valor introduït per l'usuari
            )
        )
            //Aquest codi està afegint oients d'èxit i fracàs a una tasca asíncrona. Si la tasca té
            // èxit, crea un AlertDialog amb un missatge "assaigCorrect" que té un sol botó "Acceptar"
            // i el mostra. A continuació, crida al mètode de notificació amb els valors de "titolAssaig"
            // i "llocAssaig" com a arguments. Si la tasca no té èxit, crea un AlertDialog amb un missatge
            // "assaigWrong" que té un sol botó "Acceptar" i el mostra.
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.assaigCorrect))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()

                notification(titolAssaig.text.toString(), llocAssaig.text.toString())
            }
            .addOnFailureListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.assaigWrong))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titolAssaig = binding.titolAssaig
        dataAssaig = binding.dataAssaig
        llocAssaig = binding.ubicacioAssaig
        botoAfegir = binding.botoGuardarAssaig

        //Aquest codi està configurant un OnClickListener en un botó "botoAfegir". Quan es fa clic al
        // botó, crida a una funció "llegirDades()", que llegeix algunes dades de la interfície d'usuari.
        // A continuació, crea una variable "assaig" i li assigna el valor de retorn de "llegirDades()".
        // A continuació, comprova si les propietats "titolAssaig", "dataAssaig", "llocAssaig" de l'objecte
        // "assaig" no estan buides. Si totes les propietats no estan buides, crida a la funció
        // "afegirAssaig(assaig)" i navega al fragment "assajos_fragment" mitjançant el
        // "findNavController().navigate(R.id.action_afegir_assaig_fragment_to_assajos_fragment)". En cas
        // contrari, mostra un Snackbar amb el missatge "paràmetres" a la pantalla.
        botoAfegir.setOnClickListener {
            llegirDades()
            var assaig = llegirDades() //Assaig introduït per l'usuari
            if (assaig.titolAssaig?.isNotEmpty() == true && assaig.dataAssaig?.isNotEmpty() == true && assaig.llocAssaig?.isNotEmpty() == true) {
                afegirAssaig(assaig)
                findNavController().navigate(R.id.action_afegir_assaig_fragment_to_assajos_fragment)
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