package com.example.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.notepad.database.Cloud
import com.example.notepad.database.DataBaseManager
import kotlinx.android.synthetic.main.activity_write.*

class ActivityWrite : AppCompatActivity() {
    val dbManager = DataBaseManager(this)
    var id_cate: Int = -1
    var id_write = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        getIntents()
        dbManager.open()
    }

    fun getIntents(){
//        val intent = getIntent()
        if (intent != null) {
            if(intent.hasExtra(Intents.ID_CATE)){
                id_cate = intent.getIntExtra(Intents.ID_CATE, -1)
            }
        }
    }


    fun onClickAddWrite(view: View){
        if (edTitle.text.toString() == ""){
            Toast.makeText(this, "Дополните сведения.", Toast.LENGTH_SHORT).show()
            return
        }
        when(id_write){
            -1 -> dbManager.insertData(
                Cloud.TABLE_WRITES,
                mapOf(Cloud.COLUMN_WRITES_ID_CATEGORIES to "$id_cate",
                    Cloud.COLUMN_WRITES_TITLE to edTitle.text.toString(),
                    Cloud.COLUMN_WRITES_CONTENT to edContent.text.toString())
            )
            else ->{
                //updateData
                return
            }

        }
        val addIntent = Intent(this,  MainActivity::class.java).apply {
            putExtra(Intents.ID_CATE, id_cate)
        }
        startActivity(addIntent)
    }
}