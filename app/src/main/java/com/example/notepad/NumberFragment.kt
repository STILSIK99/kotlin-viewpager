package com.example.notepad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepad.database.DataBaseManager
import com.example.notepad.database.Write.WriteAdapter
import com.example.notepad.database.Write.WriterViewModel
import kotlinx.android.synthetic.main.fragment_number.*
import androidx.lifecycle.Observer

class NumberFragment(val contextF: Context) : Fragment() {
    var id_cate: Int = 0
    val dbManager = DataBaseManager(contextF)
    private val writerViewModel by lazy { ViewModelProvider(this).get(WriterViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dbManager.open()
        return inflater.inflate(R.layout.fragment_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(Intents.ID_CATE) }?.apply {
            id_cate = getInt(Intents.ID_CATE)
            updateView()
            addWrite.setOnClickListener{
                val addWriteIntent = Intent(contextF,  ActivityWrite::class.java).apply {
                    putExtra(Intents.ID_CATE, id_cate)
                }
                startActivity(addWriteIntent)
            }
        }
    }

    fun updateView(){
        writerViewModel.listWrites.value = dbManager.readListWrites(id_cate)
        val adapter = WriteAdapter(contextF)
        recycleView.layoutManager = LinearLayoutManager(contextF)
        recycleView.adapter = adapter
        writerViewModel.listWrites.observe(this, Observer {
            it?.let {
                adapter.refreshSubjects(it)
            }
        })
    }

}