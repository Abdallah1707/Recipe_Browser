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
import com.example.recipe_browser.model.RecentMeal
import com.example.recipe_browser.viewmodel.FavoriteViewModel

class RecentHomeAdapter(
    private val meals: List<RecentMeal>,
    private val favoriteViewModel: FavoriteViewModel,
    private val favoriteIds: MutableSet<String>,
    private val onClick: (RecentMeal) -> Unit
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

        val meal = meals[position]

        holder.name.text = meal.strMeal
        holder.category.text =
            meal.strCategory ?: "Recipe"

        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.banner_food)
            .into(holder.image)

        updateFavoriteIcon(holder, meal.idMeal)

        holder.favorite.setOnClickListener {

            val favoriteMeal = FavoriteMeal(
                idMeal = meal.idMeal,
                strMeal = meal.strMeal,
                strCategory = meal.strCategory,
                strArea = null,
                strInstructions = null,
                strMealThumb = meal.strMealThumb,
                strYoutube = null
            )

            if (favoriteIds.contains(meal.idMeal)) {

                favoriteViewModel.removeFavoriteById(
                    meal.idMeal
                )

                favoriteIds.remove(meal.idMeal)

            } else {

                favoriteViewModel.addFavorite(
                    favoriteMeal
                )

                favoriteIds.add(meal.idMeal)
            }

            updateFavoriteIcon(
                holder,
                meal.idMeal
            )
        }

        holder.itemView.setOnClickListener {
            onClick(meal)
        }
    }

    private fun updateFavoriteIcon(
        holder: ViewHolder,
        id: String
    ) {

        if (favoriteIds.contains(id)) {

            holder.favorite.setImageResource(
                R.drawable.ic_favorite
            )

        } else {

            holder.favorite.setImageResource(
                R.drawable.ic_favorite_border
            )
        }
    }

    override fun getItemCount(): Int =
        meals.size
}