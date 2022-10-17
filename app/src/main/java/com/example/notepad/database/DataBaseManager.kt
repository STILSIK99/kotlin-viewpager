package com.example.notepad.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.notepad.database.Category.Category
import com.example.notepad.database.Write.Write

class DataBaseManager(val context: Context) {
    val dataBaseHelper = DataBaseHelper(context)
    var db: SQLiteDatabase? = null

    fun open(){
        db.let{
            db = dataBaseHelper.writableDatabase
        }
    }


    fun insertData(tableName: String, data: Map<String, String>){
        var values =
            when(tableName){
                Cloud.TABLE_CATEGORIES -> {
                    if (Cloud.COLUMN_CATEGORIES_NAME !in data) {
                        return
                    }
                    ContentValues().apply {
                        put(Cloud.COLUMN_CATEGORIES_NAME, data[Cloud.COLUMN_CATEGORIES_NAME])
                    }
                }
                Cloud.TABLE_WRITES ->{
                    if(Cloud.COLUMN_WRITES_ID_CATEGORIES !in data ||
                        Cloud.COLUMN_WRITES_TITLE !in data ||
                        Cloud.COLUMN_WRITES_CONTENT !in data){
                        return
                    }
                    ContentValues().apply {
                        put(Cloud.COLUMN_WRITES_ID_CATEGORIES, data[Cloud.COLUMN_WRITES_ID_CATEGORIES]?.toInt())
                        put(Cloud.COLUMN_WRITES_TITLE, data[Cloud.COLUMN_WRITES_TITLE])
                        put(Cloud.COLUMN_WRITES_CONTENT, data[Cloud.COLUMN_WRITES_CONTENT])
                    }
                }
                else -> return
            }
        db?.insert(tableName, null, values)
    }

    @SuppressLint("Range")
    fun readListCategories() : List<Category>{
        val dataList = mutableListOf<Category>()
        val cursor = db?.query(Cloud.TABLE_CATEGORIES, null, null,
            null, null, null, null)
        while(cursor?.moveToNext()!!){
            val name = cursor.getString(cursor.getColumnIndex(Cloud.COLUMN_CATEGORIES_NAME))
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            dataList.add(Category(name,  id))
        }
        cursor.close()
        return dataList.toList()
    }

    @SuppressLint("Range")
    fun readListWrites(id_cate: Int): List<Write>{
        val dataList = mutableListOf<Write>()
        val selection = "${Cloud.COLUMN_WRITES_ID_CATEGORIES} = ?"
        val selectionArgs = arrayOf("$id_cate")
        val cursor = db?.query(Cloud.TABLE_WRITES, null, selection,
            selectionArgs, null, null, null)
        while(cursor?.moveToNext()!!){
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val id_cate = cursor.getInt(cursor.getColumnIndex(Cloud.COLUMN_WRITES_ID_CATEGORIES))
            val title = cursor.getString(cursor.getColumnIndex(Cloud.COLUMN_WRITES_TITLE))
            val content = cursor.getString(cursor.getColumnIndex(Cloud.COLUMN_WRITES_CONTENT))
            dataList.add(Write(title, content, id_cate, id))
        }
        cursor.close()
        return dataList.toList()
    }

    fun close(){
        dataBaseHelper.close()
    }
}