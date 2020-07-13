package br.edu.ifb.android.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class GastoMensalDbHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "controle_de_gasto.db"
        val DB_VERSION = 1
        val TABLE_NAME = "gasto_mensal"
        val C_ID = "_id"
        val C_ANO = "ano"
        val C_MES = "mes"
        val C_FINALIDADE = "finalidade"
        val C_VALOR = "valor"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = StringBuilder()
        sql.append("create table $TABLE_NAME")
        sql.append(" ($C_ID integer primary key autoincrement, ")
        sql.append("$C_ANO text,")
        sql.append("$C_MES text,")
        sql.append("$C_FINALIDADE text,")
        sql.append("$C_VALOR decimal(8,2))")
        try {
            db?.execSQL(sql.toString())
        } catch (e: Exception) {
            Log.e("Error dbHelper", e.message.toString())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            db!!.execSQL("drop table if exists $TABLE_NAME")
            onCreate(db)
        } catch (e: java.lang.Exception) {
            Log.e("Error dbHelper", e.message.toString())
        }
    }

}