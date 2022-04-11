package com.superstore.crsm.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.superstore.crsm.R

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()



        val signUpBtn : TextView = view.findViewById(R.id.login_bottom_txt2)
        val loginBtn : MaterialButton = view.findViewById(R.id.login_entrar_btn)
        val email : TextInputEditText = view.findViewById(R.id.login_email_txt)
        val password : TextInputEditText = view.findViewById(R.id.login_password_txt)


        loginBtn.setOnClickListener {
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(context, "Login - Sucesso", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context, "Login - Erro", Toast.LENGTH_LONG).show()
                    }
                }
        }

        signUpBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.login_fragment_container, SignUpFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }


}