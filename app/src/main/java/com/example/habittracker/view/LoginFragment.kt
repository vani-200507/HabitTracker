package com.example.habittracker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.data.HabitDatabase
import com.example.habittracker.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: HabitDatabase

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

        db = HabitDatabase.buildDatabase(requireContext())

        // BONUS: Auto Login
        val pref = requireActivity().getSharedPreferences(
            "habit_pref",
            Context.MODE_PRIVATE
        )

        val isLogin = pref.getBoolean("is_login", false)

        if (isLogin) {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            return
        }

        binding.btnLogin.setOnClickListener {

            val username = binding.editUsername.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Harap isi semua field",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            lifecycleScope.launch {

                val user = withContext(Dispatchers.IO) {
                    db.userDao().login(username, password)
                }

                if (user != null) {

                    pref.edit()
                        .putBoolean("is_login", true)
                        .putString("username", user.username)
                        .apply()

                    findNavController().navigate(
                        R.id.action_loginFragment_to_dashboardFragment
                    )

                } else {

                    Toast.makeText(
                        requireContext(),
                        "Username atau Password salah!",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}