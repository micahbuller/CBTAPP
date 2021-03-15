package com.example.cbtapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_add_new_catrgory_card.view.*
import kotlinx.android.synthetic.main.list_item_cbt.view.*
import android.content.Intent as Intent



class RecyclerAdapter (var postListItems: List<HomePage.PostModel>)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(postModel: HomePage.PostModel){
            itemView.image_list_title.text = postModel.categoryCardName
            itemView.image_list_description.text = postModel.categoryCardDesc
            itemView.image_list_info.setImageResource(R.mipmap.ic_launcher_info_icon_round)
            itemView.setOnClickListener{
                Log.d("Congrats", "bind: Clicked ${postModel.categoryCardName}")

                val intent = Intent(itemView.context, CategoryClickedPage::class.java)
                intent.putExtra("categoryCardName", postModel.categoryCardName)
                itemView.context.startActivity(intent)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v :View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cbt, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {

        (holder).bind(postListItems[position])

    }

    override fun getItemCount(): Int {
        return postListItems.size
    }

}