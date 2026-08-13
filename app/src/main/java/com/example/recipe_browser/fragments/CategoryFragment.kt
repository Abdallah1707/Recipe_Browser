package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.R
import com.example.recipe_browser.adapter.CategoryAdapter
import com.example.recipe_browser.model.RepositoryProvider
import com.example.recipe_browser.viewmodel.CategoryViewModel
import com.example.recipe_browser.viewmodel.ViewModelFactory

class CategoryFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    private lateinit var rvCategories: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_category, container, false)

        val repository = RepositoryProvider.provideRepository(requireContext())
        val factory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[CategoryViewModel::class.java]

        rvCategories = view.findViewById(R.id.rv_CategoriesFrag)

        rvCategories.layoutManager = GridLayoutManager(
            requireContext(),
            4
        )

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            rvCategories.adapter = CategoryAdapter(categories) { category ->

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

        viewModel.loadCategories()

        return view
    }
}