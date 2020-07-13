package br.edu.ifb.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btLinear = findViewById<Button>(R.id.btnLinear)
        val btConstraint = findViewById<Button>(R.id.btnConstraint)
        btLinear.setOnClickListener {
            chamarLinearLayout()
        }
        btConstraint.setOnClickListener {
            chamarConstraintLayout()
        }
    }

    private fun chamarLinearLayout() {
        val i = Intent(this, LinearActivity::class.java)
        startActivity(i)
    }

    private fun chamarConstraintLayout() {
        val i = Intent(this, ConstraintActivity::class.java)
        startActivity(i)
    }
}
