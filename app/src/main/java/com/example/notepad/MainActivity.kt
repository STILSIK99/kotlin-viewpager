package com.example.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.notepad.database.Cloud
import com.example.notepad.database.DataBaseManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_write.*

class MainActivity : AppCompatActivity() {
    val dbManager = DataBaseManager(this)
    private var adapter = NumberAdapter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbManager.open()

    }

    override fun onResume() {
        super.onResume()
        loadResourses()
        getIntents()
    }

    fun newCategory(view: View){
        val addCateIntent = Intent(this,  Category_add::class.java)
        startActivity(addCateIntent)
    }

    fun loadResourses(){
        dbManager?.let{
            adapter.listCate = dbManager.readListCategories()
            pager.adapter = adapter
            TabLayoutMediator(tabLayout, pager){tab, position ->
                tab.text = adapter.listCate[position].name
            }.attach()
        }
    }

    fun getIntents(){
        if (intent != null) {
            if(intent.hasExtra(Intents.ID_CATE)){
                val id_cate = intent.getIntExtra(Intents.ID_CATE, -1)
                for (i in 0 until adapter.itemCount){
                    if (id_cate != adapter.listCate[i].id) continue
                    tabLayout.selectTab(tabLayout.getTabAt(i))
                    break
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.close()
    }

}