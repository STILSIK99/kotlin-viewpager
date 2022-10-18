package com.example.notepad.database.Category

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notepad.Intents
import com.example.notepad.MainActivity
import com.example.notepad.R
import com.example.notepad.database.Cloud
import com.example.notepad.database.DataBaseManager
import kotlinx.android.synthetic.main.activity_category_add.*

class Category_add : AppCompatActivity() {
    val dbManager = DataBaseManager(this)
    var id_category  = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_add)

        button_add.setBackgroundColor(Color.parseColor(Cloud.COLOR_CREATE))
        button_add.setOnClickListener{
            if (edName.text.toString() == ""){
                Toast.makeText(this,
                    "Не задано название категории.",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            dbManager.open()
            when(id_category){
                -1 -> dbManager.insertData(
                    Cloud.TABLE_CATEGORIES,
                    mapOf(Cloud.COLUMN_CATEGORIES_NAME to edName.text.toString())
                )
                else -> return@setOnClickListener
//                    dbManager.updateSubject(
//                        id_subject,
//                        edTitle.text.toString(),
//                        edContent.text.toString()
//                    )
            }
            val mainIntent = Intent(this,  MainActivity::class.java).apply {
                putExtra(Intents.ACTION, Intents.ACTION )
            }
            startActivity(mainIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.close()
    }
}