package com.superstore.crsm.ui.login
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
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
    private lateinit var googleSignInClient: GoogleSignInClient


    private val googleSignInRequest = registerForActivityResult(
        GoogleLogInActivityContract(),
        ::onGoogleSignInResult
    )


    private val googleSignInOptions : GoogleSignInOptions
        get() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("186025361439-meba0np18572silc9io0uq7a8ohdnnka.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("186025361439-meba0np18572silc9io0uq7a8ohdnnka.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(view.context, gso)

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
            //googleSignInRequest.launch(googleSignInOptions)

            signIn()
        }


    }


    private fun onGoogleSignInResult(result: GoogleLogInActivityContract.Result?) {
        if(result is GoogleLogInActivityContract.Result.Success){
            val token = result.googleSignInAccount.idToken
            println(token)
            if (token != null) {
                loginWithGoogle(token)
            }
            Toast.makeText(context, "LoginGoogle -> $token", Toast.LENGTH_LONG).show()
        }else{
            println("Deu Ruim")
        }
    }


    private fun loginWithGoogle(token: String){
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
                task: Task<AuthResult> ->
                if(task.isSuccessful){
                    Toast.makeText(context, "Login - Register", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "Register - Error", Toast.LENGTH_LONG).show()
                }
        }

    }


    private fun signIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGM_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RC_SIGM_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try{
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("signInGoogle", "FirebaseAuth" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                }catch (e: ApiException){
                    Log.w("signInGoogle", "Google failed", e)
                }
            }else{
                Log.w("signInGoogle",  exception.toString() )
            }


        }
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(){ task ->
                if(task.isSuccessful){
                    Toast.makeText(context, "Login - Register", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "Login - Deu Ruim", Toast.LENGTH_LONG).show()
                }
            }
    }

    companion object{
        private const val RC_SIGM_IN = 120
    }

}