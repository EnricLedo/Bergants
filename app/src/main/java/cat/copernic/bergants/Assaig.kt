package cat.copernic.bergants

data class Assaig(val title: String, val data: String, val lloc: String, val assistencia: String){
    var titolAssaig: String? = null
    var dataAssaig: String? = null
    var llocAssaig: String? = null
    var assistenciaAssaig: String? = null


    init {
        this.titolAssaig = title
        this.dataAssaig = data
        this.llocAssaig = lloc
        this.assistenciaAssaig = assistencia
    }
}