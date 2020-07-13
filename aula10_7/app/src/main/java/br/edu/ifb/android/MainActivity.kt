package br.edu.ifb.android

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val uriCursos: Uri = Uri.parse("content://br.com.topicos1.cursos")
            val cursor: Cursor? = contentResolver.query(
                uriCursos, null, null, null,
                null
            )
            val from =
                arrayOf("descricao", "carga_horaria")
            val to = intArrayOf(R.id.txtViewDescricao, R.id.txtViewCargaHoraria)
            val simpleCursorAdapter = SimpleCursorAdapter(
                this,
                R.layout.activity_main_itens_1, cursor, from, to
            )
            val listView = findViewById<ListView>(R.id.lstCursosProvider)
            listView.adapter = simpleCursorAdapter
        } catch (e: Exception) {
            Log.e("Erro: ", e.message)
        }
    }
}
