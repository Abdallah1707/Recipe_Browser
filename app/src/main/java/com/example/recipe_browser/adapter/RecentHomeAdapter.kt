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
import com.example.recipe_browser.viewmodel.FavoriteViewModel

class RecentHomeAdapter(
    private val recipes: List<RecipeEntity>,
    private val onClick: (RecipeEntity) -> Unit
) : RecyclerView.Adapter<RecentHomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val image: ImageView =
            itemView.findViewById(R.id.imgRecent)

        val name: TextView =
            itemView.findViewById(R.id.txtRecentName)

        val category: TextView =
            itemView.findViewById(R.id.txtRecentTime)

        val favorite: ImageButton =
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

        val recipe = recipes[position]

        holder.name.text = recipe.strMeal

        holder.category.text =
            recipe.strCategory

        Glide.with(holder.itemView.context)
            .load(recipe.strMealThumb)
            .placeholder(R.drawable.banner_food)
            .into(holder.image)

        holder.favorite.setImageResource(
            if (recipe.isFavorite)
                R.drawable.ic_favorite
            else
                R.drawable.ic_favorite_border
        )

        holder.itemView.setOnClickListener {
            onClick(recipe)
        }
    }

    override fun getItemCount(): Int =
        recipes.size
}