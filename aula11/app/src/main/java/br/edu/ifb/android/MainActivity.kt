package br.edu.ifb.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity(), View.OnClickListener,
    GoogleApiClient.OnConnectionFailedListener {

    //Declarando uma variável para recuperar uma instância do FireBaseAuth
    private var mFirebaseAuth: FirebaseAuth? = null
    var edtEmail: EditText? = null
    var edtSenha: EditText? = null

    //Variável para recuperar as configurações de sign do Google
    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Recuperando uma instância do FireBaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)

        val gso =
            GoogleSignInOptions.Builder(DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient =
            GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addOnConnectionFailedListener(this)
                .addScope(Scope(Scopes.PROFILE))
                .addScope(Scope(Scopes.EMAIL))
                .build()

        findViewById<Button>(R.id.btnLoginSenha).setOnClickListener(this)
        findViewById<Button>(R.id.btnLoginGoogle).setOnClickListener(this)
        findViewById<View>(R.id.btnLogoutGoogle).setOnClickListener { signOut() }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.btnLoginSenha) {
                val email = edtEmail!!.text.toString()
                val senha = edtSenha!!.text.toString()
                mFirebaseAuth!!.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    InicioActivity::class.java
                                )
                            )
                            finish()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                task.exception.toString(), Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else {
                signIn()
            }
        }
    }

    private fun signIn() {
        val intent: Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, 1)
    }

    private fun signOut() {
        mFirebaseAuth!!.signOut()
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
            Toast.makeText(
                this,
                "Conta desconectada", Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, "Falha na autenticação", Toast.LENGTH_LONG).show()
    }

    private fun loginFirebase(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mFirebaseAuth!!.signInWithCredential(credential).addOnCompleteListener {
            Log.i(">> ", "loginFirebase")
            if (it.isSuccessful) {
                startActivity(
                    Intent(
                        this@MainActivity,
                        InicioActivity::class.java
                    )
                )
                finish()
            } else {
                Toast.makeText(this, "Falha na autenticação", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if (!mGoogleApiClient!!.isConnected) {
                mGoogleApiClient!!.connect()
            }

            if (result.isSuccess) {
                val account: GoogleSignInAccount? = result.signInAccount
                if (account != null) {
                    loginFirebase(account)
                }
                Toast.makeText(this, "Logado!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login falho!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Login falho!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        mGoogleApiClient!!.connect()
    }

    override fun onStop() {
        super.onStop()
        mGoogleApiClient!!.disconnect()
    }

}
