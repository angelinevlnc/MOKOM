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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.Observer

class AdminProfileFragment : Fragment() {
    lateinit var txtUsernameAdmin: TextView
    lateinit var txtRoleAdmin: TextView
    lateinit var btnEditProfileAdmin: Button
    lateinit var btnLogoutAdmin: Button
    lateinit var imgProfileAdmin: ImageView

    private val viewModel: AdminProfileViewModel by viewModels {
        val appDatabase = AppDatabase.build(requireContext())
        val userRepository = UserRepository(appDatabase.userDao())
        AdminProfileViewModelFactory(userRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: AdminProfileFragmentArgs by navArgs()
        var idUser = args.idUser

        txtUsernameAdmin = view.findViewById(R.id.txtUsernameAdmin)
        txtRoleAdmin = view.findViewById(R.id.txtRoleAdmin)
        btnEditProfileAdmin = view.findViewById(R.id.btnEditProfileAdmin)
        btnLogoutAdmin = view.findViewById(R.id.btnLogoutAdmin)
        imgProfileAdmin = view.findViewById(R.id.imgProfileAdmin)

        viewModel.getUserById(idUser).observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                txtUsernameAdmin.text = user.username
                imgProfileAdmin.setImageResource(user.gambar)
            } else {
                Log.e("AdminProfileFragment", "User not found with id: ${idUser}")
                txtUsernameAdmin.text = "User not found"
                imgProfileAdmin.setImageResource(R.drawable.ic_profile)
            }
        })

        btnEditProfileAdmin.setOnClickListener {
            val action = AdminProfileFragmentDirections
                .actionAdminProfileFragmentToEditProfileFragment2("nav_admin", "adminProfileFragment", args.idUser)
            findNavController().navigate(action)
        }

        btnLogoutAdmin.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
