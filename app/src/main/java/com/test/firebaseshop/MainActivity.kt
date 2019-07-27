package com.test.firebaseshop

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {

    private val RC_SIGNIN = 100
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var adapter: FirestoreRecyclerAdapter<Item, ItemHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        verify_email.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(it, "Verify email sent", Snackbar.LENGTH_LONG).show()
                    }
                }
        }
        //setupRecyclerView
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)
        val query = FirebaseFirestore.getInstance()
            .collection("items")
            .limit(10)
        val options = FirestoreRecyclerOptions.Builder<Item>()
            .setQuery(query, Item::class.java)
            .build()
        adapter = object : FirestoreRecyclerAdapter<Item, ItemHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row, parent, false)
                return ItemHolder(view)
            }

            override fun onBindViewHolder(holder: ItemHolder, position: Int, item: Item) {
                holder.bindTo(item)
                holder.itemView.setOnClickListener {
                    itemClicked(item, position)
                }
            }
        }
        recycler.adapter = adapter
    }

    private fun itemClicked(item: Item, position: Int) {
        Log.d(TAG, "itemClicked: ${item.title} / $position")
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        val user = FirebaseAuth.getInstance().currentUser
        Log.d(TAG, "onAuthStateChanged: ${user?.uid}")
        if (user != null) {
            user_info.setText("Email: ${user.email} / ${user.isEmailVerified}")
            //verify_email.visibility = if (user.isEmailVerified) View.GONE else View.VISIBLE
        } else {
            user_info.setText("Not login")
            verify_email.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(this)
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(this)
        adapter.stopListening()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_signin -> {
                val whiteList = listOf<String>("tw", "hk", "cn", "au")
                val myLayout = AuthMethodPickerLayout.Builder(R.layout.sign_up)
                    .setEmailButtonId(R.id.signup_email)
                    .setFacebookButtonId(R.id.signup_facebook)
                    .setGoogleButtonId(R.id.signup_google)
                    .setPhoneButtonId(R.id.sighup_sms)
                    .build()
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                            Arrays.asList(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build(),
                                AuthUI.IdpConfig.FacebookBuilder().build(),
                                AuthUI.IdpConfig.PhoneBuilder()
                                    .setWhitelistedCountries(whiteList)
                                    .setDefaultCountryIso("tw")
                                    .build()
                            )
                        )
                        .setIsSmartLockEnabled(false)
                        .setLogo(R.drawable.shop)
                        .setTheme(R.style.SignUp)
                        .setAuthMethodPickerLayout(myLayout)
                        .build(),
                    RC_SIGNIN
                )
                /*startActivityForResult(
                    Intent(this, SignInActivity::class.java),
                    RC_SIGNIN
                )*/
                true
            }
            R.id.action_signout -> {
                FirebaseAuth.getInstance().signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
