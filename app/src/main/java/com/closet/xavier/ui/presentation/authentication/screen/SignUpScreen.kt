package com.closet.xavier.ui.presentation.authentication.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import com.closet.xavier.ui.presentation.authentication.events.SignUpFormEvent
import com.closet.xavier.ui.presentation.authentication.view_model.SignUpViewModel
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    context: Context,
    networkState: State<Boolean>,
    viewModel: SignUpViewModel,
    onSignInClicked: () -> Unit,
    currentUser: FirebaseUser?
) {

    val TAG = "SignUpScreen"


    val isNetworkAvailable = networkState.value

    val isLoading = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = viewModel.stateSignUp, block = {
        viewModel.stateSignUp.collect { results ->
            isLoading.value = results.isLoading
            if (results.success != null) {
                Log.d(TAG, "SignUpScreen: ${results.success}")
            }
            if (results.error != null) {
                Log.e(TAG, "SignUpScreen: ${results.error}")
            }
        }
    })

    LaunchedEffect(key1 = viewModel.stateCreateProfile, block = {
        viewModel.stateCreateProfile.collect { results ->
            isLoading.value = results.isLoading
            if (results.success) {
                Log.d(TAG, "SignUpScreen: Profile created successful!")
                Toast.makeText(context, "Profile created successful!", Toast.LENGTH_SHORT).show()
            }
            if (results.error.isNotEmpty()) {
                Log.e(TAG, "SignUpScreen: ${results.error}")
                Toast.makeText(context, results.error, Toast.LENGTH_SHORT).show()
            }
        }
    })

    BaseScaffold(
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                SignUpScreenContent(
                    viewModel = viewModel,
                    context = context,
                    onSignInClicked = onSignInClicked,
                    currentUser = currentUser
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
        }, isLoading = isLoading.value
    )

}

@Composable
fun SignUpScreenContent(
    viewModel: SignUpViewModel,
    context: Context,
    onSignInClicked: () -> Unit,
    currentUser: FirebaseUser?
) {


    val verticalSpace = 18.dp
    val horizontalSpace = 25.dp


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = verticalSpace, horizontal = horizontalSpace)
            .verticalScroll(rememberScrollState())
    ) {

        val title = if (currentUser==null) "Sign Up" else "Create User Profile"
        val subTitle = if (currentUser==null) "Welcome, Please Sign Up to create\n" + "Your profile" else "Welcome, Please create\n" + "Your profile"

        AuthenticationHeadlineSection(
            title = title, subTitle = subTitle
        )
        SignUpFormSection(viewModel = viewModel, currentUser = currentUser)
        SignUpButtonSection(onSignUp = {
            viewModel.onSignUp(SignUpFormEvent.Submit)
        }, onGoogleSignUp = {}, currentUser = currentUser)
        if (currentUser == null) {
            SignInButtonSection(onclick = onSignInClicked, modifier = Modifier.fillMaxWidth())
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpFormSection(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel,
    currentUser: FirebaseUser?
) {
    val state = viewModel.state
    val (focusEmail, focusPassword, focusPhone, focusFirstName, focusMiddleName, focusLastName) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isPasswordVisible by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 35.dp)
    ) {
        if (currentUser != null) {
            currentUser.email.let {
                if (it != null) {
                    state.email = it
                }
            }
        } else {
            BaseOutlinedTextField(
                text = state.email,
                onTextChanged = { viewModel.onSignUp(SignUpFormEvent.EmailChanged(it)) },
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
                keyboardActions = KeyboardActions(onNext = { focusPhone.requestFocus() })
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
        }
        BaseOutlinedTextField(
            text = state.phone,
            onTextChanged = { viewModel.onSignUp(SignUpFormEvent.PhoneChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusPhone),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone Icon",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            placeholder = {
                BaseText(
                    text = "Enter Phone Number", color = MaterialTheme.colorScheme.onSecondary
                )
            },
            label = {
                BaseText(text = "Phone Number", color = MaterialTheme.colorScheme.onSecondary)
            },
            isError = state.phoneError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Phone
            ),
            keyboardActions = KeyboardActions(onNext = { focusFirstName.requestFocus() })
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (state.phoneError != null) {
            val error = state.phoneError ?: ""
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
            text = state.firstName,
            onTextChanged = { viewModel.onSignUp(SignUpFormEvent.FirstNameChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusFirstName),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "First Name Icon",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            placeholder = {
                BaseText(
                    text = "Enter First Name", color = MaterialTheme.colorScheme.onSecondary
                )
            },
            label = {
                BaseText(text = "First Name", color = MaterialTheme.colorScheme.onSecondary)
            },
            isError = state.firstNameError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onNext = { focusMiddleName.requestFocus() })
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (state.firstNameError != null) {
            val error = state.firstNameError ?: ""
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
            text = state.middleName,
            onTextChanged = { viewModel.onSignUp(SignUpFormEvent.MiddleNameChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusMiddleName),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Middle Name Icon",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            placeholder = {
                BaseText(
                    text = "Enter Middle Name", color = MaterialTheme.colorScheme.onSecondary
                )
            },
            label = {
                BaseText(text = "Middle Name", color = MaterialTheme.colorScheme.onSecondary)
            },
            isError = state.middleNameError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onNext = { focusLastName.requestFocus() })
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (state.middleNameError != null) {
            val error = state.middleNameError ?: ""
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
            text = state.lastName,
            onTextChanged = { viewModel.onSignUp(SignUpFormEvent.LastNameChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusLastName),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Last Name Icon",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            placeholder = {
                BaseText(
                    text = "Enter Last Name", color = MaterialTheme.colorScheme.onSecondary
                )
            },
            label = {
                BaseText(text = "Last Name", color = MaterialTheme.colorScheme.onSecondary)
            },
            isError = state.lastNameError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() })
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (state.lastNameError != null) {
            val error = state.lastNameError ?: ""
            BaseText(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (currentUser == null) {
            BaseOutlinedTextField(
                text = state.password,
                onTextChanged = { viewModel.onSignUp(SignUpFormEvent.PasswordChanged(it)) },
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
        }
    }
}

@Composable
fun SignUpButtonSection(
    modifier: Modifier = Modifier,
    onSignUp: () -> Unit,
    onGoogleSignUp: () -> Unit,
    currentUser: FirebaseUser?
) {
    val buttonText = if (currentUser == null) "Sign Up" else "Create User Profile"
    Column(modifier = modifier.fillMaxWidth()) {
        BaseButton(
            onClick = onSignUp,
            shapePercent = 8,
            modifier = Modifier.fillMaxWidth(),
            text = buttonText
        )

        if (currentUser == null) {

            ButtonSeparatorSection()

            GoogleButton(
                name = "Sign Up with Google",
                onClick = onGoogleSignUp,
                shapePercent = 8,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
private fun SignInButtonSection(
    onclick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(onClick = onclick, modifier = modifier) {
        val text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            ) {
                append("Already have an account?")
            }
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(" Sign In")
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
