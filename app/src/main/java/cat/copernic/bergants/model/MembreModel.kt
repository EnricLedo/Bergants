package cat.copernic.bergants.model

import androidx.annotation.Keep

@Keep
data class MembreModel(val name:String, val malname:String, val espatlles:String, val mans:String,
                        val email:String, val adress:String, val telefon:String, val rol:String,
                        val date:String, val admin:Boolean):java.io.Serializable{
    var nomMembre: String? = null
    var malnom: String? = null
    var alcadaEspatlles: String? = null
    var alcadaMans: String? = null
    var correuMembre: String? = null
    var adrecaMembre: String? = null
    var telefonMembre: String? = null
    var rolMembre: String? = null
    var altaMembre: String? = null
    var adminMembre: Boolean = false

    init {
        this.nomMembre = name
        this.malnom = malname
        this.alcadaEspatlles = espatlles
        this.alcadaMans = mans
        this.correuMembre = email
        this.adrecaMembre = adress
        this.telefonMembre = telefon
        this.rolMembre = rol
        this.altaMembre = date
        this.adminMembre = admin
    }
}
