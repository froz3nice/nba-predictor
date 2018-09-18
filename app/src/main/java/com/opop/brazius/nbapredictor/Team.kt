package com.opop.brazius.nbapredictor

import android.os.Parcel
import android.os.Parcelable
import android.view.View

data class Team(var isHeader: Boolean = true) : Parcelable {

    var name = ""
    var logo = 0
    var place = 0
    var isOut = ""
    var abbr = ""
    var visibility = View.GONE

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        logo = parcel.readInt()
        place = parcel.readInt()
        isOut = parcel.readString()
        abbr = parcel.readString()
        visibility = parcel.readInt()
        isHeader = parcel.readByte() != 0.toByte()
    }

    constructor(name: String, logo: Int,abbr: String) : this() {
        this.name = name
        this.logo = logo
        this.abbr = abbr
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(logo)
        parcel.writeInt(place)
        parcel.writeString(isOut)
        parcel.writeString(abbr)
        parcel.writeInt(visibility)
        parcel.writeByte(if (isHeader) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Team> {
        override fun createFromParcel(parcel: Parcel): Team {
            return Team(parcel)
        }

        override fun newArray(size: Int): Array<Team?> {
            return arrayOfNulls(size)
        }
    }
}
