package desriel.kiki.ojekku.presentation.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.domain.model.EmptyStateModel
import desriel.kiki.ojekku.presentation.component.Buttons
import desriel.kiki.ojekku.presentation.component.PasswordTextField
import desriel.kiki.ojekku.presentation.component.TextHeader
import desriel.kiki.ojekku.presentation.component.TrailingTextField
import desriel.kiki.ojekku.presentation.navigation.Route
import desriel.kiki.ojekku.presentation.theme.Black
import desriel.kiki.ojekku.presentation.theme.Primary

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    onLoginError: (EmptyStateModel) -> Unit

) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val uiState by viewModel.loginUiState.collectAsState(initial = LoginUiState.Idle)
    val notHaveAccount = stringResource(R.string.not_have_account) + stringResource(R.string.space)
    val registerText = stringResource(R.string.register)
    val registerString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Black)) {
            pushStringAnnotation(tag = notHaveAccount, annotation = notHaveAccount)
            append(notHaveAccount)
        }
        withStyle(style = SpanStyle(color = Primary, fontWeight = FontWeight.SemiBold)) {
            pushStringAnnotation(tag = registerText, annotation = registerText)
            append(registerText)
        }
    }
    val isButtonEnable by remember {
        derivedStateOf {
            email.trim().isNotEmpty() &&
            password.trim().isNotEmpty()
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Loading -> {

            }

            is LoginUiState.Success -> {
                viewModel.storeEmail((uiState as LoginUiState.Success).data.email)
                viewModel.storeUserName((uiState as LoginUiState.Success).data.userName)
                viewModel.storeFullName((uiState as LoginUiState.Success).data.fullName)
                onNavigateToHome.invoke()
            }

            is LoginUiState.Error -> {
                onLoginError.invoke(
                    EmptyStateModel(
                        R.raw.something_went_wrong,
                        "Ups, something error",
                        (uiState as LoginUiState.Error).message,
                        "Okay"
                    )
                )
            }

            else -> Unit
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 44.dp)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextHeader(
            modifier = Modifier.fillMaxWidth(),
            headerText = "Selamat Datang",
            supportText = "Masukkan email dan password dari akun yang pernah kamu buat sebelumnya"
        )
        TrailingTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp),
            value = email,
            label = "Email",
            placeholder = "Masukkan Email",
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mail), contentDescription = "Email"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                email = it
            })
        PasswordTextField(modifier = Modifier.padding(top = 24.dp),
            value = password,
            label = "Password",
            placeholder = "Masukkan Password",
            onValueChange = { password = it })
        Buttons(
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth(),
            buttonText = "Masuk",
            enabled = isButtonEnable,
            onClick = {
                viewModel.login(email, password)
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        ClickableText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp, top = 70.dp),
            text = registerString,
            onClick = { offset ->
                registerString.getStringAnnotations(offset, offset)
                    .firstOrNull()?.let { span ->
                        if (span.item == registerText) {
                            onNavigateToRegister.invoke()
                        }
                    }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center
            ),
        )


    }
}
