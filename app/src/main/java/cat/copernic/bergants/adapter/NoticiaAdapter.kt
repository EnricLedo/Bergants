package cat.copernic.bergants.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.Noticia
import cat.copernic.bergants.R

class NoticiaAdapter(private val noticiaList:List<Noticia>) : RecyclerView.Adapter<NoticiaViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NoticiaViewHolder(layoutInflater.inflate(R.layout.item_noticia, parent, false))
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val item = noticiaList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = noticiaList.size
}