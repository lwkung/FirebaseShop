package com.test.firebaseshop

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_row.view.*

class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {

    var titleText = view.item_title
    var priceText = view.item_price
    var image = view.item_image

    fun bindTo(item: Item) {
        titleText.setText(item.title)
        priceText.setText(item.price.toString())
        Glide.with(itemView.context)
            .load(item.imageUrl)
            .apply(RequestOptions().override(120))
            .into(image)
    }

}