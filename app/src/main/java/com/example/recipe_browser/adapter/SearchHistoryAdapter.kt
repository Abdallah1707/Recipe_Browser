package com.example.recipe_browser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.R
import com.example.recipe_browser.model.local.entities.SearchHistoryEntity

class SearchHistoryAdapter(
    private var searches: List<SearchHistoryEntity>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val keyword: TextView =
            itemView.findViewById(R.id.tvSearchKeyword)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_search_history,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val search = searches[position]

        holder.keyword.text = search.keyword

        holder.itemView.setOnClickListener {
            onClick(search.keyword)
        }
    }

    override fun getItemCount(): Int =
        searches.size
}