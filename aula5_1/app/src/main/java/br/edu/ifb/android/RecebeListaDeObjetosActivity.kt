package br.edu.ifb.android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class RecebeListaDeObjetosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recebe_lista_de_objetos)
        val listaCursos = intent.getParcelableArrayListExtra<Curso>("listaCursos")
        if (listaCursos != null) {
            Toast.makeText(this, listaCursos[0].descricao, Toast.LENGTH_LONG).show()
        }
    }
}
