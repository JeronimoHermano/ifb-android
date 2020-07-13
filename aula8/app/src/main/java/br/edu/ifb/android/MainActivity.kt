package br.edu.ifb.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spnCampus = findViewById<Spinner>(R.id.spnCampus)
        // Opção A
//        val adapterCampus = ArrayAdapter.createFromResource(
//            this, R.array.array_campus,
//            android.R.layout.simple_list_item_1
//        )
//        adapterCampus.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        // Opção B
        val listaCampus = arrayOf("São Sebastião", "Santa Maria", "Planaltina")
        val adapterCampus = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaCampus
        )
        spnCampus.adapter = adapterCampus
        spnCampus.onItemSelectedListener = this

        val btnListView = findViewById<Button>(R.id.btnListView)
        btnListView.setOnClickListener {
            val i = Intent(this@MainActivity, ListViewActivity::class.java)
            startActivity(i)
        }

        val btnAdicionar = findViewById<Button>(R.id.btnAdicionar)
        btnAdicionar.setOnClickListener {
            val i = Intent(this@MainActivity, GridViewActivity::class.java)
            startActivity(i)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val txtCampusSelecionado = findViewById<TextView>(R.id.txtCampusSelecionado)
        var campus = "Campus selecionado: "
        campus += parent!!.selectedItem.toString()
        txtCampusSelecionado.text = campus
    }
}
