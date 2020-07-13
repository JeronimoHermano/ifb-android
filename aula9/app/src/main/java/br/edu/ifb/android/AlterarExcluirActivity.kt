package br.edu.ifb.android

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifb.android.dao.GastoMensalDao
import br.edu.ifb.android.dao.GastoMensalDbHelper
import br.edu.ifb.android.model.GastoMensal


class AlterarExcluirActivity : AppCompatActivity() {

    private var dao: GastoMensalDao? = null
    private var cursor: Cursor? = null
    private var edtId: EditText? = null
    private var edtAnoAlterar: EditText? = null
    private var edtMesAlterar: EditText? = null
    private var edtFinalidadeAlterar: EditText? = null
    private var edtValorAlterar: EditText? = null
    private var btnAlterar: Button? = null
    private var btnExcluir: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_excluir)

        edtId = findViewById(R.id.edtId)
        edtAnoAlterar = findViewById(R.id.edtAnoAlterar)
        edtMesAlterar = findViewById(R.id.edtMesAlterar)
        edtFinalidadeAlterar = findViewById(R.id.edtFinalidadeAlterar)
        edtValorAlterar = findViewById(R.id.edtValorAlterar)
        btnAlterar = findViewById(R.id.btnAlterar)
        btnExcluir = findViewById(R.id.btnExcluir)

        val id = intent.extras!!.getLong("ID")
        //Buscar o gasto pelo id
        dao = GastoMensalDao(baseContext)
        cursor = dao!!.consultarPeloId(id)
        cursor?.moveToFirst()

        edtId!!.setText(id.toString())

        val ano = cursor?.getString(
            cursor!!.getColumnIndexOrThrow(
                GastoMensalDbHelper.C_ANO
            )
        )
        edtAnoAlterar!!.setText(ano)

        val mes = cursor?.getString(
            cursor!!.getColumnIndexOrThrow(
                GastoMensalDbHelper.C_MES
            )
        )
        edtMesAlterar!!.setText(mes)

        val finalidade = cursor?.getString(
            cursor!!.getColumnIndexOrThrow(
                GastoMensalDbHelper.C_FINALIDADE
            )
        )
        edtFinalidadeAlterar!!.setText(finalidade)

        val valor = cursor!!.getDouble(
            cursor!!.getColumnIndexOrThrow(
                GastoMensalDbHelper.C_VALOR
            )
        )
        edtValorAlterar!!.setText(valor.toString())

        //Listener para o botão alterar
        btnAlterar!!.setOnClickListener {
            val gastoMensal = GastoMensal()
//            val gastoMensalDao = GastoMensalDao(baseContext)
            gastoMensal.id = edtId!!.text.toString().toInt()
            gastoMensal.ano = edtAnoAlterar!!.text.toString()
            gastoMensal.mes = edtMesAlterar!!.text.toString()
            gastoMensal.finalidade = edtFinalidadeAlterar!!.text.toString()
            gastoMensal.valor = edtValorAlterar!!.text.toString().toDouble()
            //Chamando o método alterar da classe dao
            dao!!.alterar(gastoMensal)
            val intent = Intent(this@AlterarExcluirActivity, ListaGastoMensalActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Listener para o botão excluir
        btnExcluir!!.setOnClickListener {
            dao!!.excluir(edtId!!.text.toString().toInt())
            intent = Intent(this@AlterarExcluirActivity, ListaGastoMensalActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}


