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
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.net.http.HttpException
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.proyekmokom.chastethrift.Network.ApiConfig
import com.proyekmokom.chastethrift.Network.UploadResponse
import com.proyekmokom.chastethrift.Utils.bitmapToFile
import com.proyekmokom.chastethrift.Utils.reduceFileImage
import com.proyekmokom.chastethrift.Utils.uriToFile
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

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
    lateinit var sertifikatImageView: ImageView
    var produkImageUri: Uri? = null
    var sertifikatImageUri: Uri? = null

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
        sertifikatImageView = view.findViewById(R.id.sertifikatImageView)

        buttonSell.setOnClickListener(){
            addData(idUser)
        }

        // Camera Related
        // Request camera permissions
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        imageView.setOnClickListener {
            val intent = Intent(requireActivity(), CameraActivity::class.java)
            launcherProdukIntentCameraX.launch(intent)
        }

        sertifikatImageView.setOnClickListener {
            val intent = Intent(requireActivity(), CameraActivity::class.java)
            launcherSertifikatIntentCameraX.launch(intent)
        }
    }

    private val launcherProdukIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == 200) {
            produkImageUri = it.data?.getStringExtra("CAMERAX_PHOTO")?.toUri()
            produkImageUri?.let {
                imageView.setImageURI(it)
            }
        }
    }

    private val launcherSertifikatIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == 200) {
            sertifikatImageUri = it.data?.getStringExtra("CAMERAX_PHOTO")?.toUri()
            sertifikatImageUri?.let {
                sertifikatImageView.setImageURI(it)
            }
        }
    }

    private fun addData(idUser: Int) {
        db = AppDatabase.build(requireContext())

        val title = textTitle.text.toString()
        val price = textPrice.text.toString().toInt()
        val description = textDescription.text.toString()
        val brand = textBrand.text.toString()
        val size = textSize.text.toString()

        // Upload Gambar Produk
//        produkImageUri?.let { uri ->
//            val produkFile = uriToFile(uri, requireContext()).reduceFileImage()
//            Log.e("IMAGE_FILE", "show Image: ${produkFile.path}")
//            var name = title
//            var type = "produk"
//
//            var requestBodyTitle = title.toRequestBody("text/plain".toMediaType())
//            var requestBodyType = type.toRequestBody("text/plain".toMediaType())
//            var requestImageFile = produkFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "photo",
//                produkFile.name,
//                requestImageFile
//            )
//
//            try {
//                val client = ApiConfig.getApiService().uploadImageBasic(
//                    tipe = requestBodyType,
//                    title = requestBodyTitle,
//                    files = multipartBody
//                )
//                client.enqueue(object: Callback<UploadResponse> {
//                    override fun onResponse(
//                        call: Call<UploadResponse>,
//                        response: Response<UploadResponse>
//                    ) {
//                        if (response.isSuccessful){
//                            Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
//
//                    }
//                })
//
//            } catch (ex: Exception) {
//                Toast.makeText(requireContext(), ex.localizedMessage, Toast.LENGTH_SHORT).show()
//                Log.e("ERROR", ex.localizedMessage)
//            }
//        }


        coroutine.launch(Dispatchers.IO) {
            try {
                val item = ItemEntity(
                    null,
                    idUser,
                    "https://awsimages.detik.net.id/community/media/visual/2022/11/07/kasus-kucing-mati-dilempar-batu-di-jakarta-kronologi-hingga-penyebab-1.jpeg?w=1200",
                    title,
                    price,
                    description,
                    brand,
                    size,
                    null,
                    1
                )
                db.itemDao().insert(item)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Berhasil Tambah", Toast.LENGTH_SHORT).show()

                    //Pindah ke fragment lain
                    val action = PenjualAddFragmentDirections
                        .actionGlobalPenjualCatalogFragment(idUser)
                    findNavController().navigate(action)
                }
            } catch (e: Exception) {

            }
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}