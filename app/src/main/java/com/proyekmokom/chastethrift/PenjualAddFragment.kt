package com.proyekmokom.chastethrift

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
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

class PenjualAddFragment : Fragment() {

    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)

    lateinit var buttonSell: Button
    lateinit var textTitle : TextView
    lateinit var textPrice : TextView
    lateinit var textDescription : TextView
    lateinit var textBrand : TextView
    lateinit var textSize : TextView

    lateinit var imageView: ImageView
    var currentImageUri: Uri? = null

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_penjual_add, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PenjualAddFragmentArgs by navArgs()
        var idUser:Int = args.idUser

        buttonSell = view.findViewById(R.id.buttonSell)
        textTitle = view.findViewById(R.id.textTitle)
        textPrice = view.findViewById(R.id.textPrice)
        textDescription = view.findViewById(R.id.textDescription)
        textBrand = view.findViewById(R.id.textBrand)
        textSize = view.findViewById(R.id.textSize)

        imageView = view.findViewById(R.id.imageView)

        buttonSell.setOnClickListener(){
            val title = textTitle.text.toString()
            val price = textPrice.text.toString().toInt()
            val description = textDescription.text.toString()
            val brand = textBrand.text.toString()
            val size = textSize.text.toString()
            //val idUser = LoginViewModel.login_user_id //ini hasil idUser -1 terus, mungkin krn login-nya auto dari hlm Register?

            db = AppDatabase.build(requireContext())

            coroutine.launch(Dispatchers.IO) {
                val item = ItemEntity(null, idUser, "https://awsimages.detik.net.id/community/media/visual/2022/11/07/kasus-kucing-mati-dilempar-batu-di-jakarta-kronologi-hingga-penyebab-1.jpeg?w=1200", title, price, description, brand, size, null,1)
                db.itemDao().insert(item)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Berhasil Tambah", Toast.LENGTH_SHORT).show()

                    //Pindah ke fragment lain
                    val action = PenjualAddFragmentDirections
                        .actionGlobalPenjualCatalogFragment(idUser)
                    findNavController().navigate(action)
                }
            }
        }

        // Camera Related
        // Request camera permissions
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        imageView.setOnClickListener {
            val intent = Intent(requireActivity(), CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == 200) {
            currentImageUri = it.data?.getStringExtra("CAMERAX_PHOTO")?.toUri()
            showImage()
        }
    }

    private fun showImage(){
        currentImageUri?.let {
            imageView.setImageURI(it)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}