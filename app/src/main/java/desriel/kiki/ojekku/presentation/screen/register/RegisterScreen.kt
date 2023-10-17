package desriel.kiki.ojekku.presentation.screen.register

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import desriel.kiki.core.domain.model.User
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.domain.model.EmptyStateModel
import desriel.kiki.ojekku.presentation.component.Buttons
import desriel.kiki.ojekku.presentation.component.PasswordTextField
import desriel.kiki.ojekku.presentation.component.PlainTextField
import desriel.kiki.ojekku.presentation.component.TextHeader
import desriel.kiki.ojekku.presentation.component.TrailingTextField
import desriel.kiki.ojekku.presentation.navigation.Route
import desriel.kiki.ojekku.presentation.theme.Black
import desriel.kiki.ojekku.presentation.theme.Primary

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onRegisterError: (EmptyStateModel) -> Unit
) {
    var name by remember {
        mutableStateOf("")
    }
    var number by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    val haveAccount = stringResource(R.string.have_account) + stringResource(R.string.space)
    val loginText = stringResource(R.string.login)
    val loginString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Black)) {
            pushStringAnnotation(tag = haveAccount, annotation = haveAccount)
            append(haveAccount)
        }
        withStyle(style = SpanStyle(color = Primary, fontWeight = FontWeight.SemiBold)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }
    val uiState by viewModel.registerUiState.collectAsState(initial = RegisterUiState.Idle)
    val isButtonEnable by remember {
        derivedStateOf {
            name.trim().isNotEmpty() &&
                    number.trim().isNotEmpty() &&
                    email.trim().isNotEmpty() &&
                    password.trim().isNotEmpty() &&
                    confirmPassword == password
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is RegisterUiState.Loading -> {

            }
            is RegisterUiState.Success -> {
                onNavigateBack.invoke()
            }
            is RegisterUiState.Error -> {
                onRegisterError.invoke(
                    EmptyStateModel(
                        R.raw.something_went_wrong,
                        "Ups, something error",
                        (uiState as RegisterUiState.Error).message,
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
            headerText = "Daftar Akun",
            supportText = "Masukkan identitas yang diperlukan untuk dapat menikmati keseruannya"
        )
        PlainTextField(
            modifier = Modifier.padding(top = 36.dp),
            label = "Nama Lengkap",
            value = name,
            placeholder = "Nama Lengkap Anda",
            onValueChange = { name = it })
        PlainTextField(
            modifier = Modifier.padding(top = 24.dp),
            label = "No. HP",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            value = number,
            placeholder = "Nomor handphone anda",
            onValueChange = { number = it })
        TrailingTextField(
            modifier = Modifier.padding(top = 24.dp),
            value = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mail), contentDescription = "Email"
                )
            },
            label = "Email",
            placeholder = "Masukkan Email",
            onValueChange = { email = it },
        )
        PasswordTextField(
            modifier = Modifier.padding(top = 24.dp),
            value = password,
            label = "Password",
            placeholder = "Masukkan Password",
            onValueChange = { password = it })
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            value = confirmPassword,
            label = "Konfirmasi Password",
            placeholder = "Masukkan konfirmasi password",
            onValueChange = {
                confirmPassword = it
            }
        )
        Buttons(
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth(),
            buttonText = "Masuk",
            enabled = isButtonEnable,
            onClick = {
                viewModel.register(
                    User(email, password, name, number)
                )
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        ClickableText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp, top = 70.dp),
            text = loginString,
            onClick = { offset ->
                loginString.getStringAnnotations(offset, offset)
                    .firstOrNull()?.let { span ->
                        if (span.item == loginText) {
                            onNavigateBack.invoke()
                        }
                    }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center
            ),
        )


    }
}