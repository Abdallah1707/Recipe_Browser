package com.example.recipe_browser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_browser.R
import com.example.recipe_browser.model.local.entities.RecipeEntity

class FavoriteAdapter(
    private val meals: List<RecipeEntity>,
    private val onClick: (RecipeEntity) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val imgRecent: ImageView =
            itemView.findViewById(R.id.imgRecent)

        val txtRecentName: TextView =
            itemView.findViewById(R.id.txtRecentName)

        val txtRecentTime: TextView =
            itemView.findViewById(R.id.txtRecentTime)

        val btnFavorite: ImageButton =
            itemView.findViewById(R.id.btnFavorite)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_recent_recipe,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val meal = meals[position]

        holder.txtRecentName.text =
            meal.strMeal

        holder.txtRecentTime.text =
            meal.strCategory

        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.banner_food)
            .into(holder.imgRecent)

        holder.btnFavorite.setImageResource(
            R.drawable.ic_favorite
        )

        holder.itemView.setOnClickListener {
            onClick(meal)
        }
    }

    override fun getItemCount(): Int {
        return meals.size
    }
}