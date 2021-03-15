package com.example.cbtapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.nav_header.*

class HomePage : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth

    lateinit var toggle: ActionBarDrawerToggle

    class PostModel(
        val categoryCardName: String = "",
        val categoryCardDesc: String = ""
    )

    private var postList: List<PostModel> = ArrayList()

    private val RecyclerAdapter: RecyclerAdapter = RecyclerAdapter(postList)

    class ProductModel(val categoryCardName: String = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)


        //setting button toggle
        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //respond to menu clicks
        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homePage -> startActivity(Intent(this,HomePage::class.java))
                R.id.profilePage -> startActivity(Intent(this,ProfilePage::class.java))
                R.id.howPage -> startActivity(Intent(this,HowItWorks::class.java))
                R.id.signOut -> {

                    signOut()

                }
            }
            true
        }
        

        val user = Firebase.auth.currentUser

        if (user != null) {
            // User is signed in
            val email = user.email


        } else {
            // No user is signed in
            Toast.makeText(baseContext, "No user::", Toast.LENGTH_SHORT).show()
        }

        loadPostData()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter =  RecyclerAdapter

    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this,MainActivity::class.java))

    }

    private fun loadPostData() {

        getPostList().addOnCompleteListener{
            if (it.isSuccessful){
                postList = it.result!!.toObjects(PostModel::class.java)
                RecyclerAdapter.postListItems = postList
                RecyclerAdapter.notifyDataSetChanged()
            }else{
                Log.d("Fail to get list data", "Error")
            }
        }
    }

    fun getPostList(): Task<QuerySnapshot> {
        return db.collection("users")
            .document(user?.email.toString())
            .collection("categoryCardNames").get()
    }


    fun addNewCategoryTrigger(view: View) {
        startActivity(Intent(this,AddNewCatrgoryCard::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}