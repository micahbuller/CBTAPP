package com.example.cbtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_new_card.*
import kotlinx.android.synthetic.main.activity_add_new_catrgory_card.*
import kotlinx.android.synthetic.main.activity_category_clicked_page.*

class CategoryClickedPage : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth

    class PostModel(
        val cardLie: String = "",
        val cardTruth: String = ""
    )

    private var postList: List<PostModel> = ArrayList()

    private val CardRecyclerAdapter: CardRecyclerAdapter = CardRecyclerAdapter(postList)

    private var firestoreListener: ListenerRegistration? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_clicked_page)

        //getting data from the intent
        var intent = intent
        val categoryCardname = intent.getStringExtra("categoryCardName")

        categoryCardDisplay.text = categoryCardname

        val actionbar = supportActionBar
        actionbar!!.title = categoryCardname
        actionbar.setDisplayHomeAsUpEnabled(true)

        val user = Firebase.auth.currentUser

        if (user != null) {
            // User is signed in
            val email = user.email


        } else {
            // No user is signed in
            Toast.makeText(baseContext, "No user::", Toast.LENGTH_SHORT).show()
        }

        subscribeToRealtimeUpdates()
        card_recycler_view.layoutManager = LinearLayoutManager(this)
        card_recycler_view.adapter =  CardRecyclerAdapter

    }

    private fun loadPostData() {

        getPostList().addOnCompleteListener{
            if (it.isSuccessful){
                postList = it.result!!.toObjects(PostModel::class.java)
                CardRecyclerAdapter.postListItems = postList
                CardRecyclerAdapter.notifyDataSetChanged()
            }else{
                Log.d("Fail to get list data", "Error")
            }
        }
    }

    fun subscribeToRealtimeUpdates(){
        //Now we are setting a listener to listen for updates.
        firestoreListener = db!!.collection("users")
            .document(com.example.cbtapp.user?.email.toString())
            .collection("categoryCardNames")
            .document(categoryCardDisplay.text.toString())
            .collection("cardNames").addSnapshotListener { querySnapshot, e ->
                e?.let { Toast.makeText(this, "failed to refresh querysnapshot", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener}
                querySnapshot?.let {
                    postList = it.toObjects(PostModel::class.java)
                    CardRecyclerAdapter.postListItems = postList
                    CardRecyclerAdapter.notifyDataSetChanged()
                }
            }
    }

    fun getPostList(): Task<QuerySnapshot> {
        return db.collection("users")
            .document(user?.email.toString())
            .collection("categoryCardNames")
            .document(categoryCardDisplay.text.toString())
            .collection("cardNames").get()

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun addNewCardTrigger(view: View) {

        val categoryCardName = categoryCardDisplay.text

        val newIntent = Intent(this, AddNewCard::class.java)
        newIntent.putExtra("categoryCardName", categoryCardName)
        startActivity(newIntent)

    }

}