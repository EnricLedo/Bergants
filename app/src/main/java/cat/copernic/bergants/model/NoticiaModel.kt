package cat.copernic.bergants.model

data class NoticiaModel (var title: String, var content: String, var date: String):java.io.Serializable {
    var titolNoticia: String? = null
    var contingutNoticia: String?=null
    var dataNoticia: String? = null

    init {
        this.titolNoticia = title
        this.contingutNoticia = content
        this.dataNoticia = date
    }
}

