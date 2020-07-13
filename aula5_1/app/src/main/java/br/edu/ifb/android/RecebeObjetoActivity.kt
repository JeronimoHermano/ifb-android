package br.edu.ifb.android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class RecebeObjetoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recebe_objeto)

        val b = intent.extras
        val curso = b!!["CURSO"] as Curso?
        if (curso != null) {
            Toast.makeText(this, curso.descricao, Toast.LENGTH_LONG).show()
        }
    }
}
