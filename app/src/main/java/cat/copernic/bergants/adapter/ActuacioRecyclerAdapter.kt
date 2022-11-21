package cat.copernic.bergants.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.databinding.DisenyActuacioBinding
import cat.copernic.bergants.model.Actuacio

class ActuacioRecyclerAdapter : RecyclerView.Adapter<ActuacioRecyclerAdapter.ViewHolder>() {
    var actuacions: MutableList<Actuacio> = ArrayList()
    lateinit var context: Context

    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun ActuacioRecyclerAdapter(actuacionsList:MutableList<Actuacio>,contxt: Context){
        this.actuacions = actuacionsList
        this.context = contxt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActuacioRecyclerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            DisenyActuacioBinding.inflate(
                layoutInflater, parent, false
            )

        )
    }

    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(actuacions.get(position)){
                binding.titleActuacio.text = this.titolActuacio
                binding.dataActuacio.text = this.dataActuacio
                binding.busActuacio.text = this.busActuacio
                binding.llocActuacio.text = this.llocActuacio
                binding.assistenciaActuacio.text = this.assistencia
            }
        }
        val item = actuacions.get(position)
        holder.bind(item)

        //establim un listener
        holder.itemView.setOnClickListener{
        }
    }

    override fun getItemCount(): Int {
        return actuacions.size
    }

    class ViewHolder(val binding: DisenyActuacioBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(actuacio: Actuacio) {

        }

    }
}