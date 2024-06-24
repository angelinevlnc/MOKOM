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
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileFragment : Fragment() {
    lateinit var txtUsernamePembeli: TextView
    lateinit var txtRolePembeli: TextView
    lateinit var btnEditProfilePembeli: Button
    lateinit var btnLogoutPembeli: Button
    lateinit var imgProfilePembeli: ImageView

    private val viewModel: PembeliProfileViewModel by viewModels {
        val appDatabase = AppDatabase.build(requireContext())
        val userRepository = UserRepository(appDatabase.userDao())
        PembeliProfileViewModelFactory(userRepository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: ProfileFragmentArgs by navArgs()
        var idUser:Int = args.idUser

        txtUsernamePembeli = view.findViewById(R.id.txtUsernamePembeli)
        txtRolePembeli = view.findViewById(R.id.txtRolePembeli)
        btnEditProfilePembeli = view.findViewById(R.id.btnEditProfilePembeli)
        btnLogoutPembeli = view.findViewById(R.id.btnLogoutPembeli)
        imgProfilePembeli = view.findViewById(R.id.imgProfilePembeli)

        viewModel.getUserById(args.idUser).observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                txtUsernamePembeli.text = user.username
                imgProfilePembeli.setImageResource(user.gambar)
            } else {
                Log.e("AdminProfileFragment", "User not found with id: ${args.idUser}")
                txtUsernamePembeli.text = "User not found"
                imgProfilePembeli.setImageResource(R.drawable.ic_profile)
            }
        })

        btnEditProfilePembeli.setOnClickListener {
            val action = ProfileFragmentDirections
                .actionProfileFragmentToEditProfileFragment3("nav", "profileFragment",idUser)
            findNavController().navigate(action)
        }

        btnLogoutPembeli.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}