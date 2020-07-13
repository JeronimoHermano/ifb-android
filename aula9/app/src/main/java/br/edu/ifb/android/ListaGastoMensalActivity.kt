package br.edu.ifb.android

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.cursoradapter.widget.SimpleCursorAdapter
import br.edu.ifb.android.dao.GastoMensalDao
import br.edu.ifb.android.dao.GastoMensalDbHelper


class ListaGastoMensalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_gasto_mensal)

        //Definindo os campos da tabela
        val campos = arrayOf(
            GastoMensalDbHelper.C_ID,
            GastoMensalDbHelper.C_ANO,
            GastoMensalDbHelper.C_MES,
            GastoMensalDbHelper.C_FINALIDADE,
            GastoMensalDbHelper.C_VALOR
        )
        //Capturando os ids das widgets do grid
        val idViews = intArrayOf(
            R.id.edtId, R.id.edtAno, R.id.edtMes,
            R.id.edtFinalidade, R.id.edtValor
        )
        val dao = GastoMensalDao(baseContext)
        val cursor = dao.listarTodos()
        val adapter = SimpleCursorAdapter(
            applicationContext,
            R.layout.grid_lista_gasto_mensal, cursor, campos, idViews, 0
        )
        val lstGastoMensal = findViewById<ListView>(R.id.lstGastoMensal)
        lstGastoMensal.adapter = adapter

        lstGastoMensal.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                val intent = Intent(
                    this@ListaGastoMensalActivity,
                    AlterarExcluirActivity::class.java
                )
                intent.putExtra("ID", id)
                startActivity(intent)
                finish()
            }
    }

}
