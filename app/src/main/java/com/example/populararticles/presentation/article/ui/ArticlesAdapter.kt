package com.example.populararticles.presentation.article.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.populararticles.R
import com.example.populararticles.entities.Article
import com.example.populararticles.presentation.article.SendSingleItemListener

class ArticlesAdapter(
    private val values: MutableList<Article>,
    private var sendSingleItemListener: SendSingleItemListener<Article>
) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    fun updateList(newItems: MutableList<Article>) {
        this.values.addAll(newItems)
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.apply {
            tv_title.text = item.title
            tv_description.text = item.subsection
            tv_by.text = item.byline
            tv_date.text = item.published_date

            Glide.with(iv_article.context)
                .load(item.byline)
                .placeholder(R.drawable.ic_user_list)
                .into(holder.iv_article)

            //contentView.text = item.abstract
            itemView.setOnClickListener { sendSingleItemListener.sendItem(item) }
        }


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_title: TextView = view.findViewById(R.id.tv_title)
        val tv_description: TextView = view.findViewById(R.id.tv_description)
        val tv_by: TextView = view.findViewById(R.id.tv_by)
        val iv_article: ImageView = view.findViewById(R.id.iv_article_writer)
        val tv_date: TextView = view.findViewById(R.id.tv_date)
        // val contentView: TextView = view.findViewById(R.id.content)
    }
}