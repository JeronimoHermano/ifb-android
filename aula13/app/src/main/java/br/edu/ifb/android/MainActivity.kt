package br.edu.ifb.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btAula).setOnClickListener(this)
        findViewById<Button>(R.id.btBusca).setOnClickListener(this)
        findViewById<Button>(R.id.btMapa).setOnClickListener(this)
        findViewById<Button>(R.id.btPin).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        var i : Intent? = when(p0!!.id){
            R.id.btAula -> Intent(this, AulaMapsActivity::class.java)
            R.id.btBusca -> Intent(this, SearchMapsActivity::class.java)
            R.id.btMapa -> Intent(this, MapsActivity::class.java)
            R.id.btPin -> Intent(this, MarkerMapsActivity::class.java)
            else -> null
        }
        if(i != null){
            startActivity(i)
//            finish()
        }
    }
}
