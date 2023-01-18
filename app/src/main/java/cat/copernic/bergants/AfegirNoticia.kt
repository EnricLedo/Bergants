package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentAfegirNoticiaBinding
import cat.copernic.bergants.model.NoticiaModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

@Keep
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

    /**

    Mètode que s'executa quan es crea la vista del fragment.

    @param inflater inflador per inflar la vista del fragment

    @param container contenidor on s'afegirà la vista

    @param savedInstanceState estat desat anteriorment (pot ser null)

    @return retorna la vista generada
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAfegirNoticiaBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**

    Aquesta funció llegeix les dades introduïdes per l'usuari i les guarda en variables.
    Després, aquestes dades són passades a un objecte de la classe NoticiaModel.
    @return Retorna un objecte de la classe NoticiaModel amb les dades introduïdes per l'usuari.
     */
    fun llegirDades(): NoticiaModel {
        //Guardem les dades introduïdes per l'usuari
        var titolNoticia = titolNoticia.text.toString()
        var contingutNoticia = contingutNoticia.text.toString()
        var dataNoticia = dataNoticia.text.toString()

        return NoticiaModel(titolNoticia, contingutNoticia, dataNoticia)
    }

    /**

    Aquesta funció afegeix una notícia a la base de dades mitjançant Firestore.
    @param noticia: Objecte de la classe NoticiaModel que conté les dades de la notícia a afegir.
     */
    fun afegirNoticia(noticia: NoticiaModel) {
        //Seleccionem la col.lecció on volem afegir la notícia mitjançant la funció collection("Noticies"), si no existeix la col.lecció
        //es crearà, si no la sobreescriurà. Afegim la notícia a la col.lecció seleccionada amb un id que genera automàticament Firestore
        // mitjançant la funció add(noticia). Si la notícia existeix, es sobreescriurà, sinó es crearà de nou.
        bd.collection("Noticies").document(titolNoticia.text.toString()).set(
            hashMapOf(
                "titolNoticia" to titolNoticia.text.toString(), //Atribut titolNoticia amb el valor introduït per l'usuari
                "contingutNoticia" to contingutNoticia.text.toString(), //Atribut contingutNoticia amb el valor introduït per l'usuari
                "dataNoticia" to dataNoticia.text.toString() //Atribut dataNoticia amb el valor introduït per l'usuari
            )
        )
            //Aquest codi està afegint oients d'èxit i fracàs a una tasca asíncrona. Si la tasca té
            // èxit, crea un AlertDialog amb un missatge "assaigCorrect" que té un sol botó "Acceptar"
            // i el mostra. A continuació, crida al mètode de notificació amb els valors de "titolAssaig"
            // i "llocAssaig" com a arguments. Si la tasca no té èxit, crea un AlertDialog amb un missatge
            // "assaigWrong" que té un sol botó "Acceptar" i el mostra.
            .addOnSuccessListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.noticiaProva))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()//S'ha afegir la noticia...
                notification(titolNoticia.text.toString(), contingutNoticia.text.toString())

            }
            .addOnFailureListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.noticiaWrong))
                builder.setPositiveButton(getString(R.string.aceptar), null)
                val dialog = builder.create()
                dialog.show()
            }
    }

    /**

    Aquest mètode s'executa quan la vista s'ha creat. Es configura un onClickListener per al botó "botoAfegir",
    que recull les dades introduïdes per l'usuari, les valida i les afegeix a la base de dades mitjançant Firestore.
    També navega l'usuari a un altre fragment.
    @param view: Vista associada al fragment.
    @param savedInstanceState: Bundle amb l'estat guardat anteriorment de la vista.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titolNoticia = binding.titolNoticia
        contingutNoticia = binding.contingutNoticia
        dataNoticia = binding.dataNoticia
        botoAfegir = binding.botoGuardarNoticia

        //Aquest codi està configurant un onClickListener per a un botó (botoAfegir) a Kotlin. Quan
        // es fa clic al botó, el codi executarà primer la funció "llegirDades()" que recull l'entrada
        // de l'usuari d'un formulari i crea una instància de la classe "NoticiaModel".
        //Aleshores comprovarà que tots els camps obligatoris del NoticiaModel no estiguin buits, si
        // és així, cridarà a la funció "afegirNoticia(noticia)" i navegarà l'usuari a la pantalla
        // "noticia_fragment". Si algun dels camps està buit, mostrarà un missatge de Snackbar amb la
        // cadena "paràmetres", que probablement sigui un missatge d'error indicant a l'usuari que ompli
        // els camps obligatoris.
        botoAfegir.setOnClickListener {
            llegirDades()
            var noticia = llegirDades() //Noticia introduida per l'usuari
            if (noticia.titolNoticia?.isNotEmpty() == true && noticia.contingutNoticia?.isNotEmpty() == true && noticia.dataNoticia?.isNotEmpty() == true) {
                afegirNoticia(noticia)
                findNavController().navigate(R.id.action_afegirNoticia_to_noticia_fragment)
            } else {
                Snackbar.make(it, getString(R.string.parametres), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    /**

    Aquesta funció crea una notificació amb el títol i el contingut passats per paràmetre.
    @param titol: Títol de la notificació.
    @param contingut: Contingut de la notificació.
     */
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