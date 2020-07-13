package br.edu.ifb.android

class Curso {

    lateinit var uid: String
    lateinit var descricao: String
    var cargaHoraria: Int = 0

    override fun toString(): String {
        return "$descricao\n$cargaHoraria"
    }

}