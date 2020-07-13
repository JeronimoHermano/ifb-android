package br.edu.ifb.android

import android.os.Parcel
import android.os.Parcelable

class Curso() : Parcelable {

    // codigo (Integer), descricao (String) e cargaHoraria (Integer).
    var codigo: Int = 0
    var descricao: String = ""
    var cargaHoraria: Int = 0

    constructor(parcel: Parcel) : this() {
        codigo = parcel.readInt()
        descricao = parcel.readString().toString()
        cargaHoraria = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(codigo)
        parcel.writeString(descricao)
        parcel.writeInt(cargaHoraria)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Curso> {
        override fun createFromParcel(parcel: Parcel): Curso {
            return Curso(parcel)
        }

        override fun newArray(size: Int): Array<Curso?> {
            return arrayOfNulls(size)
        }
    }

}