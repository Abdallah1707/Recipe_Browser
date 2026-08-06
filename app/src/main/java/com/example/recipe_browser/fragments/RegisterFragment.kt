package com.example.recipe_browser.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipe_browser.R
import com.example.recipe_browser.activities.MainActivity
import com.example.recipe_browser.viewmodel.RegisterViewModel

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = view.findViewById<EditText>(R.id.etConfirmPassword)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etAge = view.findViewById<EditText>(R.id.etAge)
        val etCountry = view.findViewById<EditText>(R.id.etCountry)
        
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val txtLogin = view.findViewById<TextView>(R.id.txtLogin)

        btnRegister.setOnClickListener {
            viewModel.register(
                etName.text.toString().trim(),
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim(),
                etConfirmPassword.text.toString().trim(),
                etPhone.text.toString().trim(),
                etAge.text.toString().trim(),
                etCountry.text.toString().trim()
            )
        }

        txtLogin.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel.registerStatus.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RegisterViewModel.RegisterResult.Success -> {
                    Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                is RegisterViewModel.RegisterResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}