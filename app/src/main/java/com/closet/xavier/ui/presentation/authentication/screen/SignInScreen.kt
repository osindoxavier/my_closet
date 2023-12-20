package com.closet.xavier.ui.presentation.authentication.screen

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.closet.xavier.ui.components.button.BaseButton
import com.closet.xavier.ui.components.button.GoogleButton
import com.closet.xavier.ui.components.network.NetworkBox
import com.closet.xavier.ui.components.scaffold.BaseScaffold
import com.closet.xavier.ui.components.text.BaseOutlinedTextField
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.ui.presentation.authentication.events.AuthenticationFormEvent
import com.closet.xavier.ui.presentation.authentication.view_model.SignInViewModel
import com.closet.xavier.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    networkState: State<Boolean>,
    viewModel: SignInViewModel,
    context: Context,
    onSignUpClick: () -> Unit
) {
    val isLoading = remember {
        mutableStateOf(false)
    }

    var user by remember { mutableStateOf(Firebase.auth.currentUser) }


    LaunchedEffect(key1 = viewModel.stateSignIn) {
        viewModel.stateSignIn.collect { state ->
            isLoading.value = state.isLoading
            if (state.success!=null) {
                user = state.success
                Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
            }
            if (!state.error.isNullOrEmpty()) {
                Toast.makeText(context, "Sign In Error. Create Account!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(key1 = viewModel.stateSignIn) {
        viewModel.stateSignIn.collect { state ->
            isLoading.value = state.isLoading
            if (state.success!=null) {
                user = state.success
                Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
            }
            if (!state.error.isNullOrEmpty()) {
                Toast.makeText(context, "Sign In Error. Create Account!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val isNetworkAvailable = networkState.value
    BaseScaffold(
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                SignInScreenContent(
                    viewModel = viewModel, context = context, onSignUpClick = onSignUpClick
                )
                if (!isNetworkAvailable) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
                        NetworkBox(
                            isConnected = isNetworkAvailable
                        )
                    }
                }
            }
        },
        isLoading = isLoading.value
    )
}

@Composable
fun SignInScreenContent(
    viewModel: SignInViewModel, context: Context, onSignUpClick: () -> Unit
) {
    val TAG = "SignInScreen"

    val verticalSpace = 18.dp
    val horizontalSpace = 25.dp

    //google sign in
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
//    val launcher = rememberFirebaseAuthLauncher(
//        onAuthComplete = { result ->
//            user = result.user
//        },
//        onAuthError = {
//            user = null
//        },
//        viewModel=viewModel
//    )
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(),
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = verticalSpace, horizontal = horizontalSpace),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AuthenticationHeadlineSection(
            title = "Sign In", subTitle = "Welcome back, Please sign in to\n" +
                    "continue"
        )
        SignInFormSection(viewModel = viewModel, onForgetPasswordClicked = {})
        Spacer(modifier = Modifier.height(10.dp))
        SignInButtonSection(
            onSignIn = { viewModel.onSignIn(AuthenticationFormEvent.Submit) },
            onGoogleSignIn = {
                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                        .requestIdToken(Constants.GOOGLE_TOKEN_ID).build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)

                launcher.launch(googleSignInClient.signInIntent)
            })
        SignUpButtonSection(onclick = onSignUpClick, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInFormSection(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel,
    onForgetPasswordClicked: () -> Unit
) {
    val state = viewModel.state

    val (focusEmail, focusPassword) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        BaseOutlinedTextField(
            text = state.email,
            onTextChanged = { viewModel.onSignIn(AuthenticationFormEvent.EmailChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusEmail),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            placeholder = {
                BaseText(
                    text = "Enter Email Address", color = MaterialTheme.colorScheme.onSecondary
                )
            },
            label = {
                BaseText(text = "Email Address", color = MaterialTheme.colorScheme.onSecondary)
            },
            isError = state.emailError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() })
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (state.emailError != null) {
            val error = state.emailError ?: ""
            BaseText(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        BaseOutlinedTextField(
            text = state.password,
            onTextChanged = { viewModel.onSignIn(AuthenticationFormEvent.PasswordChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusPassword),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Password Visible toggle icon"
                    )
                }
            },
            placeholder = {
                BaseText(
                    text = "Enter Password", color = MaterialTheme.colorScheme.onSecondary
                )
            },
            label = {
                BaseText(text = "Password", color = MaterialTheme.colorScheme.onSecondary)
            },
            isError = state.passwordError != null,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (state.passwordError != null) {
            val error = state.passwordError ?: ""
            BaseText(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall
            )
        }
        ForgetPasswordSection(onClick = onForgetPasswordClicked)

    }
}

@Composable
fun ForgetPasswordSection(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        TextButton(onClick = { onClick() }) {
            BaseText(
                text = "Forget Password?",
                color = MaterialTheme.colorScheme.inversePrimary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

    }
}


@Composable
fun SignInButtonSection(
    modifier: Modifier = Modifier, onSignIn: () -> Unit, onGoogleSignIn: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        BaseButton(
            onClick = onSignIn,
            shapePercent = 8,
            modifier = Modifier.fillMaxWidth(),
            text = "Sign In"
        )

        ButtonSeparatorSection()

        GoogleButton(
            name = "Sign In with Google",
            onClick = onGoogleSignIn,
            shapePercent = 8,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
private fun SignUpButtonSection(onclick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(onClick = onclick, modifier = modifier) {
        val text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            ) {
                append("Don't have an account?")
            }
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(" Sign Up")
            }
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.3.sp,
            textAlign = TextAlign.Center
        )
    }
}


