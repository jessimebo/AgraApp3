import android.os.Parcel
import android.os.Parcelable

data class Animal(
    val id: Int,
    val nombre: String,
    val raza: String,
    val edad: Int,
    val especie: String,
    val genero: String,
    val desparasitacion: String,
    val vacunas: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(raza)
        parcel.writeInt(edad)
        parcel.writeString(especie)
        parcel.writeString(genero)
        parcel.writeString(desparasitacion)
        parcel.writeString(vacunas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Animal> {
        override fun createFromParcel(parcel: Parcel): Animal {
            return Animal(parcel)
        }

        override fun newArray(size: Int): Array<Animal?> {
            return arrayOfNulls(size)
        }
    }
}
