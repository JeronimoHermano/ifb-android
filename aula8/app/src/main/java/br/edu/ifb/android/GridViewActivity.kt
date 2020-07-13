package br.edu.ifb.android

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.appcompat.app.AppCompatActivity


class GridViewActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemClickListener {

    private var listaCursos: ArrayList<String>? = null
    private var adapterCursos: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view)
        //Criando a lista para armazenar os cursos
        listaCursos = ArrayList()
        //Criando um adapter para a lista
        adapterCursos = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, listaCursos as ArrayList<String>
        )

        val gridViewCursos = findViewById<GridView>(R.id.gridCursos)
        //Setando o adapter à listview
        gridViewCursos.adapter = adapterCursos
        registerForContextMenu(gridViewCursos)

        val btnAdicionar: Button = findViewById<Button>(R.id.btnAdicionar)
        btnAdicionar.setOnClickListener(this)
        gridViewCursos.onItemClickListener = this
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.excluir_contexto -> {
                val menuInfo = item.menuInfo as AdapterContextMenuInfo
                excluir(menuInfo.position)
                true
            }
            R.id.ajuda_contexto -> {
                ajuda()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun ajuda() {
        Toast.makeText(
            this,
            "Adicionar: Digite e clique no +.\nRemover: Clique no item.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun excluir(position: Int) {
        listaCursos?.removeAt(position)
        adapterCursos?.notifyDataSetChanged()
        Toast.makeText(this, "Item excluído", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        val edtCurso = findViewById<EditText>(R.id.edtCurso)
        val curso = edtCurso.text.toString()
        if (listaCursos != null) {
            if (curso.isNotEmpty()) {
                listaCursos!!.add(curso)
                adapterCursos!!.notifyDataSetChanged()
                edtCurso.setText("")
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        listaCursos?.removeAt(position)
        adapterCursos?.notifyDataSetChanged()
    }
}
