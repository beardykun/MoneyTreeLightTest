package com.example.moneytreelighttest.model

import android.os.Parcel
import android.os.Parcelable


data class Account(
    val id: Int,
    val name: String?,
    val institution: String?,
    val currency: String?,
    val currentBalance: Double,
    val currentBalanceInBase: Double
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(institution)
        parcel.writeString(currency)
        parcel.writeDouble(currentBalance)
        parcel.writeDouble(currentBalanceInBase)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Account> {
        const val ID = "id"
        const val NAME = "name"
        const val INSTITUTION = "institution"
        const val CURRENCY = "currency"
        const val CURRENT_BALANCE = "current_balance"
        const val CURRENT_BALANCE_IN_BASE = "current_balance_in_base"

        override fun createFromParcel(parcel: Parcel): Account {
            return Account(parcel)
        }

        override fun newArray(size: Int): Array<Account?> {
            return arrayOfNulls(size)
        }
    }
}