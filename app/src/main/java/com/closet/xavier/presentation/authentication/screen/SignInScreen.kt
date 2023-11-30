package com.closet.xavier.presentation.authentication.screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.closet.xavier.presentation.authentication.events.AuthenticationFormEvent
import com.closet.xavier.presentation.authentication.view_model.AuthenticationViewModel
import com.closet.xavier.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(viewModel: AuthenticationViewModel,modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val TAG = "SignInScreen"



    LaunchedEffect(key1 = viewModel.stateSignIn) {
        viewModel.stateSignIn.collect { state ->
            if (!state.success.isNullOrEmpty()) {
                Toast.makeText(context, state.success, Toast.LENGTH_SHORT).show()
            }
            if (!state.error.isNullOrEmpty()) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.signInWithGoogle(credential = credential)
            } catch (e: ApiException) {
                e.stackTrace
                Log.e(TAG, "SignInScreen: ${e.message}", e)
            }
        })

    val state = viewModel.state
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {

        OutlinedTextField(
            value = state.email,
            onValueChange = {
                viewModel.onSignIn(AuthenticationFormEvent.EmailChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = {
                viewModel.onSignIn(AuthenticationFormEvent.PasswordChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = { viewModel.onSignIn(AuthenticationFormEvent.Submit) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Sign In")
        }

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(Constants.GOOGLE_TOKEN_ID)
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)

                launcher.launch(googleSignInClient.signInIntent)


            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Google Sign In")
        }

    }
}