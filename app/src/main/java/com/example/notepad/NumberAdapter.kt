package com.example.notepad

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notepad.database.Category.Category

class NumberAdapter(val contextFA: Context, fragment: FragmentActivity) : FragmentStateAdapter(fragment){
    var listCate: List<Category> = listOf()


    override fun getItemCount() = listCate.size

    override fun createFragment(position: Int): Fragment {
        val fragment = NumberFragment(contextFA)
        fragment.arguments = Bundle().apply {
            putInt(Intents.ID_CATE, listCate[position].id)
        }
        return fragment
    }

}