package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.R
import com.example.recipe_browser.adapter.FavoriteAdapter
import com.example.recipe_browser.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var rvFavorite: RecyclerView
    private lateinit var tvEmptyFavorite: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_favorite,
            container,
            false
        )

        rvFavorite = view.findViewById(R.id.rvFavorite)
        tvEmptyFavorite = view.findViewById(R.id.tvEmptyFavorite)

        rvFavorite.layoutManager =
            LinearLayoutManager(requireContext())

        favoriteViewModel =
            ViewModelProvider(this)[FavoriteViewModel::class.java]

        favoriteViewModel.favorites.observe(
            viewLifecycleOwner
        ) { favorites ->

            if (favorites.isEmpty()) {

                tvEmptyFavorite.visibility = View.VISIBLE
                rvFavorite.visibility = View.GONE

            } else {

                tvEmptyFavorite.visibility = View.GONE
                rvFavorite.visibility = View.VISIBLE

                rvFavorite.adapter =
                    FavoriteAdapter(favorites) { meal ->

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

        return view
    }
}