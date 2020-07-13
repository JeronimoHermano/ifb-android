package br.edu.ifb.android

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private var edtMatricula: EditText? = null
    private var edtNome: EditText? = null
    private var txtStatus: TextView? = null
    private var btnSalvar: Button? = null

    private val sharedPreferences = View.OnClickListener {
        //Recuperando os valores da tela
        val matricula = edtMatricula!!.text.toString()
        val nome = edtNome!!.text.toString()
        //Criando um SharedPreferences para um arquivo
        val filePreferences = getPreferences(Context.MODE_PRIVATE)
        //Criando um mapra para os valores da forma chave/valor
        val editor = filePreferences.edit()
        //Colocando os valores no mapa
        editor.putString("matricula", matricula)
        editor.putString("nome", nome)
        //Gravando os valores
        editor.commit()
        txtStatus!!.text = "Status: Preferências salvas com sucesso!!!"
    }

    private val arquivoExterno =
        View.OnClickListener {
            val matricula = edtMatricula!!.text.toString()
            val nome = edtNome!!.text.toString()
            salvarArquivoExterno(matricula, nome)
            txtStatus!!.text = "Status: Arquivo salvo com sucesso!!!"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtMatricula = findViewById(R.id.edtMatricula)
        edtNome = findViewById(R.id.edtNome)
        txtStatus = findViewById(R.id.txtStatus)
        btnSalvar = findViewById(R.id.btnSalvar)

//        btnSalvar!!.setOnClickListener(sharedPreferences)
//        recuperarPreferencias()

//        btnSalvar!!.setOnClickListener(arquivoInterno)
//        lerArquivoInterno()

        btnSalvar!!.setOnClickListener(arquivoExterno)
        lerArquivoExterno()
    }

    private fun recuperarPreferencias() {
        //Criando um SharedPreferences para um arquivo
        val filePreferences = getPreferences(Context.MODE_PRIVATE)
        /*Recuperando, pelas chaves os valores da preferência salva,
        caso nada tenha sido salvo, deixa o valor em branco*/
        val matricula = filePreferences.getString("matricula", "")
        val nome = filePreferences.getString("nome", "")
        //Colocando os valores nos campos da tela
        edtMatricula!!.setText(matricula)
        edtNome!!.setText(nome)
    }

    private val arquivoInterno = View.OnClickListener {
        val matricula = edtMatricula!!.text.toString()
        val nome = edtNome!!.text.toString()
        salvarArquivoInterno(matricula, nome)
        txtStatus!!.text = "Status: Arquivo salvo com sucesso!!!"
    }

    private fun salvarArquivoInterno(matricula: String, nome: String) {
        val dados = StringBuilder()
        dados.append("matricula=" + matricula)
        dados.append("\n")
        dados.append("nome=" + nome)
        dados.append("\n")
        try {
            //Abrindo um arquivo para gravação de dados
            val fos = openFileOutput("dados.txt", Context.MODE_PRIVATE)
            //Escrevendo no arquivo
            fos.write(dados.toString().toByteArray())
            //Fechando o arquivo
            fos.close()
        } catch (e: Exception) {
            Log.e("Error arquivo: ", e.message)
        }
    }

    private fun lerArquivoInterno() {
        var nome: String = ""
        var matricula: String = ""
        try {
            //Recuperando o diretório corrente
            val dir = filesDir
            //Carregando o arquivo
            val file = File("$dir/dados.txt")
            if (file.exists()) {
                //Abrindo o arquivo
                val fis = openFileInput("dados.txt")
                //Criando um array de bytes para armazenar os dados do arquivo
                val buffer = ByteArray(file.length().toInt())
                while (fis.read(buffer) != -1) {
                    var texto = String(buffer)
                    //Pegando os valores de acordo com o índice
                    if (texto.indexOf("matricula") != -1) {
                        val indice = texto.indexOf("=")
                        val indiceFinal = texto.indexOf("\n")
                        matricula = texto.substring(indice + 1, indiceFinal)
                        texto = texto.substring(indiceFinal + 1)
                    }
                    if (texto.indexOf("nome") != -1) {
                        val indice = texto.indexOf("=")
                        val indiceFinal = texto.indexOf("\n")
                        nome = texto.substring(indice + 1, indiceFinal)
                        texto = texto.substring(indiceFinal + 1)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error de arquivo: ", e.message)
        }
        edtMatricula!!.setText(matricula)
        edtNome!!.setText(nome)
    }

    private fun salvarArquivoExterno(matricula: String, nome: String) {
        val dados = StringBuilder()
        dados.append("matricula=$matricula")
        dados.append("\n")
        dados.append("nome=$nome")
        dados.append("\n")
        try {
            //Recuperando o estado da míia
            val estado = Environment.getExternalStorageState()
            //Verificando se a méida está disponível para gravação
            if (estado.equals(Environment.MEDIA_MOUNTED)) {
                //Capturando o diretório externo e passando o nome do arquivo
                val file = File(getExternalFilesDir(null), "/dados.txt")
                val fos = FileOutputStream(file)
                fos.write(dados.toString().toByteArray())
                fos.close()
            }
        } catch (e: Exception) {
            Log.e("Error arquivo: ", e.message)
        }
    }

    private fun lerArquivoExterno() {
        var nome = ""
        var matricula = ""
        try {
            val estado = Environment.getExternalStorageState()
            if (estado == Environment.MEDIA_MOUNTED) {
                val dir = getExternalFilesDir(null)
                val file = File(dir.toString() + "/dados.txt")
                if (file.exists()) {
                    val fis = FileInputStream(file)
                    val buffer = ByteArray(file.length().toInt())
                    while (fis.read(buffer) != -1) {
                        var texto = String(buffer)
                        if (texto.indexOf("matricula") != -1) {
                            val indice = texto.indexOf("=")
                            val indiceFinal = texto.indexOf("\n")
                            matricula = texto.substring(indice + 1, indiceFinal)
                            texto = texto.substring(indiceFinal + 1)
                        }
                        if (texto.indexOf("nome") != -1) {
                            val indice = texto.indexOf("=")
                            val indiceFinal = texto.indexOf("\n")
                            nome = texto.substring(indice + 1, indiceFinal)
                            texto = texto.substring(indiceFinal + 1)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error de arquivo: ", e.message)
        }
        edtMatricula!!.setText(matricula)
        edtNome!!.setText(nome)
    }
}
