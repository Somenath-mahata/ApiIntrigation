package com.example.apiintrigation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apiintrigation.databinding.PostlistRowBinding
import com.example.apiintrigation.model.Post

class PostsAdapter(val context: Context, var postsList: ArrayList<Post>) :
    RecyclerView.Adapter<PostsAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: PostlistRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PostlistRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding)
        {
            with(postsList[position])
            {
                usersid.text = userId.toString()
                ids.text = id.toString()
                titles.text = title
            }
        }
    }


}