package com.superstore.crsm.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.superstore.crsm.R

class LoginFragment : Fragment(R.layout.fragment_login) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpBtn : TextView = view.findViewById(R.id.login_bottom_txt2)

        signUpBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.login_fragment_container, SignUpFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }


}