package br.edu.ifb.android

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifb.android.dao.CursosHelper


class InserirActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir)

        //Adiciona a seta voltar e permite voltar para a tela principal
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_inserir_curso, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                inserir()
                true
            }
            R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun inserir() {
        val edtDescricao = findViewById<EditText>(R.id.edtDescricao)
        val edtCargaHoraria = findViewById<EditText>(R.id.edtCargaHoraria)
        val descricao = edtDescricao.text.toString()
        val cargaHoraria = Integer.parseInt(edtCargaHoraria.text.toString())
        val values = ContentValues().also {
            it.put(CursosHelper.DESCRICAO, descricao)
            it.put(CursosHelper.CARGA_HORARIA, cargaHoraria)
        }
        val provedor = contentResolver
        provedor.insert(CursosProvider.CONTENT_URI, values)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
