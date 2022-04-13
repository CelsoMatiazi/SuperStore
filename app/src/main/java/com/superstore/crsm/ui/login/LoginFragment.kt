package com.superstore.crsm.ui.login
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.superstore.crsm.R
import com.superstore.crsm.util.GoogleLogInActivityContract

class LoginFragment : Fragment(R.layout.fragment_login) {


    private lateinit var auth: FirebaseAuth

    private val googleSignInRequest = registerForActivityResult(
        GoogleLogInActivityContract(),
        ::onGoogleSignInResult
    )


    private val googleSignInOptions : GoogleSignInOptions
        get() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("186025361439-1j5bj61tpb4238sgsl9guviomoonq077.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val signUpBtn : TextView = view.findViewById(R.id.login_bottom_txt2)
        val loginBtn : MaterialButton = view.findViewById(R.id.login_entrar_btn)
        val googleBtn : MaterialButton = view.findViewById(R.id.login_google_btn)
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

        googleBtn.setOnClickListener {
            googleSignInRequest.launch(googleSignInOptions)
        }


    }


    private fun onGoogleSignInResult(result: GoogleLogInActivityContract.Result?) {
        if(result is GoogleLogInActivityContract.Result.Success){
            val token = result.googleSignInAccount.idToken
            token?.let {
                loginWithGoogleFirebase(token)
            }
            Toast.makeText(context, "LoginGoogle -> ${token!!.take(155)}......", Toast.LENGTH_LONG).show()
        }else{
            println("Result not Success")
        }
    }


    private fun loginWithGoogleFirebase(token: String){
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential)
            .addOnFailureListener{
                println(it)
            }
            .addOnCompleteListener {
                Result: Task<AuthResult> ->
                if(Result.isSuccessful){
                    Toast.makeText(context, "Firebase Google Login - Success", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "Firebase Google Login - Failed", Toast.LENGTH_LONG).show()
                }
        }

    }


}