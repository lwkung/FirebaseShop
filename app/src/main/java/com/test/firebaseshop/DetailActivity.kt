package com.test.firebaseshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val TAG = DetailActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent.getParcelableExtra<Item>("ITEM")
        Log.d(TAG, "onCreate: ${item.id} / ${item.title}")
        web.settings.javaScriptEnabled = true
        web.loadUrl("https://litotom.com/shop/android9/")
    }
}
