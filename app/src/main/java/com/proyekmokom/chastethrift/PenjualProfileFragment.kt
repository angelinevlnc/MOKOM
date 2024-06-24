package com.proyekmokom.chastethrift

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PenjualProfileFragment : Fragment() {
    lateinit var txtUsernamePenjual: TextView
    lateinit var txtRolePenjual: TextView
    lateinit var btnEditProfilePenjual: Button
    lateinit var btnLogoutPenjual: Button
    lateinit var imgProfilePenjual: ImageView

    private val args: PenjualProfileFragmentArgs by navArgs()
    private val viewModel: PenjualProfileViewModel by viewModels {
        PenjualProfileViewModelFactory(AppDatabase.build(requireContext()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_penjual_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var idUser:Int = args.idUser

        txtUsernamePenjual = view.findViewById(R.id.txtUsernamePenjual)
        txtRolePenjual = view.findViewById(R.id.txtRolePenjual)
        btnEditProfilePenjual = view.findViewById(R.id.btnEditProfilePenjual)
        btnLogoutPenjual = view.findViewById(R.id.btnLogoutPenjual)
        imgProfilePenjual = view.findViewById(R.id.imgProfilePenjual)

        viewModel.getUserById(args.idUser).observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                txtUsernamePenjual.text = user.username
                imgProfilePenjual.setImageResource(user.gambar)
            } else {
                Log.e("AdminProfileFragment", "User not found with id: ${args.idUser}")
                txtUsernamePenjual.text = "User not found"
                imgProfilePenjual.setImageResource(R.drawable.ic_profile)
            }
        })

        btnEditProfilePenjual.setOnClickListener {
            val action = PenjualProfileFragmentDirections
                .actionPenjualProfileFragmentToEditProfileFragment("nav_penjual", "penjualProfileFragment",idUser)
            findNavController().navigate(action)
        }

        btnLogoutPenjual.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}