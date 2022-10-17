package com.example.notepad.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper (context: Context) : SQLiteOpenHelper(context, Cloud.DATABASE_NAME,
    null, Cloud.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Cloud.CREATE_TABLE_CATEGORIES)
        db?.execSQL(Cloud.CREATE_TABLE_WRITES)
        db?.execSQL(Cloud.CREATE_TABLE_FILES)
        db?.execSQL(Cloud.CREATE_TABLE_PICTURES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(Cloud.DELETE_TABLE_FILES)
        db?.execSQL(Cloud.DELETE_TABLE_PICTURES)
        db?.execSQL(Cloud.DELETE_TABLE_WRITES)
        db?.execSQL(Cloud.DELETE_TABLE_CATEGORIES)
        onCreate(db)
    }


}