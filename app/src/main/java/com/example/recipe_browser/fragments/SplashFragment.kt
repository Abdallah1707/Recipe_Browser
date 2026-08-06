package com.example.recipe_browser.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.recipe_browser.R

class SplashFragment : Fragment() {

    interface SplashListener {
        fun onSplashFinished(isLoggedIn: Boolean)
    }

    private var listener: SplashListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SplashListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logo = view.findViewById<ImageView>(R.id.imgLogo)
        val dot1 = view.findViewById<View>(R.id.dot1)
        val dot2 = view.findViewById<View>(R.id.dot2)
        val dot3 = view.findViewById<View>(R.id.dot3)

        logo?.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.logo_anim))

        val dotsAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.dots_anim)
        dot1?.startAnimation(dotsAnim)

        Handler(Looper.getMainLooper()).postDelayed({
            dot2?.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.dots_anim))
        }, 200)

        Handler(Looper.getMainLooper()).postDelayed({
            dot3?.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.dots_anim))
        }, 400)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
            listener?.onSplashFinished(isLoggedIn)
        }, 3000)
    }
}