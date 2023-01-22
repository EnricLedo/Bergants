package cat.copernic.bergants

import androidx.annotation.Keep

@Keep
//Aquest codi defineix una classe de dades anomenada "Membre" a Kotlin que pren dos paràmetres,
// nom i foto, que són cadenes, que representen el nom i la foto d'un membre respectivament.
//
//Declara dues variables nomMembre i fotoMembre que són cadenes anul·lables.
//
//També té un bloc d'inici que assigna els valors passats en el constructor a les variables de classe.
//
//Aquesta classe probablement s'utilitza per emmagatzemar dades d'un membre, com ara el seu nom i foto.
// La classe es defineix com una classe de dades de manera que obté automàticament funcionalitats com
// els mètodes equals() i hashCode() i declaracions de desestructuració.

/**

La classe Membre és una classe de dades que conté informació sobre el nom i la foto d'un membre.
@property nomMembre El nom del membre.
@property fotoMembre La foto del membre.
 */
data class Membre (val nom:String, val foto:String){
    var nomMembre: String? = null
    var fotoMembre: String? = null

    init {
        this.nomMembre = nom
        this.fotoMembre = foto
    }
}