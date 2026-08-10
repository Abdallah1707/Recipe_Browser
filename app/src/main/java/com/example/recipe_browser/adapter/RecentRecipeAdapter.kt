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

class RecentRecipeAdapter(
    private val recipes: List<RecipeEntity>,
    private val favoriteIds: MutableSet<String>,
    private val onFavoriteClick: (RecipeEntity) -> Unit,
    private val onClick: (RecipeEntity) -> Unit

) : RecyclerView.Adapter<RecentRecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) :
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
    ): RecipeViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_recent_recipe,
                parent,
                false
            )

        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecipeViewHolder,
        position: Int
    ) {

        val recipe = recipes[position]

        holder.txtRecentName.text = recipe.strMeal

        holder.txtRecentTime.text =
            recipe.strCategory.ifEmpty { "Recipe" }

        Glide.with(holder.itemView.context)
            .load(recipe.strMealThumb)
            .placeholder(R.drawable.banner_food)
            .into(holder.imgRecent)

        updateFavoriteIcon(
            holder.btnFavorite,
            recipe.idMeal
        )

        holder.btnFavorite.setOnClickListener {

            onFavoriteClick(recipe)

            if (favoriteIds.contains(recipe.idMeal)) {
                favoriteIds.remove(recipe.idMeal)
            } else {
                favoriteIds.add(recipe.idMeal)
            }

            updateFavoriteIcon(
                holder.btnFavorite,
                recipe.idMeal
            )
        }

        holder.itemView.setOnClickListener {
            onClick(recipe)
        }
    }

    private fun updateFavoriteIcon(
        button: ImageButton,
        mealId: String
    ) {

        if (favoriteIds.contains(mealId)) {

            button.setImageResource(
                R.drawable.ic_favorite
            )

        } else {

            button.setImageResource(
                R.drawable.ic_favorite_border
            )
        }
    }

    override fun getItemCount(): Int =
        recipes.size
}