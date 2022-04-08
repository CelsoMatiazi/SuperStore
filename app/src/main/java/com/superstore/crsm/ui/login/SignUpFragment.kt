package com.superstore.crsm.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.superstore.crsm.R

class SignUpFragment : Fragment(R.layout.fragment_cadastro) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backBtn = view.findViewById<ImageView>(R.id.cadastro_back_btn)

        backBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }


}