package com.example.habittracker.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = view.findViewById<EditText>(R.id.editUsername)
        val password = view.findViewById<EditText>(R.id.editPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val user = username.text.toString()
            val pass = password.text.toString()

            // validasi kosong
            if(user.isEmpty() || pass.isEmpty()){
                Toast.makeText(requireContext(), "Harap isi semua field", Toast.LENGTH_SHORT).show()
            }
            else if(user == "student" && pass == "123"){

                // simpan username
                val pref = requireActivity().getSharedPreferences("habit_pref", Context.MODE_PRIVATE)
                pref.edit().putString("username", user).apply()

                // pindah ke dashboard
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            }
            else{
                Toast.makeText(requireContext(), "Username atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}