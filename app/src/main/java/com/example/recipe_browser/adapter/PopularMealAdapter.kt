package com.example.recipe_browser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_browser.R
import com.example.recipe_browser.model.Meal

class PopularMealAdapter(
    private val meals: List<Meal>,
    private val onClick: (Meal) -> Unit
) : RecyclerView.Adapter<PopularMealAdapter.PopularMealViewHolder>() {

    class PopularMealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgRecipe: ImageView = itemView.findViewById(R.id.imgRecipe)
        val txtRecipeName: TextView = itemView.findViewById(R.id.txtRecipeName)
        val txtTime: TextView = itemView.findViewById(R.id.txtRecipeName)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_recipe, parent, false)

        return PopularMealViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {

        val meal = meals[position]

        holder.txtRecipeName.text = meal.strMeal
        holder.txtTime.text = meal.strCategory ?: "Recipe"

        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.banner_food)
            .into(holder.imgRecipe)

        holder.ratingBar.rating = 4.5f

        holder.itemView.setOnClickListener {
            onClick(meal)
        }
    }

    override fun getItemCount(): Int = meals.size
}