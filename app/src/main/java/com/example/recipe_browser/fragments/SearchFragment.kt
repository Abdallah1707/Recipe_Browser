package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.R
import com.example.recipe_browser.adapter.RecentRecipeAdapter
import com.example.recipe_browser.viewmodel.SearchViewModel
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    private lateinit var etSearch: TextInputEditText
    private lateinit var rvSearch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvNoResult: TextView

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

        etSearch = view.findViewById(R.id.etSearch)
        rvSearch = view.findViewById(R.id.rvSearch)
        progressBar = view.findViewById(R.id.progressBar)
        tvNoResult = view.findViewById(R.id.tvNoResult)

        rvSearch.layoutManager =
            LinearLayoutManager(requireContext())

        viewModel =
            ViewModelProvider(this)[SearchViewModel::class.java]

        // استقبال نتائج البحث
        viewModel.meals.observe(viewLifecycleOwner) { meals ->

            progressBar.visibility = View.GONE

            if (meals.isEmpty()) {

                tvNoResult.visibility = View.VISIBLE
                rvSearch.visibility = View.GONE

            } else {

                tvNoResult.visibility = View.GONE
                rvSearch.visibility = View.VISIBLE

                rvSearch.adapter =
                    RecentRecipeAdapter(meals) { meal ->

                        parentFragmentManager.beginTransaction()
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

        // لو جاي من Category
        val category =
            arguments?.getString("category")

        if (!category.isNullOrEmpty()) {

            etSearch.setText(category)

            progressBar.visibility = View.VISIBLE
            tvNoResult.visibility = View.GONE

            viewModel.searchByCategory(category)
        }

        // البحث العادي من Search Bar
        etSearch.setOnEditorActionListener { _, actionId, event ->

            if (
                actionId == EditorInfo.IME_ACTION_SEARCH ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER
            ) {

                val text =
                    etSearch.text.toString().trim()

                if (text.isNotEmpty()) {

                    progressBar.visibility = View.VISIBLE
                    tvNoResult.visibility = View.GONE

                    viewModel.search(text)
                }

                true

            } else {

                false
            }
        }

        return view
    }
}