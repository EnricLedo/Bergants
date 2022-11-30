package cat.copernic.bergants.model

data class ActuacioModel(val title:String, val data:String, val lloc:String):java.io.Serializable {
    var titolActuacio: String? = null
    var dataActuacio: String? = null
    var llocActuacio: String? = null

    init {
        this.titolActuacio = title
        this.dataActuacio = data
        this.llocActuacio = lloc
    }
}