package cat.copernic.bergants.model

data class Noticia(val title:String, val content:String, val date: String) {
    var titolNoticia: String? = null
    var contingutNoticia: String?=null
    var dataNoticia: String? = null

    init {
        this.titolNoticia = title
        this.contingutNoticia = content
        this.dataNoticia = date
    }
}