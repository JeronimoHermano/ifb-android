package br.edu.ifb.android

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MensagemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensagem)
        val t = TextView(this)
        t.text = "Dados iseridos com sucesso!"
        setContentView(t)
    }
}
