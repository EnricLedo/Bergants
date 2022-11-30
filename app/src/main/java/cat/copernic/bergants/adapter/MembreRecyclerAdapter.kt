package cat.copernic.bergants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.Membre
import cat.copernic.bergants.databinding.DisenyMembreBinding
import coil.api.load


class MembreRecyclerAdapter: RecyclerView.Adapter<MembreRecyclerAdapter.ViewHolder>() {
    var membres: MutableList<Membre> = ArrayList()
    lateinit var context: Context

    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun MembreRecyclerAdapter(membreList:MutableList<Membre>, contxt: Context){
        this.membres = membreList
        this.context = contxt
    }

    //és l'encarregat de retornar el ViewHolder ja configurat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            DisenyMembreBinding.inflate(
                layoutInflater, parent, false
            )
        )
    }

    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){
            with(membres.get(position)){
                binding.nomMembre.text = this.nomMembre
                binding.fotoMembre.load(this.fotoMembre)
                /*
                 //Monstrar la imatge des de Storage de Firebase
                 val storageRef = FirebaseStorage.getInstance().reference
                 val imageRef = storageRef.child("rv/${this.nomMembre}")
                 imageRef.downloadUrl.addOnSuccessListener { url ->
                     binding.fotoMembre.load(url)
                 }.addOnFailureListener {
                     //mostrar error
                 } */
            }
        }
        val item = membres.get(position)
        holder.bind(item)

        //estamblim un listener
        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return membres.size
    }


    class ViewHolder(val binding: DisenyMembreBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(membre: Membre) {

        }

    }

}
