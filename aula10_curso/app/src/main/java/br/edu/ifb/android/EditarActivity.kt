package br.edu.ifb.android

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import br.edu.ifb.android.dao.CursosHelper

class EditarActivity : AppCompatActivity() {

    //Consulta pelo ID, conforme definido no provider, 1 = listar todos, 2 = buscar pelo ID
    private val LOADER_ID = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        //Adiciona a seta voltar e permite voltar para a tela principal
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        if (intent.hasExtra("id")) {
//            val loaderManager = loaderManager
            val initLoader =
                supportLoaderManager.initLoader(LOADER_ID, intent.extras, cursorLoaderCallbacks)
//                loaderManager!!.initLoader(LOADER_ID,intent.extras,cursorLoaderCallbacks)
        } else {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_editar_curso, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_alter -> {
                alterar()
                true
            }
            R.id.home -> {
                finish()
                true
            }
            R.id.action_delete -> {
                excluir()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun alterar() {
        val edtID = findViewById<EditText>(R.id.edtEditarId)
        val edtDescricao = findViewById<EditText>(R.id.edtEditarDescricao)
        val edtCargaHoraria = findViewById<EditText>(R.id.edtEditarCargaHoraria)
        val id = edtID.text.toString()
        val descricao = edtDescricao.text.toString()
        val cargaHoraria = edtCargaHoraria.text.toString()
        val values = ContentValues()
        values.put(CursosHelper.DESCRICAO, descricao)
        values.put(CursosHelper.CARGA_HORARIA, cargaHoraria)
        contentResolver.update(
            CursosProvider.CONTENT_URI,
            values,
            CursosHelper.ID + "=?", arrayOf(id.toString())
        )
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun excluir() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(android.R.drawable.ic_menu_delete)
            .setTitle("Confirmação!")
            .setMessage("Tem certeza que deseja excluir este registro?")
            .setPositiveButton("Sim") { dialogInterface: DialogInterface, i: Int ->
                Log.i(">> OK", "OK")
                val txtId = findViewById<EditText>(R.id.edtEditarId)
                val id = txtId.text.toString()
                contentResolver.delete(
                    CursosProvider.CONTENT_URI,
                    CursosHelper.ID + "=?",
                    arrayOf(id.toString())
                )
                Toast.makeText(applicationContext, "Registro excluido!", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()
            }
            .setNegativeButton("Não", null)
        builder.show()
    }


    val cursorLoaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {

        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            //if (id == LOADER_ID) {
            var idCurso: String? = ""
            idCurso = args!!.getString("id")
            val uri: Uri = Uri.withAppendedPath(
                CursosProvider.CONTENT_URI,
                "id/$idCurso"
            )
            return CursorLoader(
                applicationContext, uri, null, null,
                null, null
            )
            //} else {null}
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            if (loader.id == LOADER_ID) {
                var descricao = ""
                var id = ""
                var cargaHoraria = ""

                if (data!!.moveToNext()) {
                    id = data.getString(0)
                    descricao = data.getString(1)
                    cargaHoraria = data.getString(2)
                }

                val txtId = findViewById<EditText>(R.id.edtEditarId)
                val txtTitulo = findViewById<EditText>(R.id.edtEditarDescricao)
                val txtAutor = findViewById<EditText>(R.id.edtEditarCargaHoraria)

                txtId.setText(id)
                txtTitulo.setText(descricao)
                txtAutor.setText(cargaHoraria)
            }
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {

        }

    }

}
