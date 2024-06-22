package com.proyekmokom.chastethrift

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_penjual_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PenjualProfileFragmentArgs by navArgs()
        var idUser:Int = args.idUser

        txtUsernamePenjual = view.findViewById(R.id.txtUsernamePenjual)
        txtRolePenjual = view.findViewById(R.id.txtRolePenjual)
        btnEditProfilePenjual = view.findViewById(R.id.btnEditProfilePenjual)
        btnLogoutPenjual = view.findViewById(R.id.btnLogoutPenjual)
        imgProfilePenjual = view.findViewById(R.id.imgProfilePenjual)

        db = AppDatabase.build(requireContext())

        coroutine.launch(Dispatchers.IO) {
            var user = db.userDao().searchById(idUser)
            withContext(Dispatchers.Main) {
                txtUsernamePenjual.text = user.username
                imgProfilePenjual.setImageResource(user.gambar)
            }
        }

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