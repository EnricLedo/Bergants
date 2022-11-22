package cat.copernic.bergants.model

data class NoticiaModel(val title:String, val content:String, val date: String) {
    var titolNoticia: String? = null
    var contingutNoticia: String?=null
    var dataNoticia: String? = null
}