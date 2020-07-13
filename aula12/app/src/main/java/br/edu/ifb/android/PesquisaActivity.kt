package br.edu.ifb.android

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*


class PesquisaActivity : AppCompatActivity() {

    var edtTextoDaPesquisa: EditText? = null
    var lstPesquisa: ListView? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    var listaDeCursos: ArrayList<Curso> = ArrayList()
    var arrayAdapterCurso: ArrayAdapter<Curso>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)

        edtTextoDaPesquisa = findViewById(R.id.edtTextoDaPesquisa)
        lstPesquisa = findViewById(R.id.lstPesquisa)

        inicializarBancoDeDados()
        onChangeEdtTextoDaPesquisa()
    }

    private fun inicializarBancoDeDados() {
        //Inicializando o app Firebase
        FirebaseApp.initializeApp(this)
        //Recuperando uma instância do FirebaseDatabase
        firebaseDatabase = FirebaseDatabase.getInstance()
        //Passando a referência da instância para o DatabaseReference
        databaseReference = firebaseDatabase!!.reference
        //Permite leitura e escrita no Firebase e no celular
//        firebaseDatabase.setPersistenceEnabled(true)
    }

    private fun onChangeEdtTextoDaPesquisa() {
        edtTextoDaPesquisa?.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    val texto = edtTextoDaPesquisa!!.text.toString().trim { it <= ' ' }
                    /*Método a ser criado*/
                    pesquisarTexto(texto)
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            }
        )
    }

    private fun pesquisarTexto(texto: String) {
        val query: Query = if (texto == "") {
            databaseReference!!.child("Curso").orderByChild("descricao")
        } else {
            databaseReference!!.child("Curso").orderByChild("descricao")
                .startAt(texto).endAt(texto + "\uf8ff")
        }
        listaDeCursos.clear()
        query.addValueEventListener(
            object : ValueEventListener {
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
                        this@PesquisaActivity,
                        android.R.layout.simple_list_item_1, listaDeCursos
                    )
                    lstPesquisa!!.adapter = arrayAdapterCurso
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        pesquisarTexto("")
    }
}
