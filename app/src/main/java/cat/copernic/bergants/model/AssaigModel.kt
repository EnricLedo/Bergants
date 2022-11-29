package cat.copernic.bergants.model

data class AssaigModel(val title: String, val data: String, val lloc: String): java.io.Serializable {
    var titolAssaig: String? = null
    var dataAssaig: String? = null
    var llocAssaig: String? = null


    init {
        this.titolAssaig = title
        this.dataAssaig = data
        this.llocAssaig = lloc
    }
}