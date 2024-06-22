package com.proyekmokom.chastethrift

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfileFragment : Fragment() {
    lateinit var editProfileUsername: TextView
    lateinit var editProfilePassword: TextView
    lateinit var editProfileConfirmPassword: TextView
    lateinit var editPasswordLama: TextView
    lateinit var btnProfileSaveEdit: Button
    lateinit var btnProfileCancelEdit: Button
    lateinit var btnProfileDelete: Button

    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: EditProfileFragmentArgs by navArgs()
        var idUser:Int = args.idUser
        var sourceNavGraph = args.sourceNavGraph
        var sourceFragment = args.sourceFragment

        editProfileUsername = view.findViewById(R.id.editProfileUsername)
        editProfilePassword = view.findViewById(R.id.editProfilePassword)
        editProfileConfirmPassword = view.findViewById(R.id.editProfileConfirmPassword)
        editPasswordLama = view.findViewById(R.id.editPasswordLama)
        btnProfileSaveEdit = view.findViewById(R.id.btnProfileSaveEdit)
        btnProfileCancelEdit = view.findViewById(R.id.btnProfileCancelEdit)
        btnProfileDelete = view.findViewById(R.id.btnProfileDelete)

        db = AppDatabase.build(requireContext())

        coroutine.launch(Dispatchers.IO) {
            var nowUser = db.userDao().searchById(idUser)
            withContext(Dispatchers.Main) {
                editProfileUsername.text = nowUser.username
            }
        }

        btnProfileSaveEdit.setOnClickListener {
            if(editProfileUsername.text.toString().isNotEmpty() && editProfilePassword.text.toString().isNotEmpty() && editProfileConfirmPassword.text.toString().isNotEmpty()){
                if(editProfilePassword.text.toString() == editProfileConfirmPassword.text.toString()){
                    val usernameBaru = editProfileUsername.text.toString()
                    val passwordBaru = editProfilePassword.text.toString()
                    val passwordLama:String = editPasswordLama.text.toString()
                    coroutine.launch(Dispatchers.IO) {
                        var currentUser = db.userDao().searchById(idUser)
                        if(passwordLama == currentUser.password){
                            var user = db.userDao().get(usernameBaru)
                            if (user.isEmpty() || usernameBaru == currentUser.username) {
                                //UPDATE
                                db.userDao().updateProfile(usernameBaru,passwordBaru,idUser)

                                //NAVIGATE BACK TO PROFILE OF PENJUAL/PEMBELI/ADMIN
                                withContext(Dispatchers.Main) {
//                                    navigateBack(sourceNavGraph, sourceFragment, idUser)
                                    findNavController().popBackStack()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(requireContext(), "Username sudah ada!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        else{
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Password Lama salah!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(requireContext(), "Password dan Confirm Password harus sama!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), "Semua inputan harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        btnProfileCancelEdit.setOnClickListener {
//            navigateBack(sourceNavGraph, sourceFragment, idUser)
            findNavController().popBackStack()
        }

        btnProfileDelete.setOnClickListener {
            coroutine.launch(Dispatchers.IO) {
                db.userDao().updateStatus(0, idUser)
            }
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
//    private fun navigateBack(sourceNavGraph: String, sourceFragment: String, idUser: Int) {
//        when (sourceNavGraph) {
//            "nav_penjual" -> {
//                when (sourceFragment) {
//                    "penjualProfileFragment" -> {
//                        val action = EditProfileFragmentDirections.actionEditProfileFragmentToPenjualProfileFragment(idUser)
//                        findNavController().navigate(action)
//                    }
//                }
//            }
//            "nav" -> {
//                when (sourceFragment) {
//                    "profileFragment" -> {
//                        val action = EditProfileFragmentDirections.actionEditProfileFragment3ToProfileFragment(idUser)
//                        findNavController().navigate(action)
//                    }
//                }
//            }
//            "nav_admin" -> {
//                when (sourceFragment) {
//                    "adminProfileFragment" -> {
//                        val action = EditProfileFragmentDirections.actionEditProfileFragment2ToAdminProfileFragment(idUser)
//                        findNavController().navigate(action)
//                    }
//                }
//            }
//            else -> findNavController().popBackStack()
//        }
//    }
}