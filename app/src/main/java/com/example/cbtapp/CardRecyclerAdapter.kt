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



class CardRecyclerAdapter (var postListItems: List<CategoryClickedPage.PostModel>)
    : RecyclerView.Adapter<CardRecyclerAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(postModel: CategoryClickedPage.PostModel){
            itemView.image_list_title.text = postModel.cardLie
            itemView.image_list_description.text = postModel.cardTruth
            itemView.image_list_info.setImageResource(R.mipmap.ic_launcher_info_icon_round)
            itemView.setOnClickListener{
                Log.d("Congrats", "bind: Clicked ${postModel.cardLie}")

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardRecyclerAdapter.ViewHolder {
        val v :View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cbt, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CardRecyclerAdapter.ViewHolder, position: Int) {

        (holder).bind(postListItems[position])

    }

    override fun getItemCount(): Int {
        return postListItems.size
    }

}