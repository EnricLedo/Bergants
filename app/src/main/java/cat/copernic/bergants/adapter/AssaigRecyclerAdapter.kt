package cat.copernic.bergants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.databinding.DisenyAssaigBinding
import cat.copernic.bergants.Assaig
import cat.copernic.bergants.R

class AssaigRecyclerAdapter : RecyclerView.Adapter<AssaigRecyclerAdapter.ViewHolder>() {
    var assajos: MutableList<Assaig> = ArrayList()
    lateinit var context: Context

    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun AssaigRecyclerAdapter(assajosList: MutableList<Assaig>, contxt: Context) {
        this.assajos = assajosList
        this.context = contxt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssaigRecyclerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AssaigRecyclerAdapter.ViewHolder(
            DisenyAssaigBinding.inflate(
                layoutInflater, parent, false
            )

        )
    }

    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        with(holder){
            with(assajos.get(position)){
                binding.titleAssaig.text = this.titolAssaig
                binding.dataAssaig.text = this.titolAssaig
                binding.llocAssaig.text = this.llocAssaig
                binding.assistenciaAssaig.text = this.assistenciaAssaig
            }
        }
        val item = assajos.get(position)
        holder.bind(item)

        //establim un listener
        holder.itemView.setOnClickListener{
        }
    }

    override fun getItemCount(): Int {
        return assajos.size
    }

    class ViewHolder(val binding: DisenyAssaigBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(assaig: Assaig){

        }
    }
}