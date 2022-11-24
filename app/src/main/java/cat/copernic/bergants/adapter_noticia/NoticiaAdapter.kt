package cat.copernic.bergants.adapter_noticia

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.Noticia
import cat.copernic.bergants.databinding.DisenyNoticiaBinding

class NoticiaRecyclerAdapter : RecyclerView.Adapter<NoticiaRecyclerAdapter.ViewHolder>(){
    var noticies: MutableList<Noticia> = ArrayList()
    lateinit var context: Context

    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun NoticiesRecyclerAdapter(noticiesList:MutableList<Noticia>, contxt: Context){
        this.noticies = noticiesList
        this.context = contxt
    }

    //és l'encarregat de retornar el ViewHolder ja configurat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            DisenyNoticiaBinding.inflate(
                layoutInflater, parent, false
            )

        )
    }
    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(noticies.get(position)){
                binding.titlenotice.text = this.titolNoticia
                binding.contingut.text = this.contingutNoticia
                binding.data.text = this.dataNoticia
            }
        }
        val item = noticies.get(position)
        holder.bind(item)

        //establim un listener
        holder.itemView.setOnClickListener{
        }
    }

    override fun getItemCount(): Int {
        return noticies.size
    }

    class ViewHolder(val binding: DisenyNoticiaBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(noticia: Noticia) {

        }

    }

}