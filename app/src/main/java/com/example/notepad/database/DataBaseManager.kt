package com.example.notepad.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.widget.Toast
import com.example.notepad.database.Category.Category
import com.example.notepad.database.Write.Write

class DataBaseManager(val context: Context) {
    val dataBaseHelper = DataBaseHelper(context)
    var db: SQLiteDatabase? = null

//    init{
//        db.let{
//            db = dataBaseHelper.writableDatabase
//        }
//    }


    fun open(){
        db.let{
            db = dataBaseHelper.writableDatabase
        }
    }

    fun deleteData(tableName: String, id: Int){
        val request = "delete from $tableName where ${BaseColumns._ID} = $id"
        db?.execSQL(request)
    }


    fun updateData(tableName: String, id: Int, data: Map<String, String>){
        val values =
            when(tableName){
                Cloud.TABLE_WRITES -> {
                    if(Cloud.COLUMN_WRITES_TITLE !in data ||
                        Cloud.COLUMN_WRITES_CONTENT !in data){
                        return
                    }
                    ContentValues().apply {
                        put(Cloud.COLUMN_WRITES_TITLE, data[Cloud.COLUMN_WRITES_TITLE])
                        put(Cloud.COLUMN_WRITES_CONTENT, data[Cloud.COLUMN_WRITES_CONTENT])
                    }
                }

                else -> return
            }

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id")
        val count = db?.update(
            tableName,
            values,
            selection,
            selectionArgs)
        if (count != 0){
            Toast.makeText(context, "Изменения внесены.", Toast.LENGTH_LONG).show()
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
    fun getWriteById(id_write: Int): Write{
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id_write")
        val cursor = db?.query(Cloud.TABLE_WRITES, null, selection,
            selectionArgs, null, null, null)
        val result: Write
        if(cursor?.moveToNext()!!) {
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val id_cate = cursor.getInt(cursor.getColumnIndex(Cloud.COLUMN_WRITES_ID_CATEGORIES))
            val title = cursor.getString(cursor.getColumnIndex(Cloud.COLUMN_WRITES_TITLE))
            val content = cursor.getString(cursor.getColumnIndex(Cloud.COLUMN_WRITES_CONTENT))
            result = Write(title, content, id_cate, id_write)
        }
        else{
            result = Write("", "", -1)
        }
        cursor.close()

        return result
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