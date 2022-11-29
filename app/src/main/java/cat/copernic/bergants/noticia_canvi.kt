package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import cat.copernic.bergants.databinding.FragmentNoticiaCanviBinding
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.bergants.adapter.NoticiaRecyclerAdapter
import cat.copernic.bergants.model.NoticiaModel


class noticia_canvi : Fragment() {

    private lateinit var binding: FragmentNoticiaCanviBinding

    private val myAdapter: NoticiaRecyclerAdapter = NoticiaRecyclerAdapter()

    private fun setupRecyclerView() {
        binding.recyclerNoticies.setHasFixedSize(true)


        //indiquem que el RV es mostrarà en format llista
        binding.recyclerNoticies.layoutManager = LinearLayoutManager(context)

        //generem el adapter
        myAdapter.NoticiesRecyclerAdapter(getNoticies(),requireActivity())
        //assignem el adapter al RV
        binding.recyclerNoticies.adapter = myAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddNot = requireView().findViewById<Button>(R.id.botoAfegirNoticia)

        btnAddNot.setOnClickListener{
            findNavController().navigate(R.id.action_noticia_fragment_to_afegirNoticia)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNoticiaCanviBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun getNoticies():MutableList<NoticiaModel>{
        val noticies: MutableList<NoticiaModel> = arrayListOf()
        noticies.add(NoticiaModel("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(NoticiaModel("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(NoticiaModel("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(NoticiaModel("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(NoticiaModel("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(NoticiaModel("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))
        noticies.add(NoticiaModel("Anem d'excursió", "Recordeu que avui anirem d'excursió a Montserrat. Porteu-vos: motxilla, cantimplora, entrepà, botes de muntanya i moltes ganes de passar-ho bé.", "27-10-22 9:27h"))

        return noticies

    }

}