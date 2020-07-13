package br.edu.ifb.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEnviaDado = findViewById<Button>(R.id.btnEnviarDado)
        btnEnviaDado.setOnClickListener(this)

        val btnEnviarObjeto = findViewById<Button>(R.id.btnEnviarObjeto)
        btnEnviarObjeto.setOnClickListener(this)

        val btnEnviarListaDeObjetos = findViewById<Button>(R.id.btnEnviarListaDeObjetos)
        btnEnviarListaDeObjetos.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnEnviarObjeto -> enviarObjeto()
            R.id.btnEnviarDado -> enviarDados()
            R.id.btnEnviarListaDeObjetos -> enviarListaDeObjetos()
        }
    }

    private fun enviarListaDeObjetos() {
        val listaCursos = ArrayList<Curso>()
        val c1 = Curso()
        val c2 = Curso()
        val c3 = Curso()
        val c4 = Curso()
        c1.codigo = 1
        c1.descricao = "ABI em Ciência da Computação"
        c1.cargaHoraria = 3000
        c2.codigo = 2
        c2.descricao = "Tecnologia em Automação Industrial"
        c2.cargaHoraria = 2400
        c3.codigo = 3
        c3.descricao = "Licenciatura em Física"
        c3.cargaHoraria = 2800
        c4.codigo = 4
        c4.descricao = "Moda"
        c4.cargaHoraria = 2100
        listaCursos.addAll(arrayOf(c1, c2, c3, c4))
        val i = Intent(this, RecebeListaDeObjetosActivity::class.java)
        i.putParcelableArrayListExtra("listaCursos", listaCursos)
        startActivity(i)
    }

    private fun enviarObjeto() {
        val curso = Curso()
        curso.codigo = 900
        curso.descricao = "ABI em Ciência da Computação"
        curso.cargaHoraria = 3000
        val i = Intent(this, RecebeObjetoActivity::class.java)
        i.putExtra("CURSO", curso)
        startActivity(i)
    }

    private fun enviarDados() {
        val s1 = "Este é valor de uma String"
        val d1 = 90.8
        val i1 = 78
        val i = Intent(this, RecebeDadoActivity::class.java)
        val b = Bundle()
        b.putString("S1", s1)
        b.putDouble("D1", d1)
        b.putInt("I1", i1)
        i.putExtras(b)
        startActivity(i)
    }


}
