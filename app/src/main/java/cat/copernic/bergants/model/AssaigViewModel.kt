package cat.copernic.bergants.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.copernic.bergants.repository.AssaigRepository

class AssaigViewModel : ViewModel() {

    private val repository : AssaigRepository
    private val _allAssaig = MutableLiveData<List<AssaigModel>>()
    val allAssaig : LiveData<List<AssaigModel>> = _allAssaig


    init{
        repository = AssaigRepository().getInstance()
        repository.carregarAssaig(_allAssaig)
    }
}