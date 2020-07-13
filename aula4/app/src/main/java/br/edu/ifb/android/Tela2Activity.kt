package br.edu.ifb.android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Tela2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)

        val nome = intent?.extras?.get("NOME").toString()

        Toast.makeText(
            this, "Ai $nome!", Toast
                .LENGTH_LONG
        ).show()
    }
}
