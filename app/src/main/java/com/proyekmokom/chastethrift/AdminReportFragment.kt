package com.proyekmokom.chastethrift

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs

class AdminReportFragment : Fragment() {
    lateinit var textView15: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: AdminReportFragmentArgs by navArgs()
        var idUser:Int = args.idUser

        textView15 = view.findViewById(R.id.textView15)
        textView15.text = "Report Admin -> Nanti pakai API Google Chart. Yg login admin dgn id_user: $idUser"
    }
}