package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.R
import com.example.recipe_browser.adapter.CategoryAdapter
import com.example.recipe_browser.adapter.MealAdapter
import com.example.recipe_browser.adapter.RecentHomeAdapter
import com.example.recipe_browser.model.RepositoryProvider
import com.example.recipe_browser.viewmodel.HomeViewModel
import com.example.recipe_browser.viewmodel.RecentViewModel
import com.example.recipe_browser.viewmodel.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var recentViewModel: RecentViewModel
    private lateinit var recentRecycler: RecyclerView
    private lateinit var popularRecycler: RecyclerView
    private lateinit var categoryRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val repository =
            RepositoryProvider.provideRepository(requireContext())

        val factory =
            ViewModelFactory(repository)

        viewModel =
            ViewModelProvider(
                this,
                factory
            )[HomeViewModel::class.java]

        recentViewModel =
            ViewModelProvider(
                this,
                factory
            )[RecentViewModel::class.java]

        val imgProfile = view.findViewById<ImageView>(R.id.imgProfileToolbar)

        val etSearch = view.findViewById<TextInputEditText>(R.id.etSearch)
        etSearch.isFocusable = false
        etSearch.isClickable = true

        val imgBanner = view.findViewById<ImageView>(R.id.imgBanner)
        val txtBannerSubtitle = view.findViewById<TextView>(R.id.tv_BannerSubtitle)
        val bannerCard = view.findViewById<androidx.cardview.widget.CardView>(R.id.bannerCard)

        popularRecycler = view.findViewById(R.id.rvPopular)
        categoryRecycler = view.findViewById(R.id.rvCategories)
        recentRecycler = view.findViewById(R.id.rvRecent)

        recentRecycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        popularRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        categoryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.categories.observe(viewLifecycleOwner) { categories ->

            categoryRecycler.adapter = CategoryAdapter(categories) { category ->

                val searchFragment = SearchFragment()
                val bundle = Bundle()
                bundle.putString("category", category.strCategory)
                searchFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, searchFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        etSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SearchFragment())
                .addToBackStack(null)
                .commit()
        }

        imgProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, UserFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.randomMeal.observe(viewLifecycleOwner) { meal ->
            if (meal == null) return@observe

            txtBannerSubtitle.text = meal.strMeal

            Glide.with(this)
                .load(meal.strMealThumb)
                .into(imgBanner)

            bannerCard.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, DetailsFragment.newInstance(meal.idMeal))
                    .addToBackStack(null)
                    .commit()
            }
        }

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
        recentViewModel.recentRecipes.observe(
            viewLifecycleOwner
        ) { recipes ->

            recentRecycler.adapter =
                RecentHomeAdapter(recipes) { recipe ->

                    parentFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragmentContainer,
                            DetailsFragment.newInstance(
                                recipe.idMeal
                            )
                        )
                        .addToBackStack(null)
                        .commit()
                }
        }
        viewModel.loadMeals()
        viewModel.loadCategories()
        viewModel.loadRandomMeal()
        recentViewModel.loadRecentRecipes()
        return view
    }
}