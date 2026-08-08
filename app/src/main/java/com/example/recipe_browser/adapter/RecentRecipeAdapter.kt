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
import com.example.recipe_browser.model.FavoriteMeal
import com.example.recipe_browser.model.Meal

class RecentRecipeAdapter(
    private val meals: List<Meal>,
    private val favoriteIds: MutableSet<String>,
    private val onFavoriteClick: (FavoriteMeal, Boolean) -> Unit,
    private val onClick: (Meal) -> Unit
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

        val meal = meals[position]

        holder.txtRecentName.text =
            meal.strMeal

        holder.txtRecentTime.text =
            meal.strCategory ?: "Recipe"

        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.banner_food)
            .into(holder.imgRecent)

        updateFavoriteIcon(
            holder.btnFavorite,
            meal.idMeal
        )

        holder.btnFavorite.setOnClickListener {

            val isFavorite =
                favoriteIds.contains(meal.idMeal)

            val favoriteMeal = FavoriteMeal(
                idMeal = meal.idMeal,
                strMeal = meal.strMeal,
                strCategory = meal.strCategory,
                strArea = meal.strArea,
                strInstructions = meal.strInstructions,
                strMealThumb = meal.strMealThumb,
                strYoutube = meal.strYoutube
            )

            if (isFavorite) {

                favoriteIds.remove(meal.idMeal)

            } else {

                favoriteIds.add(meal.idMeal)
            }

            updateFavoriteIcon(
                holder.btnFavorite,
                meal.idMeal
            )

            onFavoriteClick(
                favoriteMeal,
                !isFavorite
            )
        }

        holder.itemView.setOnClickListener {
            onClick(meal)
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
        meals.size
}