package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.R
import com.example.recipe_browser.adapter.RecentHomeAdapter
import com.example.recipe_browser.model.RepositoryProvider
import com.example.recipe_browser.viewmodel.RecentViewModel
import com.example.recipe_browser.viewmodel.ViewModelFactory

class RecentRecipeFragment : Fragment() {

    private lateinit var viewModel: RecentViewModel
    private lateinit var rvRecent: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_recent_recipe, container, false)

        val repository = RepositoryProvider.provideRepository(requireContext())
        val factory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[RecentViewModel::class.java]

        rvRecent = view.findViewById(R.id.rv_RecentFrag)

        rvRecent.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recentRecipes.observe(viewLifecycleOwner) { recipes ->
            rvRecent.adapter = RecentHomeAdapter(recipes) { recipe ->

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainer,
                        DetailsFragment.newInstance(recipe.idMeal)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }

        viewModel.loadRecentRecipes()

        return view
    }
}