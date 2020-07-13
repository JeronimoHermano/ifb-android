package br.edu.ifb.android

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.net.Uri
import android.util.Log
import br.edu.ifb.android.dao.CursosHelper


class CursosProvider : ContentProvider() {

    var cursosHelper: CursosHelper? = null
    var context = null

    companion object {
        /*Definição da Authority, o pacote onde está o provider*/
//        const val AUTHORITY = "br.com.topicos1.cursos"
        const val AUTHORITY = "br.edu.ifb.android"

        /*Definição do prefixo da authority*/
        val CONTENT_URI = Uri.parse("content://$AUTHORITY")

        /*Definição do do caminho para consulta pelo id*/
        const val ID_PATH = "id/*"

        /*Definição do tipo de consulta, neste caso é listar todas*/
        const val CURSOS = 1

        /*Definição do tipo de consulta, neste caso consultar pelo id*/
        const val BY_ID = 2

        /*Identificador do tipo de uri a ser utilizada*/
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            /*Uri para buscar todas*/
            matcher.addURI(AUTHORITY, null, CURSOS)
            /*Uri para consulta pelo id*/
            matcher.addURI(AUTHORITY, ID_PATH, BY_ID)
            /*Uri para argumento inválido, nesse caso, não executa ação alguma*/
            matcher.addURI(AUTHORITY, "#", CURSOS)
        }
    }

//    init {
//        onCreate()
//    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = cursosHelper!!.writableDatabase
        val match = matcher.match(uri)
        return if (match != CURSOS) {
            0
        } else {
            db.delete(CursosHelper.NOME_TABELA, selection, selectionArgs)
        }
    }

    override fun getType(uri: Uri): String? {
        val match = matcher.match(uri)
        return if (match == CURSOS) {
            "vnd.android.cursor.dir/vnd.topicos1.cursos"
        } else {
            "vnd.android.cursor.item/vnd.topicos1.curso"
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.i(">>cursosHelper", cursosHelper.toString())
        Log.i(">>writableDatabase", cursosHelper?.writableDatabase.toString())

        val db = cursosHelper?.writableDatabase
        val match = matcher.match(uri)
        var newId: Long = 0

        if (match != CURSOS) {
            throw java.lang.UnsupportedOperationException("Not yet implemented")
        }
        if (values != null) {
            try {
                newId = db!!.insert(CursosHelper.NOME_TABELA, null, values)
                Log.i(">>> newId", newId.toString())
            } catch (e: SQLException) {
                e.printStackTrace()
                Log.i(">> Erro ao inserir: ", e.stackTrace.toString())
            }
            return Uri.withAppendedPath(uri, newId.toString())
        } else {
            throw UnsupportedOperationException("Valores são obrigatórios!")
        }
    }

    override fun onCreate(): Boolean {
        cursosHelper = getContext()?.let { CursosHelper(it) }
        Log.i(
            ">> OnCreate",
            cursosHelper.toString() + " / " + cursosHelper?.writableDatabase.toString()
        )
        return cursosHelper != null
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = cursosHelper!!.writableDatabase
        var order: String? = null
        val result: Cursor? = null
        if (sortOrder != null) {
            order = sortOrder
        }
        val match = matcher.match(uri)
        try {
            return when (match) {
                CURSOS -> {
                    db!!.query(
                        CursosHelper.NOME_TABELA, projection, selection,
                        selectionArgs, null, null, order
                    )
                }
                BY_ID -> {
                    db!!.query(
                        CursosHelper.NOME_TABELA,
                        projection,
                        CursosHelper.ID + "=?",
                        arrayOf(uri.lastPathSegment),
                        null,
                        null,
                        order
                    )
                }
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        } catch (e: Exception) {
            Log.e("Error: ", e.message.toString())
        }
        return result
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val db = cursosHelper!!.writableDatabase
        val match = matcher.match(uri)
        var rows = 0
        if (match != CURSOS) {
            throw java.lang.UnsupportedOperationException("Not yet implemented")
        }
        if (values != null) {
            rows = db.update(
                CursosHelper.NOME_TABELA,
                values,
                selection,
                selectionArgs
            )
        }
        return rows
    }


}
