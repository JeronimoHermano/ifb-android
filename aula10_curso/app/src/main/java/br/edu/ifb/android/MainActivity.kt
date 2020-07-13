package br.edu.ifb.android

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import br.edu.ifb.android.dao.CursosHelper


class MainActivity : AppCompatActivity() {

    /*Define o valor do loader, neste caso, é para listar todos,
    * conforme definido no Content Provider*/
    private val LOADER_ID = 1

    /*Adaptador para colocar os dados do banco na ListView*/
    private var simpleCursorAdapter: SimpleCursorAdapter? = null

    /*Quando usuário clicar em algum item da lista, a activity para edição será
    * e receberá o id da disciplina selecionada*/
    private val itemListener =
        OnItemClickListener { parent, view, position, id ->
            val intent = Intent(baseContext, EditarActivity::class.java)
            intent.putExtra("id", id.toString())
            startActivity(intent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this, InserirActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        val from = arrayOf(CursosHelper.ID, CursosHelper.DESCRICAO, CursosHelper.CARGA_HORARIA)
        val to = intArrayOf(R.id.txtViewId, R.id.txtViewDescricao, R.id.txtViewCargaHoraria)
        simpleCursorAdapter = SimpleCursorAdapter(
            this,
            R.layout.activity_main_itens,
            null,
            from,
            to,
            0
        )
        val list = findViewById<ListView>(R.id.lstCursos)
        list.adapter = simpleCursorAdapter
        list.onItemClickListener = itemListener
        val loaderManager = loaderManager
//        loaderManager.initLoader(LOADER_ID, null, loaderCallbacks)
        supportLoaderManager.initLoader(LOADER_ID, null, loaderCallbacks)
    }

    val loaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {

        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            return CursorLoader(
                applicationContext,
                CursosProvider.CONTENT_URI,
                null,
                null,
                null,
                null
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            if (loader.id == LOADER_ID) {
                simpleCursorAdapter!!.swapCursor(data)
            }
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            simpleCursorAdapter!!.swapCursor(null)
        }
    }
}