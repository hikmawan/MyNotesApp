package dev.ghani.mynotes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DbManager {
    val dbName="MyNotes"
    val dbTable="Notes"
    val colID="ID"
    val colTitle="TITLE"
    val colContent="CONTENT"
    val dbVersion=1
    val sqlCreateTable="CREATE TABLE IF NOT EXISTS "+ dbTable +" (" + colID +" INTEGER PRIMARY KEY, "+
            colTitle + " TEXT, "+ colContent +" TEXT);"

    var sqlDB:SQLiteDatabase?=null

    constructor(context: Context){
        val db=DatabaseHelperNotes(context)

        sqlDB=db.writableDatabase
    }


    inner class DatabaseHelperNotes:SQLiteOpenHelper{
        var context:Context?=null
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context=context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context," database is created", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS" + dbTable)
        }

    }

    fun Insert(values:ContentValues):Long{
        val ID = sqlDB!!.insert(dbTable,"",values)
        return ID
    }
}