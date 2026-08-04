package com.example.recipe_browser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_browser.model.Meal

class MealAdapter(
    private val meals: List<Meal>,
    private val onItemClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgRecent)
        val textView: TextView = itemView.findViewById(R.id.txtRecentName)
        val textView2: TextView = itemView.findViewById(R.id.txtRecentTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_recipe, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.textView.text = meal.strMeal
        holder.textView2.text = meal.strCategory ?: ""
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .into(holder.imageView)

        holder.itemView.setOnClickListener { onItemClick(meal) }
    }

    override fun getItemCount(): Int = meals.size
}