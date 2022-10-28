package cat.copernic.bergants

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.bergants.adapter.NoticiaAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerNoticies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NoticiaAdapter(NoticiaProvider.noticiaList)
    }
} 