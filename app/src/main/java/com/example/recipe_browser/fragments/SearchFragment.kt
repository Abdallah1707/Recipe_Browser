package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.recipe_browser.R
import com.example.recipe_browser.adapter.MealAdapter
import com.example.recipe_browser.adapter.SearchHistoryAdapter
import com.example.recipe_browser.model.RepositoryProvider
import com.example.recipe_browser.viewmodel.SearchViewModel
import com.example.recipe_browser.viewmodel.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var etSearch: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_search,
            container,
            false
        )

        recyclerView =
            view.findViewById(R.id.rvSearch)

        historyRecyclerView =
            view.findViewById(R.id.rvSearchHistory)

        etSearch =
            view.findViewById(R.id.etSearch)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        historyRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        val repository =
            RepositoryProvider.provideRepository(
                requireContext()
            )

        val factory =
            ViewModelFactory(repository)

        viewModel =
            ViewModelProvider(
                this,
                factory
            )[SearchViewModel::class.java]

        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )


        view.findViewById<ImageView>(R.id.btnBack)
            ?.setOnClickListener {

                parentFragmentManager.popBackStack()
            }


        observeSearchResults()
        observeSearchHistory()
        viewModel.loadSearchHistory()

        val btnClearSearches =
            view.findViewById<TextView>(R.id.btnClearSearches)

        btnClearSearches.setOnClickListener {
            viewModel.clearSearchHistory()
        }

        etSearch.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                val query =
                    etSearch.text
                        .toString()
                        .trim()

                if (query.isNotEmpty()) {

                    viewModel.searchMeal(query)
                }

                true

            } else {

                false
            }
        }


        val category =
            arguments?.getString("category")

        if (!category.isNullOrEmpty()) {

            etSearch.setText(category)

            viewModel.searchMeal(category)
        }
    }

    private fun observeSearchHistory() {

        viewModel.searchHistory.observe(
            viewLifecycleOwner
        ) { history ->

            historyRecyclerView.adapter =
                SearchHistoryAdapter(
                    history
                ) { keyword ->

                    etSearch.setText(keyword)

                    etSearch.setSelection(
                        keyword.length
                    )

                    viewModel.searchMeal(keyword)
                }

            val tvRecentSearches =
                view?.findViewById<TextView>(
                    R.id.tvRecentSearches
                )

            val btnClearSearches =
                view?.findViewById<TextView>(
                    R.id.btnClearSearches
                )

            if (history.isEmpty()) {

                tvRecentSearches?.visibility = View.GONE
                btnClearSearches?.visibility = View.GONE
                historyRecyclerView.visibility = View.GONE

            } else {

                tvRecentSearches?.visibility = View.VISIBLE
                btnClearSearches?.visibility = View.VISIBLE
                historyRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun observeSearchResults() {

        viewModel.searchResults.observe(
            viewLifecycleOwner
        ) { meals ->

            recyclerView.adapter =
                MealAdapter(
                    meals
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
}