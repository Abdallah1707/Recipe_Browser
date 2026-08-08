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
import com.example.recipe_browser.adapter.RecentHomeAdapter
import com.example.recipe_browser.viewmodel.FavoriteViewModel
import com.example.recipe_browser.viewmodel.HomeViewModel
import com.example.recipe_browser.viewmodel.RecentViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var recentViewModel: RecentViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var popularRecycler: RecyclerView
    private lateinit var categoryRecycler: RecyclerView
    private lateinit var recentRecycler: RecyclerView

    private val favoriteIds = mutableSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

        val imgProfile =
            view.findViewById<ImageView>(
                R.id.imgProfileToolbar
            )

        imgProfile.setOnClickListener {

            parentFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    UserFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        popularRecycler =
            view.findViewById(R.id.rvPopular)

        categoryRecycler =
            view.findViewById(R.id.rvCategories)

        recentRecycler =
            view.findViewById(R.id.rvRecent)

        popularRecycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        categoryRecycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        recentRecycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

        viewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        recentViewModel =
            ViewModelProvider(this)[RecentViewModel::class.java]

        favoriteViewModel =
            ViewModelProvider(this)[FavoriteViewModel::class.java]

        // Favorites from Room

        favoriteViewModel.favorites.observe(
            viewLifecycleOwner
        ) { favorites ->

            favoriteIds.clear()

            favoriteIds.addAll(
                favorites.map {
                    it.idMeal
                }
            )

            recentViewModel.recentMeals.value?.let { recentMeals ->

                recentRecycler.adapter =
                    RecentHomeAdapter(
                        meals = recentMeals,
                        favoriteViewModel = favoriteViewModel,
                        favoriteIds = favoriteIds
                    ) { meal ->

                        parentFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.fragmentContainer,
                                DetailsFragment.newInstance(
                                    meal.idMeal
                                )
                            )
                            .addToBackStack(null)
                            .commit()
                    }
            }
        }

        // Popular Recipes

        viewModel.meals.observe(
            viewLifecycleOwner
        ) { meals ->

            popularRecycler.adapter =
                MealAdapter(meals) { meal ->

                    recentViewModel.addRecent(meal)

                    parentFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragmentContainer,
                            DetailsFragment.newInstance(
                                meal.idMeal
                            )
                        )
                        .addToBackStack(null)
                        .commit()
                }
        }

        // Categories

        viewModel.categories.observe(
            viewLifecycleOwner
        ) { categories ->

            categoryRecycler.adapter =
                CategoryAdapter(categories) { category ->

                    val searchFragment =
                        SearchFragment()

                    val bundle =
                        Bundle()

                    bundle.putString(
                        "category",
                        category.strCategory
                    )

                    searchFragment.arguments =
                        bundle

                    parentFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragmentContainer,
                            searchFragment
                        )
                        .addToBackStack(null)
                        .commit()
                }
        }

        // Recent Recipes

        recentViewModel.recentMeals.observe(
            viewLifecycleOwner
        ) { recentMeals ->

            recentRecycler.adapter =
                RecentHomeAdapter(
                    meals = recentMeals,
                    favoriteViewModel = favoriteViewModel,
                    favoriteIds = favoriteIds
                ) { meal ->

                    parentFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragmentContainer,
                            DetailsFragment.newInstance(
                                meal.idMeal
                            )
                        )
                        .addToBackStack(null)
                        .commit()
                }
        }

        viewModel.loadMeals()
        viewModel.loadCategories()

        return view
    }
}