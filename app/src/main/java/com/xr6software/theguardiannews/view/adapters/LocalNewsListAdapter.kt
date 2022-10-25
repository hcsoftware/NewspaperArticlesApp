package com.xr6software.theguardiannews.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xr6software.theguardiannews.R
import com.xr6software.theguardiannews.model.NewsDetailItem
import com.xr6software.theguardiannews.model.toNewsDetailItemLocal
import java.text.SimpleDateFormat

class LocalNewsListAdapter(val localNewsListAdapterClickListener: LocalNewsListAdapterClickListener) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    var newsList = listOf<NewsDetailItem>()

    fun updateDataOnView(news: List<NewsDetailItem>?) {

        news?.let {
            newsList = it
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListAdapter.ViewHolder {
        return NewsListAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsListAdapter.ViewHolder, position: Int) {

        val newsItem: NewsDetailItem = newsList[position]

        holder.textViewDesc.text = newsItem.headline
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        holder.textViewDate.text = simpleDateFormat.format(newsItem.firstPublicationDate)
        holder.imgViewCellphone.load(newsItem.thumbnail)
        holder.itemView.setOnClickListener {
            localNewsListAdapterClickListener.onClick(newsItem.toNewsDetailItemLocal())
        }

    }

    override fun getItemCount() = newsList.size

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val textViewDesc: TextView = v.findViewById(R.id.item_list_test_text)
        val imgViewCellphone: ImageView = v.findViewById(R.id.item_list_test_image)
        val textViewDate: TextView = v.findViewById(R.id.item_list_test_textdate)

    }
}