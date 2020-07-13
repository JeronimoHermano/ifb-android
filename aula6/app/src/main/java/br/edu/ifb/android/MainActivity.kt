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

        val btnAbsolute = findViewById<Button>(R.id.btnAbsolute)
        btnAbsolute.setOnClickListener(this)

        val btnTable = findViewById<Button>(R.id.btnTable)
        btnTable.setOnClickListener(this)

        val btnMixed = findViewById<Button>(R.id.btnMixed)
        btnMixed.setOnClickListener(this)

        val btnComponenteVisual = findViewById<Button>(R.id.btnComponenteVisual)
        btnComponenteVisual.setOnClickListener(this)

        val btnCadastraImovel = findViewById<Button>(R.id.btnCadastraImovel)
        btnCadastraImovel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnAbsolute -> chamarAbsolute()
            R.id.btnTable -> chamarTable()
            R.id.btnMixed -> chamarMixed()
            R.id.btnComponenteVisual -> chamarComponenteVisual()
            R.id.btnCadastraImovel -> chamarCadastraImovel()
        }
    }

    private fun chamarCadastraImovel() {
        val i = Intent(this, CadastraImovelActivity::class.java)
        startActivity(i)
    }

    private fun chamarComponenteVisual() {
        val i = Intent(this, ComponenteVisualActivity::class.java)
        startActivity(i)
    }

    private fun chamarMixed() {
        val i = Intent(this, MixedActivity::class.java)
        startActivity(i)
    }

    private fun chamarAbsolute() {
        val i = Intent(this, AbsoluteActivity::class.java)
        startActivity(i)
    }

    private fun chamarTable() {
        val i = Intent(this, TableActivity::class.java)
        startActivity(i)
    }
}
