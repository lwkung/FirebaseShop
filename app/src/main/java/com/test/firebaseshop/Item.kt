package com.test.firebaseshop

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties  //未來如果Firebase有多出認不出的欄位時，可以不用轉成這個物件
data class Item(
    var title: String,
    var price: Int,
    var imageUrl: String,
    @get:Exclude var id: String?,
    var content: String,
    var viewCount: Int
) : Parcelable {
    constructor() : this("", 0, "", "", "", 0)
}