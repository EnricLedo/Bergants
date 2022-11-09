package cat.copernic.bergants.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.Noticia
import cat.copernic.bergants.R
import cat.copernic.bergants.noticia_canvi

class NoticiaViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val titolNoticia = view.findViewById<TextView>(R.id.TitolNoticia)
    val contingutNoticia = view.findViewById<TextView>(R.id.ContingutNoticia)
    val dataNoticia = view.findViewById<TextView>(R.id.HoraNoticia)

    fun render(noticiaModel: Noticia){
        titolNoticia.text = noticiaModel.titolNoticia
        contingutNoticia.text = noticiaModel.contingutNoticia
        dataNoticia.text = noticiaModel.dataNoticia
    }
}