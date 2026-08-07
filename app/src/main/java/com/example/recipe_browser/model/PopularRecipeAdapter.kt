package com.example.recipe_browser.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_browser.R

class PopularRecipeAdapter(
    private val meals: List<Meal>
) : RecyclerView.Adapter<PopularRecipeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.imgRecipe)
        val title = view.findViewById<TextView>(R.id.txtRecipeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = meals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals[position]

        holder.title.text = meal.strMeal

        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.image)
    }
}