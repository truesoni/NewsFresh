package com.dev.newsfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener : NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>(){

    private val items : ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent , false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        Glide.with(holder.itemView.context).load(currentItem.image_url).into(holder.image)
        holder.source.text = currentItem.source_id
        holder.creator.text = currentItem.creator
        holder.pubDate.text = currentItem.pubDate
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews : ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView: TextView = itemView.findViewById(R.id.title)
   val source : TextView = itemView.findViewById(R.id.source_id)
    val image : ImageView = itemView.findViewById(R.id.image)
    val creator : TextView = itemView.findViewById(R.id.creator)
    val pubDate : TextView = itemView.findViewById(R.id.pubDate)
}

interface NewsItemClicked{
    fun onItemClicked(item: News)
}