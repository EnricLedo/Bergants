package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class noticia_canvi : Fragment() {

    data class Noticia(
        val titolNoticia: String,
        val contingutNoticia: String,
        val dataNoticia: String
    )

    private lateinit var titolNoticia: String
    private lateinit var contingutNoticia: String
    private lateinit var dataNoticia: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noticia_canvi, container, false)
    }
}