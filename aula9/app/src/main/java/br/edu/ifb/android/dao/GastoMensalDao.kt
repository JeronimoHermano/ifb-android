package br.edu.ifb.android.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import br.edu.ifb.android.model.GastoMensal

class GastoMensalDao(context: Context) {

    private var banco: GastoMensalDbHelper? = null
    private var db: SQLiteDatabase? = null

    init {
        banco = GastoMensalDbHelper(context)
    }

    fun inserir(gastoMensal: GastoMensal): String {
        //Criar um map para receber os valores dos atributos
        val valores = ContentValues()
        //Passar os alores para o map
        valores.put(GastoMensalDbHelper.C_ANO, gastoMensal.ano)
        valores.put(GastoMensalDbHelper.C_MES, gastoMensal.mes)
        valores.put(GastoMensalDbHelper.C_FINALIDADE, gastoMensal.finalidade)
        valores.put(GastoMensalDbHelper.C_VALOR, gastoMensal.valor)
        //Permitir escrita no banco
        db = banco?.writableDatabase
        //Inserir o objeto no banco
        val resultado = db!!.insert(GastoMensalDbHelper.TABLE_NAME, null, valores)
        //Fechando a conexão com o banco
        db?.close()
        //Verificando o resultado
        return if (resultado > 0) {
            "Inserido com sucesso!"
        } else {
            "Erro ao inserir"
        }
    }

    fun listarTodos(): Cursor {
        //Preparando o banco para leitura
        db = banco!!.readableDatabase
        //Definindo os campos a serem buscados no banco
        val campos = arrayOf<String>(
            GastoMensalDbHelper.C_ID,
            GastoMensalDbHelper.C_ANO, GastoMensalDbHelper.C_MES,
            GastoMensalDbHelper.C_FINALIDADE, GastoMensalDbHelper.C_VALOR
        )
        //Fazendo a consulta
        val cursor = db!!.query(
            GastoMensalDbHelper.TABLE_NAME,
            campos, null, null, null, null, null
        )
        cursor?.moveToFirst()
        db!!.close()
        return cursor
    }

    fun consultarPeloId(id: Long): Cursor? {
        //Definir os campos a serem buscados
        val campos = arrayOf(
            GastoMensalDbHelper.C_ID,
            GastoMensalDbHelper.C_ANO, GastoMensalDbHelper.C_MES,
            GastoMensalDbHelper.C_FINALIDADE, GastoMensalDbHelper.C_VALOR
        )
        //Tornar o readable
        db = banco!!.readableDatabase
        //Definir a cláusula where
        val selection = GastoMensalDbHelper.C_ID + "=" + id
        val cursor = db!!.query(
            GastoMensalDbHelper.TABLE_NAME,
            campos, selection, null, null, null, null
        )
        cursor?.moveToFirst()
        db!!.close()
        return cursor
    }

    fun alterar(gastoMensal: GastoMensal) {
        //Definir os campos
        val valores = ContentValues()
        valores.put(GastoMensalDbHelper.C_ANO, gastoMensal.ano)
        valores.put(GastoMensalDbHelper.C_MES, gastoMensal.mes)
        valores.put(GastoMensalDbHelper.C_FINALIDADE, gastoMensal.finalidade)
        valores.put(GastoMensalDbHelper.C_VALOR, gastoMensal.valor)
        db = banco!!.writableDatabase
        val whereClause =
            GastoMensalDbHelper.C_ID + "=" + gastoMensal.id
        db!!.update(GastoMensalDbHelper.TABLE_NAME, valores, whereClause, null)
        db!!.close()
    }

    fun excluir(id: Int) {
        db = banco!!.writableDatabase
        val whereClause = GastoMensalDbHelper.C_ID + "=" + id
        db!!.delete(GastoMensalDbHelper.TABLE_NAME, whereClause, null)
        db!!.close()
    }

}