package cat.copernic.bergants

import androidx.annotation.Keep

@Keep
data class Membre (val nom:String, val foto:String){
    var nomMembre: String? = null
    var fotoMembre: String? = null

    init {
        this.nomMembre = nom
        this.fotoMembre = foto
    }
}