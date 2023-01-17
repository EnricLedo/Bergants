package cat.copernic.bergants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.Membre
import cat.copernic.bergants.databinding.DisenyMembreBinding
import coil.api.load
import com.google.firebase.storage.FirebaseStorage

@Keep
class MembreRecyclerAdapter: RecyclerView.Adapter<MembreRecyclerAdapter.ViewHolder>() {
    var membres: MutableList<Membre> = ArrayList()
    lateinit var context: Context

    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun MembreRecyclerAdapter(membreList:MutableList<Membre>, contxt: Context){
        this.membres = membreList
        this.context = contxt
    }

    //és l'encarregat de retornar el ViewHolder ja configurat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            DisenyMembreBinding.inflate(
                layoutInflater, parent, false
            )
        )
    }

    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Aquest codi s'utilitza per vincular dades a les vistes en un RecyclerView. El bloc with(holder)
        // s'utilitza per accedir a les propietats i mètodes de l'objecte ViewHolder, en aquest cas,
        // s'utilitza per configurar el text del nomMembre TextView i carregar la imatge des del
        // fotoMembre ImageView.
        //S'utilitza el bloc with(membres.get(position)) per accedir a les propietats de l'objecte
        // Membre a la posició donada.
        //S'està utilitzant el FirebaseStorage per descarregar la imatge del Firebase Storage i
        // configurar-la al fotoMembre ImageView.
        //També està cridant al mètode bind(item) a l'objecte titular, aquest mètode probablement es
        // defineix a la classe Adapter i s'utilitza per vincular les dades de l'element a les vistes
        // corresponents del fitxer de disseny.
        //S'utilitza per vincular dades a les vistes en un RecyclerView, en aquest cas, s'utilitza per
        // establir el text i la imatge de les vistes amb les dades corresponents de l'objecte Membre
        // a la posició donada.
        with(holder){
            with(membres.get(position)){
                binding.nomMembre.text = this.nomMembre
                binding.fotoMembre.load(this.fotoMembre)

                 //Monstrar la imatge des de Storage de Firebase
                 val storageRef = FirebaseStorage.getInstance().reference
                 val imageRef = storageRef.child("rv/${this.nomMembre}")
                 imageRef.downloadUrl.addOnSuccessListener { url ->
                     binding.fotoMembre.load(url)
                 }.addOnFailureListener {
                     //mostrar error
                 }
            }
        }
        val item = membres.get(position)
        holder.bind(item)

        //estamblim un listener
        holder.itemView.setOnClickListener {
        }
    }

    //La funció getItemCount() és responsable de retornar el nombre total d'elements del conjunt de
    // dades que té l'adaptador. RecyclerView l'utilitza per determinar quants elements es mostraran.
    // En aquest cas, retorna la mida de la llista de membres, que és el conjunt de dades que manté
    // l'adaptador. Això vol dir que RecyclerView mostrarà el mateix nombre d'elements que hi ha a la
    // llista de membres.
    override fun getItemCount(): Int {
        return membres.size
    }


    //La classe ViewHolder és una implementació personalitzada de la classe RecyclerView.ViewHolder
    // per mostrar elements en un RecyclerView. Es necessita un sol argument,
    // "val binding: DisenyMembreBinding", que és una instància de la classe DisenyMembreBinding, que
    // s'utilitza per enllaçar les dades al RecyclerView. El mètode bind() s'utilitza per vincular les
    // dades d'un objecte Membre al disseny del ViewHolder. El constructor RecyclerView.ViewHolder es
    // crida amb l'argument binding.root, aquesta és la vista arrel del disseny que representa el
    // ViewHolder.
    class ViewHolder(val binding: DisenyMembreBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(membre: Membre) {

        }

    }

}
