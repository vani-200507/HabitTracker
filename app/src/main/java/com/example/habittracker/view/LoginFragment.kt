package com.example.habittracker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {

            val username = binding.editUsername.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Harap isi semua field",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (username == "student" && password == "123") {

                val pref = requireActivity()
                    .getSharedPreferences("habit_pref", Context.MODE_PRIVATE)

                pref.edit()
                    .putString("username", username)
                    .apply()

                findNavController()
                    .navigate(R.id.action_loginFragment_to_dashboardFragment)

            } else {

                Toast.makeText(
                    requireContext(),
                    "Username atau Password salah!",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}