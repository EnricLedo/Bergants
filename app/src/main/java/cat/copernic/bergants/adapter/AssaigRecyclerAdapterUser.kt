package cat.copernic.bergants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.assaigUsuariDirections
import cat.copernic.bergants.assajos
import cat.copernic.bergants.databinding.DisenyAssaigBinding
import cat.copernic.bergants.model.AssaigModel


@Keep
class AssaigRecyclerAdapterUser : RecyclerView.Adapter<AssaigRecyclerAdapterUser.ViewHolder>() {
    var assajosUser: MutableList<AssaigModel> = ArrayList()
    lateinit var context: Context

    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun AssaigRecyclerAdapterUser(assajosUserList: MutableList<AssaigModel>, contxt: Context) {
        this.assajosUser = assajosUserList
        this.context = contxt
    }

    //Aquest és el codi del mètode onCreateViewHolder en una classe AssaigRecyclerAdapter. RecyclerView
    // crida aquest mètode quan necessita un nou ViewHolder per mostrar un element.
    //Crea un nou objecte ViewHolder inflant un disseny anomenat "DissenyAssaig" mitjançant el mètode
    // LayoutInflater.from i passant al context del pare. A continuació, retorna l'objecte ViewHolder,
    // que es crea cridant a DisenyAssaigBinding.inflate(layoutInflater, parent, false).
    //S'utilitza per crear un nou objecte ViewHolder i inflar el disseny de cada element a la vista del
    // recycler.
    //S'encarrega de crear nous objectes ViewHolder i configurar la disposició de cada element a la vista
    // del recycler.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssaigRecyclerAdapterUser.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AssaigRecyclerAdapterUser.ViewHolder(
            DisenyAssaigBinding.inflate(
                layoutInflater, parent, false
            )

        )
    }

    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: AssaigRecyclerAdapterUser.ViewHolder, position: Int){
        with(holder){
            with(assajosUser.get(position)){
                binding.titleAssaig.text = this.titolAssaig
                binding.dataAssaig.text = this.titolAssaig
                binding.llocAssaig.text = this.llocAssaig
            }
        }
        val item = assajosUser.get(position)
        holder.bind(item)

        //establim un listener
        holder.itemView.setOnClickListener{
            val action = assaigUsuariDirections.actionAssaigUsuariToFragmentInformacioAssaigUser(item)
            holder.itemView.findNavController().navigate(action)
        }
    }

    //getItemCount(): retorna la mida de la llista d'assajos, que s'utilitza per determinar el nombre
    // d'elements a la vista del reciclador.
    //ViewHolder: és una classe interna de l'AssaigRecyclerAdapter. Crea un objecte ViewHolder que
    // s'utilitza per mostrar les dades de cada element a la vista del reciclador.
    //Té un únic constructor que pren un objecte DisenyAssaigBinding com a paràmetre i s'estén des de
    // la classe RecyclerView.ViewHolder.
    //Té un mètode anomenat bind, que pren un objecte AssaigModel com a paràmetre, aquest mètode
    // s'utilitza per vincular les dades de l'objecte assaig a les vistes corresponents del fitxer de
    // disseny que està associat amb l'objecte DisenyAssaigBinding passat al constructor.
    //S'utilitza per vincular les dades d'un objecte AssaigModel a les vistes corresponents del fitxer
    // de disseny.
    //S'utilitza per mostrar les dades de cada element a la vista del recycler.
    override fun getItemCount(): Int {
        return assajosUser.size
    }

    class ViewHolder(val binding: DisenyAssaigBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(assaigUsuari: AssaigModel){

        }
    }
}