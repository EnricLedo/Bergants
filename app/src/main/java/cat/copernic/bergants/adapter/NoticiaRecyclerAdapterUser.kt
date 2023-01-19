package cat.copernic.bergants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.databinding.DisenyNoticiaBinding
import cat.copernic.bergants.model.NoticiaModel
import cat.copernic.bergants.noticia_canviDirections

@Keep
class NoticiaRecyclerAdapterUser : RecyclerView.Adapter<NoticiaRecyclerAdapterUser.ViewHolder>(){
    var noticiesUser: MutableList<NoticiaModel> = ArrayList()
    lateinit var context: Context

    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun NoticiesRecyclerAdapterUser(noticiesUserList:MutableList<NoticiaModel>, contxt: Context){
        this.noticiesUser = noticiesUserList
        this.context = contxt
    }

    //és l'encarregat de retornar el ViewHolder ja configurat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaRecyclerAdapterUser.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NoticiaRecyclerAdapterUser.ViewHolder(
            DisenyNoticiaBinding.inflate(
                layoutInflater, parent, false
            )

        )
    }

    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: NoticiaRecyclerAdapterUser.ViewHolder, position: Int) {
        with(holder){
            with(noticiesUser.get(position)){
                binding.titlenotice.text = this.titolNoticia
                binding.contingut.text = this.contingutNoticia
                binding.data.text = this.dataNoticia
            }
        }
        val item = noticiesUser.get(position)
        holder.bind(item)
    }

    //Aquesta és una implementació del mètode getItemCount de la classe RecyclerView.Adapter. Aquest
    // mètode retorna el nombre total d'elements del conjunt de dades que té l'adaptador.
    //
    //En aquest cas, retorna la mida del conjunt de dades de notificacions, que és una llista d'elements
    // que mostrarà el RecyclerView. La mida de la llista ve determinada per la propietat size de la
    // llista d'avisos, que retorna el nombre d'elements de la llista.
    //
    //Per tant, aquest mètode retornarà el nombre d'elements de la llista d'avisos, que serà el nombre
    // d'elements a RecyclerView.
    override fun getItemCount(): Int {
        return noticiesUser.size
    }

    //Aquesta és una implementació personalitzada de la classe RecyclerView.ViewHolder per mostrar
    // elements en un RecyclerView. Es necessita un sol argument, "val binding: DisenyNoticiaBinding",
    // que és una instància de la classe DisenyNoticiaBinding, que s'utilitza per enllaçar les dades
    // al RecyclerView. El mètode bind() s'utilitza per vincular les dades d'un objecte NoticiaModel
    // al disseny del ViewHolder. El constructor RecyclerView.ViewHolder es crida amb l'argument
    // binding.root, aquesta és la vista arrel del disseny que representa el ViewHolder.
    //Aquesta classe s'ha d'utilitzar juntament amb l'adaptador personalitzat que l'utilitza per
    // mostrar les dades al RecyclerView.
    //
    //S'utilitza per vincular les dades de l'objecte NoticiaModel al disseny, i aquest disseny
    // s'utilitza per inflar la vista dels elements al RecyclerView.
    class ViewHolder(val binding: DisenyNoticiaBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(noticiesUsuari: NoticiaModel) {

        }

    }
}