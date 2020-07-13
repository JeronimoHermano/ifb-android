package br.edu.ifb.android

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var edtDescricao: EditText
    private var edtCargaHoraria: EditText? = null
    private var lstCursos: ListView? = null

    private var listaDeCursos: ArrayList<Curso> = ArrayList()
    private var arrayAdapterCurso: ArrayAdapter<Curso>? = null

    private lateinit var cursoSelecionado: Curso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtDescricao = findViewById(R.id.edtDescricao)
        edtCargaHoraria = findViewById(R.id.edtCargaHoraria)
        lstCursos = findViewById(R.id.lstCursos)

        inicializarBancoDeDados()
        listar()

        lstCursos!!.setOnItemClickListener { parent, view, position, id ->
            cursoSelecionado = parent.getItemAtPosition(position) as Curso
            edtDescricao.setText(cursoSelecionado.descricao)
            edtCargaHoraria?.setText(cursoSelecionado.cargaHoraria.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuNovo) {
            inserir()
        } else if (item.itemId == R.id.mnuAlterar) {
            if (cursoSelecionado != null) {
                alterar()
            } else {
                Toast.makeText(this, "Favor selecionar curso.", Toast.LENGTH_SHORT).show()
            }
        } else if (item.itemId == R.id.mnuExcluir) {
            if (cursoSelecionado != null) {
                excluir()
            } else {
                Toast.makeText(this, "Favor selecionar o curso.", Toast.LENGTH_SHORT).show()
            }
        } else if (item.itemId == R.id.mnuPesquisar) {
            val intent = Intent(this, PesquisaActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun inicializarBancoDeDados() {
        //Inicializando o app Firebase
        FirebaseApp.initializeApp(this@MainActivity)
        //Recuperando uma instância do FirebaseDatabase
        firebaseDatabase = FirebaseDatabase.getInstance()
        //Passando a referência da instância para o DatabaseReference
        databaseReference = firebaseDatabase.reference
        //Permite leitura e escrita no Firebase e no celular
//        firebaseDatabase.setPersistenceEnabled(true)
    }

    private fun inserir() {
        val curso = Curso()
        curso.uid = UUID.randomUUID().toString()
        curso.descricao = edtDescricao.text.toString()
        curso.cargaHoraria = edtCargaHoraria!!.text.toString().toInt()
        //Comando para persistir o objeto no Firebase
        databaseReference.child("Curso").child(curso.uid).setValue(curso)
    }

    private fun listar() {
        val eventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listaDeCursos.clear()
                snapshot.children.forEach {
                    val curso = it.getValue(Curso::class.java)
                    if (curso != null) {
                        listaDeCursos.add(curso)
                    }
                }
                arrayAdapterCurso = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    listaDeCursos
                )
                lstCursos!!.adapter = arrayAdapterCurso
            }

        }
        databaseReference.child("Curso").addValueEventListener(eventListener)
    }

    private fun alterar() {
        val curso = Curso()
        curso.uid = cursoSelecionado.uid
        curso.descricao = edtDescricao.text.toString()
        curso.cargaHoraria = edtCargaHoraria!!.text.toString().toInt()
        databaseReference.child("Curso").child(curso.uid).setValue(curso)
    }

    private fun excluir() {
        val builder = AlertDialog.Builder(this)

        builder.setIcon(android.R.drawable.ic_menu_delete)
            .setTitle("Confirmação!")
            .setMessage("Tem certeza que deseja excluir este registro?")
            .setPositiveButton(
                "Sim",
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    val curso = Curso()
                    curso.uid = cursoSelecionado.uid
                    databaseReference.child("Curso").child(curso.uid).removeValue()
                    Toast.makeText(applicationContext, "Registro excluido!", Toast.LENGTH_SHORT)
                        .show()
                }).setNegativeButton("Não", null)
        builder.show()
    }
}
