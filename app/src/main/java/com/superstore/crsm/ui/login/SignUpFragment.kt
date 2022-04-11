package com.superstore.crsm.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.superstore.crsm.R

class SignUpFragment : Fragment(R.layout.fragment_cadastro) {


    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val backBtn = view.findViewById<ImageView>(R.id.cadastro_back_btn)
        val signUpBtn = view.findViewById<Button>(R.id.cadastro_entrar_btn)
        val email : TextInputEditText = view.findViewById(R.id.cadastro_email_txt)
        val password : TextInputEditText = view.findViewById(R.id.cadastro_password_txt)

        signUpBtn.setOnClickListener {
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(context, "fez cadastro", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context, "Cadastro - Erro", Toast.LENGTH_LONG).show()
                    }
                }
        }



        backBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }


}