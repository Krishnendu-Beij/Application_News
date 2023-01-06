package com.example.applicationnews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applicationsomething.Article
import com.example.applicationsomething.News
import kotlinx.android.synthetic.main.item_layout.view.*

class NewsAdapter (val context: Context, val articles: List<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(viewItem : View) : RecyclerView.ViewHolder(viewItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val newsView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return NewsViewHolder(newsView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.apply {
            textViewNewsTitle.text = articles.get(position).title
        }
        holder.itemView.textViewNewsDescription.text = articles[position].description
        // image binging using glide
        Glide.with(context).load(articles[position].urlToImage).into(holder.itemView.imageViewNewsImage)
//        /////
        holder.itemView.setOnClickListener{
            Toast.makeText(context,"clicked on ${articles[position].title}",Toast.LENGTH_LONG).show()
            val my_intent = Intent(context,DetailActivity::class.java)
            my_intent.putExtra("EXTRA_URL",articles[position].url)
            context.startActivity(my_intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}