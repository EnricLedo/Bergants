package cat.copernic.bergants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.DataLists.NoticiaModelList.Companion.NoticiaRV
import cat.copernic.bergants.databinding.DisenyNoticiaBinding
import cat.copernic.bergants.model.NoticiaModel
import cat.copernic.bergants.noticia_canvi
import cat.copernic.bergants.noticia_canviDirections

class NoticiaRecyclerAdapter : RecyclerView.Adapter<NoticiaRecyclerAdapter.NoticiaRecyclerHolder>(){
    var noticies: MutableList<NoticiaModel> = ArrayList()
    lateinit var context: Context

    inner class NoticiaRecyclerHolder(val binding: DisenyNoticiaBinding) : RecyclerView.ViewHolder(binding.root)
    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun NoticiesRecyclerAdapter(noticiesList:MutableList<NoticiaModel>, contxt: Context){
        this.noticies = noticiesList
        this.context = contxt
    }

    //és l'encarregat de retornar el ViewHolder ja configurat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaRecyclerAdapter.NoticiaRecyclerHolder {
        val binding = DisenyNoticiaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticiaRecyclerHolder(binding)
    }
    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: NoticiaRecyclerAdapter.NoticiaRecyclerHolder, position: Int) {
        with(holder){
            with(NoticiaRV[position]){
                binding.titlenotice.text = this.titolNoticia
                binding.contingut.text = this.contingutNoticia
                binding.data.text = this.dataNoticia
            }
        }
        /*//establim un listener
        holder.itemView.setOnClickListener{
            val action = noticia_canviDirections.actionNoticiaFragmentToEditarNoticia(item)
            holder.itemView.findNavController().navigate(action)
        }
         */
    }

    override fun getItemCount(): Int {
        return noticies.size
    }

    class ViewHolder(val binding: DisenyNoticiaBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(noticia: NoticiaModel) {

        }

    }

}