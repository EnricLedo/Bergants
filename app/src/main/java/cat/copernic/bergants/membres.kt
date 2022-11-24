package cat.copernic.bergants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.bergants.adapter.MembreRecyclerAdapter
import cat.copernic.bergants.databinding.FragmentMembresBinding
import cat.copernic.bergants.databinding.FragmentNoticiaCanviBinding

class membres : Fragment() {
    private lateinit var binding: FragmentMembresBinding

    private val myAdapter: MembreRecyclerAdapter = MembreRecyclerAdapter()

    private fun setupRecyclerView() {
        binding.recyclerMembres.setHasFixedSize(true)


        //indiquem que el RV es mostrarà en format llista
        binding.recyclerMembres.layoutManager = LinearLayoutManager(context)

        //generem el adapter
        myAdapter.MembreRecyclerAdapter(getMembres(),requireActivity())
        //assignem el adapter al RV
        binding.recyclerMembres.adapter = myAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddMem = requireView().findViewById<Button>(R.id.botoAfegirMembre)

        btnAddMem.setOnClickListener{
            findNavController().navigate(R.id.action_membres_fragment_to_afegirMembre)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMembresBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun getMembres():MutableList<Membre>{
        val membres: MutableList<Membre> = arrayListOf()
        membres.add(Membre("Enric Ledo Muntal", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Marc Fernández González", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Joan Galindo Palacio", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Belen Esteban", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Mila Ximenez", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Pablo Motos", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Alfons Lopez Navarro", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))
        membres.add(Membre("Adri Navarro González", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6jimbnhKGIjk--l0ovAaI4qVdLXFo3CJDhA&usqp=CAU"))

        return membres

    }

}