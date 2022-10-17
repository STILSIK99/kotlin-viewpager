package com.example.notepad.database

import android.provider.BaseColumns

object Cloud{

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "database.db"

    const val TABLE_CATEGORIES = "categories"
    const val COLUMN_CATEGORIES_NAME = "name"

    const val TABLE_PICTURES = "pictures"
    const val COLUMN_PICTURES_NAME = "name"
    const val COLUMN_PICTURES_DATA = "data"
    const val COLUMN_PICTURES_ID_WRITES = "id_write"

    const val TABLE_WRITES = "writes"
    const val COLUMN_WRITES_TITLE = "title"
    const val COLUMN_WRITES_CONTENT = "content"
    const val COLUMN_WRITES_ID_CATEGORIES = "id_cat"



    const val TABLE_FILES = "files"
    const val COLUMN_FILES_NAME = "name"
    const val COLUMN_FILES_DATA = "data"
    const val COLUMN_FILES_ID_WRITES = "id_write"

    const val CREATE_TABLE_CATEGORIES =
        "CREATE TABLE IF NOT EXISTS ${TABLE_CATEGORIES} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${COLUMN_CATEGORIES_NAME} TEXT)"

    const val CREATE_TABLE_WRITES =
        "CREATE TABLE IF NOT EXISTS ${TABLE_WRITES} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${COLUMN_WRITES_TITLE} TEXT," +
        "${COLUMN_WRITES_CONTENT} TEXT," +
        "${COLUMN_WRITES_ID_CATEGORIES} INTEGER REFERENCES ${TABLE_CATEGORIES}(${BaseColumns._ID}) ON DELETE CASCADE )"

    const val CREATE_TABLE_PICTURES =
        "CREATE TABLE IF NOT EXISTS ${TABLE_PICTURES} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${COLUMN_PICTURES_NAME} TEXT," +
        "${COLUMN_PICTURES_DATA} TEXT," +
        "${COLUMN_PICTURES_ID_WRITES} INTEGER REFERENCES ${TABLE_WRITES}(${BaseColumns._ID}) ON DELETE CASCADE)"


    const val CREATE_TABLE_FILES =
        "CREATE TABLE IF NOT EXISTS ${TABLE_FILES} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${COLUMN_FILES_NAME} TEXT," +
        "${COLUMN_FILES_DATA} TEXT," +
        "${COLUMN_FILES_ID_WRITES} INTEGER REFERENCES ${TABLE_WRITES}(${BaseColumns._ID}) ON DELETE CASCADE)"

    const val DELETE_TABLE_CATEGORIES =
        "DROP TABLE IF EXISTS ${TABLE_CATEGORIES}"

    const val DELETE_TABLE_WRITES =
        "DROP TABLE IF EXISTS ${TABLE_WRITES}"

    const val DELETE_TABLE_PICTURES =
        "DROP TABLE IF EXISTS ${TABLE_PICTURES}"

    const val DELETE_TABLE_FILES =
        "DROP TABLE IF EXISTS ${TABLE_FILES}"



}
