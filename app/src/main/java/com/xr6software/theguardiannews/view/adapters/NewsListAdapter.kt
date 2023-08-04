package com.xr6software.theguardiannews.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xr6software.theguardiannews.R
import com.xr6software.theguardiannews.model.NewsArticle
import java.text.SimpleDateFormat

class NewsListAdapter (private val adapterClickListener: AdapterClickListener<String>) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    var itemList = listOf<NewsArticle>()

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {

        val textViewDesc : TextView = v.findViewById(R.id.item_list_test_text)
        val imgViewCellphone: ImageView = v.findViewById(R.id.item_list_test_image)
        val textViewDate : TextView = v.findViewById(R.id.item_list_test_textdate)

    }

    fun updateDataOnView(news: List<NewsArticle>) {
        itemList = news
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return NewsListAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val newsItem : NewsArticle = itemList[position]

        holder.textViewDesc.text = newsItem.headline
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        holder.textViewDate.text = simpleDateFormat.format(newsItem.firstPublicationDate)
        holder.imgViewCellphone.load(newsItem.thumbnail)
        holder.itemView.setOnClickListener {
            adapterClickListener.onClick(newsItem.headline, position)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}