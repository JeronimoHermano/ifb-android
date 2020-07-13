package br.edu.ifb.android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class RecebeDadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recebe_dado)

        val b = intent.extras
        val s1 = b!!.getString("S1")
        val d1 = b.getDouble("D1")
        val i1 = b.getInt("I1")
        val resultado = "$s1, de um double $d1 e de um inteiro $i1"
        Toast.makeText(this, resultado, Toast.LENGTH_LONG).show()
    }
}
