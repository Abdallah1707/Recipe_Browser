package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.R
import com.example.recipe_browser.adapter.CategoryAdapter
import com.example.recipe_browser.adapter.MealAdapter
import com.example.recipe_browser.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var popularRecycler: RecyclerView
    private lateinit var categoryRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val imgProfile = view.findViewById<ImageView>(R.id.imgProfileToolbar)

        imgProfile.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, UserFragment())
                .addToBackStack(null)
                .commit()

        }

        popularRecycler = view.findViewById(R.id.rvPopular)
        categoryRecycler = view.findViewById(R.id.rvCategories)

        popularRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        categoryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.meals.observe(viewLifecycleOwner) { meals ->

            popularRecycler.adapter = MealAdapter(meals) { meal ->

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainer,
                        DetailsFragment.newInstance(meal.idMeal)
                    )
                    .addToBackStack(null)
                    .commit()

            }

        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->

            categoryRecycler.adapter = CategoryAdapter(categories)

        }

        viewModel.loadMeals()
        viewModel.loadCategories()

        return view
    }
}