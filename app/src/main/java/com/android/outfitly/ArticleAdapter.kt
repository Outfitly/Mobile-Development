package com.android.outfitly

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.outfitly.data.api.model.Article
import com.android.outfitly.databinding.ItemArticleBinding
import com.bumptech.glide.Glide

class ArticleAdapter(
    private var newsFashion :List<Article>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return UpcomingViewHolder(binding)
}

    override fun getItemCount(): Int {
        return newsFashion.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UpcomingViewHolder).bind(newsFashion[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newsFashion: List<Article>){
        this.newsFashion= newsFashion
        notifyDataSetChanged()
    }

    inner class UpcomingViewHolder  (
        private val itemEventBinding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(itemEventBinding.root) {
        fun bind(article: Article) {

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .centerCrop()
                .into(itemEventBinding.ivEventBanner)
            itemEventBinding.tvEventTitle.text = article.title

            itemEventBinding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(article.url)
                }

                if (intent.resolveActivity(itemView.context.packageManager) != null) {
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
