package com.proyekmokom.chastethrift.MidtransPayment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.midtrans.sdk.uikit.api.model.CreditCard
import com.midtrans.sdk.uikit.api.model.CustomColorTheme
import com.midtrans.sdk.uikit.api.model.CustomerDetails
import com.midtrans.sdk.uikit.api.model.Expiry
import com.midtrans.sdk.uikit.api.model.GopayPaymentCallback
import com.midtrans.sdk.uikit.api.model.ItemDetails
import com.midtrans.sdk.uikit.api.model.PaymentCallback
import com.midtrans.sdk.uikit.api.model.SnapTransactionDetail
import com.midtrans.sdk.uikit.api.model.TransactionResult
import com.midtrans.sdk.uikit.external.UiKitApi
import com.midtrans.sdk.uikit.internal.util.UiKitConstants
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_CANCELED
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_FAILED
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_INVALID
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_PENDING
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_SUCCESS
import com.proyekmokom.chastethrift.DetailActivity
import com.proyekmokom.chastethrift.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID


class MidtransGateway : AppCompatActivity() {

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result?.resultCode == RESULT_OK) {
            result.data?.let {
                val transactionResult = it.getParcelableExtra<TransactionResult>(UiKitConstants.KEY_TRANSACTION_RESULT)
                Toast.makeText(this,"${transactionResult?.transactionId}", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val transactionResult = data?.getParcelableExtra<TransactionResult>(
                UiKitConstants.KEY_TRANSACTION_RESULT
            )
            if (transactionResult != null) {
                when (transactionResult.status) {
                    STATUS_SUCCESS -> {
                        Toast.makeText(this, "Transaction Finished. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                        finish()
                    }
                    STATUS_PENDING -> {
                        Toast.makeText(this, "Transaction Pending. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                        finish()
                    }
                    STATUS_FAILED -> {
                        Toast.makeText(this, "Transaction Failed. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                        finish()
                    }
                    STATUS_CANCELED -> {
                        Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    STATUS_INVALID -> {
                        Toast.makeText(this, "Transaction Invalid. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else -> {
                        Toast.makeText(this, "Transaction ID: " + transactionResult.transactionId + ". Message: " + transactionResult.status, Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_midtrans_gateway)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z")
        val currentDateandTime: String = sdf.format(Date())

        val intent = intent
        val namaBarang = intent.getStringExtra("namaBarang")
        val hargaBarang = intent.getIntExtra("hargaBarang", 0)

        UiKitApi.Builder()
            .withMerchantClientKey("SB-Mid-client-UWfYgcHJNqxPJuBK") // client_key is mandatory
            .withContext(this) // context is mandatory
            .withMerchantUrl("https://midtrans-api.000webhostapp.com/index.php/") // set transaction finish callback (sdk callback)
            .enableLog(true) // enable sdk log (optional)
            .withColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .build()
        setLocaleNew("en") //`en` for English and `id` for Bahasa


        //TransactionRequest on revamp is included in the startPayment Constructor



        val itemDetails = listOf(ItemDetails("Test01", hargaBarang!!.toDouble(), 1, namaBarang))

        UiKitApi.getDefaultInstance().startPaymentUiFlow(
            this, // activity
            launcher, //ActivityResultLauncher
            SnapTransactionDetail(UUID.randomUUID().toString(), hargaBarang.toDouble(), "IDR"), // Transaction Details
            CustomerDetails("hia-6789",
                "Stevanus",
                "Hia",
                "stevanushiaa@utomo.com",
                "0213213123",
                null,
                null), // Customer Details
            itemDetails, // Item Details
            CreditCard(true, null,  null, null, null, null, null, null, null, null), // Credit Card
            "customerIdentifier", // User Id
            PaymentCallback(""), // UobEzpayCallback
            GopayPaymentCallback("mysamplesdk://midtrans"), // GopayCallback
            PaymentCallback("mysamplesdk://midtrans"), // ShopeepayCallback
            Expiry( currentDateandTime, Expiry.UNIT_HOUR, 24), // expiry (null: default expiry time)
        )


    }

    fun setLocaleNew(languageCode: String?) {
        val locales = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(locales)
    }
}