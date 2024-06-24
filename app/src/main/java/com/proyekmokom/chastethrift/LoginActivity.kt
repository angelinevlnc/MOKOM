package com.proyekmokom.chastethrift

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    lateinit var username:TextView
    lateinit var password:TextView
    lateinit var btnLogin:Button
    lateinit var txtToRegister:TextView

    private val viewModel: LoginViewModel by viewModels {
        val appDatabase = AppDatabase.build(this)
        val userRepository = UserRepository(appDatabase.userDao())
        LoginViewModelFactory(userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.editLoginUsername)
        password = findViewById(R.id.editLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtToRegister = findViewById(R.id.txtToRegister)

        //openOrCreateDatabase("chastethrift", MODE_PRIVATE, null) //utk paksa buka DB yg closed. Kalo bisa buka tanpa paksa, lebih baik di-comment saja.

        btnLogin.setOnClickListener {
            val usernameStr = username.text.toString()
            val passwordStr = password.text.toString()

//            if (usernameStr == "admin" && passwordStr == "admin") {
//                navigateTo(MainActivityAdmin::class.java, 99)
//            }
            if (usernameStr.isNotEmpty() && passwordStr.isNotEmpty()) {
                viewModel.loginUser(usernameStr, passwordStr,
                    onSuccess = { role, idUser ->
                        navigateToMainActivity(role, idUser)
                    },
                    onError = {
                        Toast.makeText(this@LoginActivity, "Username atau password salah!", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Semua inputan harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        txtToRegister.setOnClickListener{
            val intent2 = Intent(this, RegisterActivity::class.java)
            startActivity(intent2)
            finish()
        }
    }
    private fun navigateToMainActivity(cek: Int, idUser: Int) {
        when (cek) {
            0 -> Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
            1 -> navigateTo(MainActivityPenjual::class.java, idUser)
            2 -> navigateTo(MainActivity::class.java, idUser)
            3 -> navigateTo(MainActivityAdmin::class.java, idUser)
        }
    }
    private fun navigateTo(destination: Class<*>, idUser: Int) {
        val intent = Intent(this, destination)
        intent.putExtra("idUser", idUser)
        startActivity(intent)
        finish()
    }
}