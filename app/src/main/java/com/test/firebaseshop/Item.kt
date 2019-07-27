package com.test.firebaseshop

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(var title: String,
                var price: Int,
                var imageUrl: String,
                var id: String?,
                var content: String) : Parcelable{
    constructor() : this("", 0, "", "","")
}