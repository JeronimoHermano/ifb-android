package br.edu.ifb.android

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnLongClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnClickLongo = findViewById<Button>(R.id.btnClickLongo)
        btnClickLongo.setOnLongClickListener(this)

        val btnMenuDeContexto = findViewById<Button>(R.id.btnMenuDeContexto)
        registerForContextMenu(btnMenuDeContexto)
    }

    override fun onLongClick(v: View?): Boolean {
        when (v!!.id) {
            R.id.btnClickLongo -> {
                makeText(this, "Clique longo", LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_contexto, menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar_curso -> {
                editarCurso()
                true
            }
            R.id.excluir_curso -> {
                excluirCurso()
                true
            }
            R.id.ajuda -> {
                ajuda()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun ajuda() {
        makeText(this, "Ajuda", LENGTH_LONG).show()
    }

    private fun excluirCurso() {
        makeText(this, "Excluir", LENGTH_LONG).show()
    }

    private fun editarCurso() {
        makeText(this, "Editar", LENGTH_LONG).show()
    }

    fun chamarMetodo(view: View) {
        makeText(this, "By XML", LENGTH_LONG).show()
    }
}
