package com.example.recipe_browser.fragments

import android.content.Context
import android.net.Uri
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
import java.io.File

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var recentViewModel: RecentViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var popularRecycler: RecyclerView
    private lateinit var categoryRecycler: RecyclerView
    private lateinit var recentRecycler: RecyclerView
    private lateinit var imgProfile: ImageView

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

        imgProfile =
            view.findViewById(
                R.id.imgProfileToolbar
            )

        popularRecycler =
            view.findViewById(
                R.id.rvPopular
            )

        categoryRecycler =
            view.findViewById(
                R.id.rvCategories
            )

        recentRecycler =
            view.findViewById(
                R.id.rvRecent
            )

        // Profile

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

        // Popular Recycler

        popularRecycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        // Categories Recycler

        categoryRecycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        // Recent Recycler

        recentRecycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

        // ViewModels

        viewModel =
            ViewModelProvider(this)[
                HomeViewModel::class.java
            ]

        recentViewModel =
            ViewModelProvider(this)[
                RecentViewModel::class.java
            ]

        favoriteViewModel =
            ViewModelProvider(this)[
                FavoriteViewModel::class.java
            ]

        // Favorites

        favoriteViewModel.favorites.observe(
            viewLifecycleOwner
        ) { favorites ->

            favoriteIds.clear()

            favoriteIds.addAll(
                favorites.map {
                    it.idMeal
                }
            )

            recentViewModel.recentMeals.value?.let {
                    recentMeals ->

                setRecentAdapter(
                    recentMeals
                )
            }
        }

        // Popular Recipes

        viewModel.meals.observe(
            viewLifecycleOwner
        ) { meals ->

            popularRecycler.adapter =
                MealAdapter(meals) { meal ->

                    // Save to Recent

                    recentViewModel.addRecent(
                        meal
                    )

                    // Open Details

                    openDetails(
                        meal.idMeal
                    )
                }
        }

        // Categories

        viewModel.categories.observe(
            viewLifecycleOwner
        ) { categories ->

            categoryRecycler.adapter =
                CategoryAdapter(
                    categories
                ) { category ->

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

            setRecentAdapter(
                recentMeals
            )
        }

        // Load data

        viewModel.loadMeals()

        viewModel.loadCategories()

        return view
    }

    private fun setRecentAdapter(
        recentMeals: List<com.example.recipe_browser.model.RecentMeal>
    ) {

        recentRecycler.adapter =
            RecentHomeAdapter(
                meals = recentMeals,
                favoriteViewModel = favoriteViewModel,
                favoriteIds = favoriteIds
            ) { meal ->

                openDetails(
                    meal.idMeal
                )
            }
    }

    private fun openDetails(
        id: String
    ) {

        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                DetailsFragment.newInstance(
                    id
                )
            )
            .addToBackStack(null)
            .commit()
    }

    // Update profile image every time
    // we return to Home

    override fun onResume() {

        super.onResume()

        if (!::imgProfile.isInitialized) {
            return
        }

        val sharedPref =
            requireActivity()
                .getSharedPreferences(
                    "user_prefs",
                    Context.MODE_PRIVATE
                )

        val savedImage =
            sharedPref.getString(
                "profile_image",
                null
            )

        if (!savedImage.isNullOrEmpty()) {

            val file =
                File(savedImage)

            if (file.exists()) {

                imgProfile.setImageURI(
                    Uri.fromFile(file)
                )

            } else {

                imgProfile.setImageResource(
                    R.drawable.profile
                )
            }

        } else {

            imgProfile.setImageResource(
                R.drawable.profile
            )
        }
    }
}