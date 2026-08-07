package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipe_browser.R
import com.example.recipe_browser.viewmodel.DetailsViewModel

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel

    private lateinit var imgMeal: ImageView
    private lateinit var txtName: TextView
    private lateinit var txtInstructions: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_recipe_detail, container, false)

        imgMeal = view.findViewById(R.id.imgRecipe)
        txtName = view.findViewById(R.id.txtRecipeName)
        txtInstructions = view.findViewById(R.id.txtInstructions)

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        val mealId = arguments?.getString("id") ?: ""

        if (mealId.isNotEmpty()) {
            viewModel.loadMeal(mealId)
        }

        viewModel.meal.observe(viewLifecycleOwner) { meal ->

            txtName.text = meal.strMeal
            txtInstructions.text = meal.strInstructions

            Glide.with(requireContext())
                .load(meal.strMealThumb)
                .placeholder(R.drawable.banner_food)
                .into(imgMeal)
        }

        return view
    }

    companion object {

        fun newInstance(id: String): DetailsFragment {

            val fragment = DetailsFragment()

            val bundle = Bundle()
            bundle.putString("id", id)

            fragment.arguments = bundle

            return fragment
        }
    }
}