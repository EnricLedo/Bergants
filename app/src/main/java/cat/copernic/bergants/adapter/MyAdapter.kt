package cat.copernic.bergants.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Keep
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.R
import cat.copernic.bergants.model.AssaigModel

@Keep
class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val AssaigList = ArrayList<AssaigModel>()

    //Aquesta és una implementació del mètode onCreateViewHolder de la classe RecyclerView.Adapter.
    // Aquest mètode és responsable de crear i retornar un objecte ViewHolder quan el RecyclerView en
    // necessita un de nou.
    //El mètode pren dos arguments:
    //
    //pare: ViewGroup: el ViewGroup pare al qual s'afegirà la nova vista.
    //viewType: Int: el tipus de vista de la nova vista, que s'utilitza generalment quan hi ha diversos
    // tipus de vista al RecyclerView.
    //El mètode primer infla el disseny definit per R.layout.diseny_assaig mitjançant
    // LayoutInflater.from(parent.context), aquest inflador utilitza el context de la vista principal
    // per inflar el disseny. La vista inflada es passa com a argument al constructor de la classe
    // personalitzada ViewHolder MyViewHolder i es retorna.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.diseny_assaig,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    //Aquesta és una implementació del mètode onBindViewHolder de la classe RecyclerView.Adapter.
    // quest mètode és responsable d'enllaçar dades al ViewHolder en una posició específica del
    // RecyclerView.
    //Es requereixen dos arguments:
    //
    //titular: MyViewHolder: el ViewHolder que s'ha d'actualitzar per representar el contingut de
    // l'element a la posició donada del conjunt de dades.
    //posició: Int: la posició de l'element dins del conjunt de dades de l'adaptador.
    //El mètode utilitza l'argument de posició per obtenir l'element actual de l'AssaigList en aquesta
    // posició. A continuació, utilitza l'argument titular per accedir als elements de disseny del
    // ViewHolder i estableix el text d'aquests elements de disseny amb les dades corresponents de
    // l'element actual. En aquest cas, estableix el text del titolAssaig, dataAssaig i llocAssaig
    // TextViews amb els valors de les propietats titolAssaig, dataAssaig i llocAssaig de l'element
    // actual, respectivament.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = AssaigList[position]

        holder.titolAssaig.text = currentitem.titolAssaig
        holder.dataAssaig.text = currentitem.dataAssaig
        holder.llocAssaig.text = currentitem.llocAssaig
    }

    //Aquesta és una implementació del mètode getItemCount de la classe RecyclerView.Adapter. Aquest
    // mètode retorna el nombre total d'elements del conjunt de dades que té l'adaptador.
    //
    //En aquest cas, retorna la mida del conjunt de dades AssaigList, que és una llista d'elements
    // que mostrarà el RecyclerView. La mida de la llista ve determinada per la propietat size de la
    // llista AssaigList, que retorna el nombre d'elements de la llista.
    //
    //Per tant, aquest mètode retornarà el nombre d'elements de la llista AssaigList, que serà el
    // nombre d'elements a RecyclerView.
    override fun getItemCount(): Int {
        return AssaigList.size
    }

    //Aquest és un mètode personalitzat que actualitza el conjunt de dades utilitzat per l'adaptador
    // RecyclerView.
    //
    //Es necessita un únic argument:
    //
    //AssaigList : List<AssaigModel>: el nou conjunt de dades que s'hauria d'utilitzar per actualitzar
    // el RecyclerView.
    //El mètode primer esborra el conjunt de dades actual (this.AssaigList) i hi afegeix tots els
    // elements del nou conjunt de dades (AssaigList). A continuació, crida al mètode
    // notifyDataSetChanged(), que és un mètode integrat de la classe RecyclerView.Adapter. Aquest
    // mètode notificarà a l'adaptador que el conjunt de dades ha canviat i hauria d'actualitzar
    // RecyclerView per reflectir les dades noves. Això farà que el RecyclerView torni a dibuixar
    // tots els seus elements, que mostraran les dades noves a l'usuari.
    //
    //És una funció que podeu utilitzar quan voleu actualitzar les dades del RecyclerView amb dades
    // noves.
    fun updateAssaigList(AssaigList : List<AssaigModel>){
        this.AssaigList.clear()
        this.AssaigList.addAll(AssaigList)
        notifyDataSetChanged()
    }

    //Aquesta és la implementació personalitzada de la classe RecyclerView.ViewHolder per mostrar
    // elements en un RecyclerView.
    //Es necessita un únic argument itemView : Vista que és la vista que utilitzarà el ViewHolder
    // per mostrar les seves dades.
    //
    //Té 3 propietats titolAssaig, dataAssaig, llocAssaig que són instàncies de TextView i
    // s'inicialitzen al constructor del ViewHolder mitjançant itemView.findViewById(R.id.titleAssaig).
    //
    //El mètode findViewById() s'utilitza per trobar els TextViews al disseny associat amb el
    // ViewHolder i el R.id.titleAssaig, R.id.dataAssaig, R.id.llocAssaig són els identificadors
    // únics de les TextViews al fitxer de disseny, que està vinculat a la classe.
    //
    //Així, aquesta classe s'utilitzarà per contenir referències a les TextViews en el disseny,
    // de manera que l'adaptador pugui accedir i actualitzar fàcilment els seus continguts quan
    // sigui necessari.
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titolAssaig : TextView = itemView.findViewById(R.id.titleAssaig)
        val dataAssaig : TextView = itemView.findViewById(R.id.dataAssaig)
        val llocAssaig : TextView = itemView.findViewById(R.id.llocAssaig)
    }
}