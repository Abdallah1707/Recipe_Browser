package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipe_browser.R
import com.example.recipe_browser.model.RepositoryProvider
import com.example.recipe_browser.viewmodel.DetailsViewModel
import com.example.recipe_browser.viewmodel.ViewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel

    private lateinit var imgMeal: ImageView
    private lateinit var txtName: TextView
    private lateinit var txtInstructions: TextView
    private lateinit var youtubePlayerView: YouTubePlayerView

    private lateinit var btnFavorite: ImageButton
    private lateinit var btnBack: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_recipe_detail,
            container,
            false
        )

        imgMeal =
            view.findViewById(R.id.imgRecipe)

        txtName =
            view.findViewById(R.id.txtRecipeName)

        txtInstructions =
            view.findViewById(R.id.txtInstructions)

        youtubePlayerView =
            view.findViewById(R.id.youtubePlayerView)

        btnFavorite =
            view.findViewById(R.id.btnFavorite)

        btnBack =
            view.findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        lifecycle.addObserver(
            youtubePlayerView
        )

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
            )[DetailsViewModel::class.java]

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

        val mealId =
            arguments?.getString("id") ?: ""

        observeMeal()
        observeFavorite()

        if (mealId.isNotEmpty()) {
            viewModel.loadMeal(mealId)
        }

        btnFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun observeMeal() {

        viewModel.meal.observe(
            viewLifecycleOwner
        ) { meal ->

            if (meal == null) return@observe
            viewModel.addToRecent(meal)

            txtName.text =
                meal.strMeal

            txtInstructions.text =
                meal.strInstructions
                    ?: "No instructions available"

            Glide.with(requireContext())
                .load(meal.strMealThumb)
                .placeholder(
                    R.drawable.banner_food
                )
                .into(imgMeal)

            setupYoutube(
                meal.strYoutube
            )
        }
    }

    private fun observeFavorite() {

        viewModel.isFavorite.observe(
            viewLifecycleOwner
        ) { favorite ->

            if (favorite) {

                btnFavorite.setImageResource(
                    R.drawable.ic_favorite
                )

            } else {

                btnFavorite.setImageResource(
                    R.drawable.ic_favorite_border
                )
            }
        }
    }

    private fun setupYoutube(url: String?) {

        if (url.isNullOrEmpty()) return

        val videoId =
            getYoutubeVideoId(url)

        if (videoId.isEmpty()) return

        youtubePlayerView
            .addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {

                    override fun onReady(
                        youTubePlayer: YouTubePlayer
                    ) {

                        youTubePlayer.cueVideo(
                            videoId,
                            0f
                        )
                    }
                }
            )
    }

    private fun getYoutubeVideoId(
        url: String
    ): String {

        return try {

            if (url.contains("v=")) {

                url.substringAfter("v=")
                    .substringBefore("&")

            } else if (
                url.contains("youtu.be/")
            ) {

                url.substringAfter("youtu.be/")
                    .substringBefore("?")

            } else {

                ""

            }

        } catch (e: Exception) {

            ""

        }
    }

    override fun onDestroyView() {

        youtubePlayerView.release()

        super.onDestroyView()
    }

    companion object {

        fun newInstance(
            id: String
        ): DetailsFragment {

            val fragment =
                DetailsFragment()

            val bundle =
                Bundle()

            bundle.putString(
                "id",
                id
            )

            fragment.arguments =
                bundle

            return fragment
        }
    }
}