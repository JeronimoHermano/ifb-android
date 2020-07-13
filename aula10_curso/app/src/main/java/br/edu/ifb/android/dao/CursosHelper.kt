package br.edu.ifb.android.dao

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class CursosHelper(context: Context) :
    SQLiteOpenHelper(context, CursosHelper.NOME_BANCO, null, CursosHelper.VERSAO) {

    companion object {
        const val NOME_BANCO = "academico.db"
        const val NOME_TABELA = "curso"
        const val ID = "_id"
        const val DESCRICAO = "descricao"
        const val CARGA_HORARIA = "carga_horaria"
        const val VERSAO = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // CREATE TABLE curso(id integer primary
        // key autoincrement, descricao text, carga_horaria integer)
        val sql = "CREATE TABLE ${CursosHelper.NOME_TABELA} (" +
                "${CursosHelper.ID} integer primary key autoincrement," +
                "${CursosHelper.DESCRICAO} text," +
                "${CursosHelper.CARGA_HORARIA} integer" +
                ")"
        try {
            db.execSQL(sql)
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("CRIAR_BANCO", e.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Verificando se a tabela existe
        db!!.execSQL("DROP TABLE IF EXISTS ${CursosHelper.NOME_TABELA}")
        onCreate(db)
    }

}