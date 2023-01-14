package cat.copernic.bergants.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Keep
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.R
import cat.copernic.bergants.model.AssaigModel

@Keep
class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val AssaigList = ArrayList<AssaigModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.diseny_assaig,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = AssaigList[position]

        holder.titolAssaig.text = currentitem.titolAssaig
        holder.dataAssaig.text = currentitem.dataAssaig
        holder.llocAssaig.text = currentitem.llocAssaig
    }

    override fun getItemCount(): Int {
        return AssaigList.size
    }

    fun updateAssaigList(AssaigList : List<AssaigModel>){
        this.AssaigList.clear()
        this.AssaigList.addAll(AssaigList)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titolAssaig : TextView = itemView.findViewById(R.id.titleAssaig)
        val dataAssaig : TextView = itemView.findViewById(R.id.dataAssaig)
        val llocAssaig : TextView = itemView.findViewById(R.id.llocAssaig)
    }
}