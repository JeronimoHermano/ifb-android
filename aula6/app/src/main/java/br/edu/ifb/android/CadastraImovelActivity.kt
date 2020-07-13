package br.edu.ifb.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class CadastraImovelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_imovel)

        val txt1 = findViewById<EditText>(R.id.edtNome)
        txt1.setOnClickListener { usarToast("Clck"); }
        txt1.setOnLongClickListener {
            usarToast("Clck longo")
            return@setOnLongClickListener true
        }

        val btn = findViewById<Button>(R.id.btnCadastraImovel)
        btn.setOnClickListener { mostrarMensagem() }

    }

    private fun mostrarMensagem() {
        val i = Intent(this, MensagemActivity::class.java)
        startActivity(i)
    }

    fun usarToast(texto: String?) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
    }
}
