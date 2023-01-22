package cat.copernic.bergants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.ActuacioUsuariDirections
import cat.copernic.bergants.databinding.DisenyActuacioBinding
import cat.copernic.bergants.model.ActuacioModel

@Keep
class ActuacioRecyclerAdapterUser : RecyclerView.Adapter<ActuacioRecyclerAdapterUser.ViewHolder>(){
    var actuacionsUser: MutableList<ActuacioModel> = ArrayList()
    lateinit var context: Context
    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun ActuacioRecyclerAdapterUser(actuacionsUserList:MutableList<ActuacioModel>, contxt: Context){
        this.actuacionsUser = actuacionsUserList
        this.context = contxt
    }

    //Aquest és el codi del mètode onCreateViewHolder en una classe ActuacioRecyclerAdapter. RecyclerView
    // crida aquest mètode quan necessita un nou ViewHolder per mostrar un element.
    //Crea un nou objecte ViewHolder inflant un disseny anomenat "DissenyActuacio" mitjançant el mètode
    // LayoutInflater.from i passant al context del pare. A continuació, retorna l'objecte ViewHolder,
    // que es crea cridant a DisenyActuacioBinding.inflate(layoutInflater, parent, false).
    //S'utilitza per crear un nou objecte ViewHolder i inflar el disseny de cada element a la vista
    // del reciclador.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActuacioRecyclerAdapterUser.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActuacioRecyclerAdapterUser.ViewHolder(
            DisenyActuacioBinding.inflate(
                layoutInflater, parent, false
            )

        )
    }

    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: ActuacioRecyclerAdapterUser.ViewHolder, position: Int) {
        with(holder){
            with(actuacionsUser.get(position)){
                binding.titleActuacio.text = this.titolActuacio
                binding.dataActuacio.text = this.dataActuacio
                binding.llocActuacio.text = this.llocActuacio
            }
        }
        val item = actuacionsUser.get(position)
        holder.bind(item)
        //establim un listener
        holder.itemView.setOnClickListener{
            val action = ActuacioUsuariDirections.actionActuacioUsuariToInformacioActuacio(item)
            holder.itemView.findNavController().navigate(action)
        }
    }

    //Aquest és el codi del mètode getItemCount en una classe ActuacioRecyclerAdapter. S'utilitza
    // per determinar el nombre d'elements a la vista del reciclador retornant la mida de la llista
    // d'actuacions. El RecyclerView crida a aquest mètode per obtenir el nombre d'elements a mostrar.
    //Retorna la mida de la llista d'actuacions, de manera que la vista del reciclador sàpiga quants
    // elements mostrar.
    //S'utilitza per indicar al RecyclerView quants elements ha de mostrar retornant la mida del conjunt
    // de dades que representa l'adaptador.
    override fun getItemCount(): Int {
        return actuacionsUser.size
    }

    //Aquesta és una classe ViewHolder per a un ActuacioRecyclerAdapter. Crea un objecte ViewHolder
    // que s'utilitza per mostrar les dades de cada element a la vista del reciclador.
    //Té un únic constructor que pren un objecte DisenyActuacioBinding com a paràmetre i s'estén des
    // de la classe RecyclerView.ViewHolder.
    //Té un mètode anomenat bind, que pren un objecte ActuacioModel com a paràmetre, aquest mètode
    // s'utilitza per vincular les dades de l'objecte actuacio a les vistes corresponents del fitxer
    // de disseny que està associat a l'objecte DisenyActuacioBinding passat al constructor.
    //S'utilitza per vincular les dades d'un objecte ActuacioModel a les vistes corresponents del
    // fitxer de disseny.
    class ViewHolder(val binding: DisenyActuacioBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ActuacioUsuari: ActuacioModel) {
        }
    }
}