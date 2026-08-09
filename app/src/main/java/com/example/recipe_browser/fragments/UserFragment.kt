package com.example.recipe_browser.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.recipe_browser.R
import com.example.recipe_browser.activities.AuthActivity
import com.example.recipe_browser.room.MealDatabase
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class UserFragment : Fragment() {

    private lateinit var imgProfile: ImageView

    private val imagePicker =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {

                val savedFile =
                    saveImageToInternalStorage(uri)

                if (savedFile != null) {

                    imgProfile.setImageURI(
                        Uri.fromFile(savedFile)
                    )

                    val sharedPref =
                        requireActivity()
                            .getSharedPreferences(
                                "user_prefs",
                                Context.MODE_PRIVATE
                            )

                    sharedPref.edit()
                        .putString(
                            "profile_image",
                            savedFile.absolutePath
                        )
                        .apply()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_user,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        imgProfile =
            view.findViewById(
                R.id.imgProfile
            )

        val tvName =
            view.findViewById<TextView>(
                R.id.tvName
            )

        val tvEmail =
            view.findViewById<TextView>(
                R.id.tvEmail
            )

        val tvPhone =
            view.findViewById<TextView>(
                R.id.tvPhone
            )

        val tvAge =
            view.findViewById<TextView>(
                R.id.tvAge
            )

        val tvCountry =
            view.findViewById<TextView>(
                R.id.tvCountry
            )

        val btnLogout =
            view.findViewById<Button>(
                R.id.btnLogout
            )

        val sharedPref =
            requireActivity()
                .getSharedPreferences(
                    "user_prefs",
                    Context.MODE_PRIVATE
                )

        // Load saved profile image

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

        // Open Gallery

        imgProfile.setOnClickListener {

            imagePicker.launch("image/*")
        }

        // Get logged-in user

        val userEmail =
            sharedPref.getString(
                "userEmail",
                null
            )

        if (userEmail != null) {

            val db =
                MealDatabase.getDatabase(
                    requireContext()
                )

            lifecycleScope.launch {

                try {

                    val user =
                        db.userDao()
                            .getUserByEmail(userEmail)

                    if (user != null) {

                        tvName.text =
                            user.name

                        tvEmail.text =
                            user.email

                        tvPhone.text =
                            user.phone

                        tvAge.text =
                            user.age

                        tvCountry.text =
                            user.country

                    } else {

                        performLogout(
                            sharedPref
                        )
                    }

                } catch (e: Exception) {

                    e.printStackTrace()

                    performLogout(
                        sharedPref
                    )
                }
            }
        }

        // Logout

        btnLogout.setOnClickListener {

            performLogout(
                sharedPref
            )
        }
    }

    private fun saveImageToInternalStorage(
        uri: Uri
    ): File? {

        return try {

            val inputStream =
                requireContext()
                    .contentResolver
                    .openInputStream(uri)
                    ?: return null

            val file =
                File(
                    requireContext().filesDir,
                    "profile_image.jpg"
                )

            val outputStream =
                FileOutputStream(file)

            inputStream.use { input ->

                outputStream.use { output ->

                    input.copyTo(output)
                }
            }

            file

        } catch (e: Exception) {

            e.printStackTrace()

            null
        }
    }

    private fun performLogout(
        sharedPref: android.content.SharedPreferences
    ) {

        sharedPref.edit()
            .putBoolean(
                "isLoggedIn",
                false
            )
            .apply()

        val intent =
            Intent(
                requireContext(),
                AuthActivity::class.java
            )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

        requireActivity().finish()
    }
}