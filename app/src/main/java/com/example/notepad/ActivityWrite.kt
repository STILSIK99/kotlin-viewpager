package com.example.notepad

import android.content.Intent
import android.graphics.Color
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
        dbManager.open()
        getIntents()

        button_save.setOnClickListener {
            if (edTitle.text.toString() == ""){
                Toast.makeText(this, "Дополните сведения.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            when(id_write){
                -1 -> dbManager.insertData(
                    Cloud.TABLE_WRITES,
                    mapOf(
                        Cloud.COLUMN_WRITES_ID_CATEGORIES to "$id_cate",
                        Cloud.COLUMN_WRITES_TITLE to edTitle.text.toString(),
                        Cloud.COLUMN_WRITES_CONTENT to edContent.text.toString()
                    )
                )
                else ->{
                    dbManager.updateData(
                        Cloud.TABLE_WRITES,
                        id_write,
                        mapOf(
                            Cloud.COLUMN_WRITES_TITLE to edTitle.text.toString(),
                            Cloud.COLUMN_WRITES_CONTENT to edContent.text.toString()
                        )
                    )
                }

            }
            returnMainActivity()
        }

        button_delete.setOnClickListener{
            dbManager.deleteData(Cloud.TABLE_WRITES, id_write)
            returnMainActivity()
        }
    }

    fun getIntents(){
//        val intent = getIntent()
        if (intent != null) {
            if(intent.hasExtra(Intents.ID_CATE)){
                id_cate = intent.getIntExtra(Intents.ID_CATE, -1)
            }
            if(intent.hasExtra(Intents.ID_WRITE)){
                id_write = intent.getIntExtra(Intents.ID_WRITE, -1)
                loadWrite()
                textView.setText("Изменение записи")
                button_save.setBackgroundColor(Color.parseColor(Cloud.COLOR_UPDATE))
            }
            else{
                button_delete.visibility = View.GONE
            }
        }
    }

    fun loadWrite(){
        val write = dbManager.getWriteById(id_write)
        edTitle.setText(write.title)
        edContent.setText(write.content)
    }

    fun returnMainActivity(){
        val addIntent = Intent(this,  MainActivity::class.java).apply {
            putExtra(Intents.ID_CATE, id_cate)
        }
        startActivity(addIntent)
    }

    fun onClickAddWrite(view: View){


    }




    override fun onDestroy() {
        super.onDestroy()
        dbManager.close()
    }
}