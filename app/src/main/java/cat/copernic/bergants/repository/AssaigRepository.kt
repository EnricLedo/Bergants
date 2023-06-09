package cat.copernic.bergants.repository

import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import cat.copernic.bergants.assajos
import cat.copernic.bergants.model.AssaigModel
import com.google.firebase.database.*

@Keep
class AssaigRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Assajos")

    @Volatile private var INSTANCE : AssaigRepository ?= null

    fun getInstance():AssaigRepository{
        return INSTANCE ?: synchronized(this){
            val instance = AssaigRepository()
            INSTANCE = instance
            instance
        }
    }

    fun carregarAssaig(assajos: MutableLiveData<List<AssaigModel>>){
        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _assajos : List<AssaigModel> = snapshot.children.map {dataSnapshot ->
                        dataSnapshot.getValue(AssaigModel::class.java)!!

                    }
                    assajos.postValue(_assajos)

                }catch (e : Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}