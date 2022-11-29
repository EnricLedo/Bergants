package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import cat.copernic.bergants.databinding.FragmentEditarNoticiaBinding
import cat.copernic.bergants.databinding.FragmentNoticiaCanviBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.firestore.FirebaseFirestore

class editar_noticia : Fragment() {
    private lateinit var binding: FragmentEditarNoticiaBinding

    private val args by navArgs<editar_noticiaArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentEditarNoticiaBinding.inflate(inflater, container, false)

        binding.editartitol.setText(args.currentNoticia.titolNoticia)
        binding.editarnoticia.setText(args.currentNoticia.contingutNoticia)
        binding.editardata.setText(args.currentNoticia.dataNoticia)

        return binding.root
    }
}