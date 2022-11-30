package cat.copernic.bergants

class Utilities {
    fun campEsBuit(correu:String,contrasenya:String):Boolean{
        return correu.isNotEmpty()&&contrasenya.isNotEmpty()
    }
}