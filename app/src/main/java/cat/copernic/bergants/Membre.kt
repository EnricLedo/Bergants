package cat.copernic.bergants

data class Membre (val nom:String, val foto:String){
    var nomMembre: String? = null
    var fotoMembre: String? = null

    init {
        this.nomMembre = nom
        this.fotoMembre = foto
    }
}