package com.proyekmokom.chastethrift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.proyekmokom.chastethrift.AppDatabase
import com.proyekmokom.chastethrift.MainActivity
import com.proyekmokom.chastethrift.MainActivityAdmin
import com.proyekmokom.chastethrift.MainActivityPenjual
import com.proyekmokom.chastethrift.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var password: TextView
    private lateinit var confirmPassword: TextView
    private lateinit var btnRegister: Button
    private lateinit var rbPembeli: RadioButton
    private lateinit var rbPenjual: RadioButton
    private lateinit var txtToLogin:TextView

    private val viewModel: RegisterViewModel by viewModels {
        val appDatabase = AppDatabase.build(this)
        val userRepository = UserRepository(appDatabase.userDao())
        RegisterViewModelFactory(userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = findViewById(R.id.editRegisterUsername)
        password = findViewById(R.id.editRegisterPassword)
        confirmPassword = findViewById(R.id.editRegisterConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        rbPembeli = findViewById(R.id.rbPembeli)
        rbPenjual = findViewById(R.id.rbPenjual)
        txtToLogin = findViewById(R.id.txtToLogin)

        btnRegister.setOnClickListener {
            val usernameStr = username.text.toString()
            val passwordStr = password.text.toString()
            val confirmPasswordStr = confirmPassword.text.toString()

            if (usernameStr.isNotEmpty() && passwordStr.isNotEmpty() && confirmPasswordStr.isNotEmpty()) {
                if (passwordStr == confirmPasswordStr) {
                    val role = if (rbPembeli.isChecked) 2 else 1

                    viewModel.registerUser(usernameStr, passwordStr, role,
                        onSuccess = { role, idUser ->
                            navigateToMainActivity(role, idUser)
                        },
                        onError = { errorMessage ->
                            Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    Toast.makeText(this, "Password dan Confirm Password harus sama!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Semua inputan harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        txtToLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun navigateToMainActivity(role: Int, idUser: Int) {
        when (role) {
            1 -> navigateTo(MainActivityPenjual::class.java, idUser)
            2 -> navigateTo(MainActivity::class.java, idUser)
            3 -> navigateTo(MainActivityAdmin::class.java, idUser)
            else -> Toast.makeText(this, "Error, user tidak ditemukan!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateTo(destination: Class<*>, idUser: Int) {
        val intent = Intent(this, destination)
        intent.putExtra("idUser", idUser)
        startActivity(intent)
        finish()
    }
}
