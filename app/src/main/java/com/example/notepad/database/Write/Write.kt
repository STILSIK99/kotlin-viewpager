package com.example.notepad.database.Write

import android.os.Parcel
import android.os.Parcelable

data class Write(val title: String, val content: String, val id_cate: Int, val id: Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeInt(id_cate)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Write> {
        override fun createFromParcel(parcel: Parcel): Write {
            return Write(parcel)
        }

        override fun newArray(size: Int): Array<Write?> {
            return arrayOfNulls(size)
        }
    }
}