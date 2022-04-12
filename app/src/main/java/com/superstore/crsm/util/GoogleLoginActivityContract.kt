package com.superstore.crsm.util

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleLogInActivityContract  : ActivityResultContract<
        GoogleSignInOptions,
        GoogleLogInActivityContract.Result
        >() {
    override fun  createIntent (context: Context, input: GoogleSignInOptions): Intent {
        val client =
            GoogleSignIn.getClient(context , input ?: error("No GoogleSignInOptions" ))
        return client.signInIntent
    }
    override fun  parseResult (resultCode: Int , intent: Intent?): Result {
        return handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(intent))
    }
    private fun  handleSignInResult (completedTask: Task<GoogleSignInAccount>): Result {
        return try  {
            Result.Success(completedTask.result)
        } catch (exception: Exception) {
            (exception  as? ApiException)?.let {
                Log.w("GOOGLE", "signInResult:failed code= ${it.statusCode}")
            }
            Result.Error(exception)
        }
    }
    sealed class  Result {
        data class  Success(val googleSignInAccount : GoogleSignInAccount) : Result()
        data class  Error(val exception: Exception) : Result()
    }

}

