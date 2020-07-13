package br.edu.ifb.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("ON_CREATE", "Método onCreate sendo executado")

        val edtNome = findViewById<EditText>(R.id.edtNome)
        edtNome.hint = "Digite um nome"

        btnStartAnotherActivity.setOnClickListener {
            val intent = Intent(this, Tela2Activity::class.java)
            val bundle = bundleOf("NOME" to edtNome.text)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("ON_START", "Método onStart sendo executado")
    }

}
