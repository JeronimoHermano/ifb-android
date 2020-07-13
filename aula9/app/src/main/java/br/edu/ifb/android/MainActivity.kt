package br.edu.ifb.android

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifb.android.dao.GastoMensalDao
import br.edu.ifb.android.model.GastoMensal
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    val DATE_DIALOG_ID = 0
    var edtDataDeEfetivacao: EditText? = null
    private val cal = Calendar.getInstance()

    private val datEfetivacaoSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            atualizarData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdicionar = findViewById<Button>(R.id.btnInserir)
        btnAdicionar.setOnClickListener { inserir() }

        val btnListar = findViewById<Button>(R.id.btnListar)
        btnListar.setOnClickListener {
            val intent = Intent(this@MainActivity, ListaGastoMensalActivity::class.java)
            startActivity(intent)
        }

        edtDataDeEfetivacao = findViewById(R.id.edtDataDeEfetivacao)

        edtDataDeEfetivacao!!.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
                datEfetivacaoSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        edtDataDeEfetivacao!!.setOnFocusChangeListener { view: View, b: Boolean ->
//            showDialog(DATE_DIALOG_ID)
            DatePickerDialog(
                this@MainActivity,
                datEfetivacaoSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun inserir() {
        val edtAno = findViewById<EditText>(R.id.edtAno)
        val edtMes = findViewById<EditText>(R.id.edtMes)
        val edtFinalidade = findViewById<EditText>(R.id.edtFinalidade)
        val edtValor = findViewById<EditText>(R.id.edtValor)

        val gastoMensalDao = GastoMensalDao(baseContext)

        val gastoMensal = GastoMensal()
        gastoMensal.ano = edtAno.text.toString()
        gastoMensal.mes = edtMes.text.toString()
        gastoMensal.finalidade = edtFinalidade.text.toString()
        gastoMensal.valor = edtValor.text.toString().toDouble()

        val resultado = gastoMensalDao.inserir(gastoMensal)
        Toast.makeText(
            applicationContext,
            resultado, Toast.LENGTH_LONG
        ).show()
    }

    private fun atualizarData() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edtDataDeEfetivacao!!.setText(sdf.format(cal.time))
    }

//    override fun onCreateDialog(id: Int): Dialog? {
//        when (id) {
//            DATE_DIALOG_ID -> return DatePickerDialog(
//                this,
//                datEfetivacaoSetListener,
//
//                dataAno, dataMes, dataDia
//            )
//        }
//        return null
//    }

}
