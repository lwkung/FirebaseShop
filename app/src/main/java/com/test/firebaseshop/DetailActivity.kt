package com.test.firebaseshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.test.firebaseshop.model.Item
import com.test.firebaseshop.model.WatchItem
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val TAG = DetailActivity::class.java.simpleName
    lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        item = intent.getParcelableExtra<Item>("ITEM")
        Log.d(TAG, "onCreate: ${item.id} / ${item.title}")
        web.settings.javaScriptEnabled = true
        web.loadUrl(item.content)
        //web.loadUrl("https://litotom.com/shop/android9/")

        // read watch item
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        FirebaseFirestore.getInstance().collection("users")
            .document(uid!!)
            .collection("watchItems")
            .document(item.id).get()
            .addOnCompleteListener { tack ->
                if (tack.isSuccessful) {
                    val watchItem = tack.result?.toObject(WatchItem::class.java)
                    if (watchItem != null) {
                        watch.isChecked = true
                    }
                }
            }
        // watches
        watch.setOnCheckedChangeListener { buttonView, isChecked ->
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (isChecked) {
                FirebaseFirestore.getInstance().collection("users")
                    .document(uid!!)
                    .collection("watchItems")
                    .document(item.id)
                    .set(WatchItem(item.id))
            } else {
                FirebaseFirestore.getInstance().collection("users")
                    .document(uid!!)
                    .collection("watchItems")
                    .document(item.id)
                    .delete()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        item.viewCount++
        item.id?.let {
            FirebaseFirestore.getInstance().collection("items")
                .document(it).update("viewCount", item.viewCount)
        }
    }

}
