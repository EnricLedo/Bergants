package cat.copernic.bergants.model

data class Actuacio(val title:String, val data:String, val bus:String, val lloc:String, val assistencia:String){
    var titolActuacio: String? = null
    var dataActuacio: String? = null
    var busActuacio: String? = null
    var llocActuacio: String? = null
    var assistenciaActuacio: String? = null


    init {
        this.titolActuacio = title
        this.dataActuacio = data
        this.busActuacio = bus
        this.llocActuacio = lloc
        this.assistenciaActuacio = assistencia
    }
}

