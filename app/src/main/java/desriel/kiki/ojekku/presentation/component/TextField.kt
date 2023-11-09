package desriel.kiki.ojekku.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.presentation.theme.Black
import desriel.kiki.ojekku.presentation.theme.Border
import desriel.kiki.ojekku.presentation.theme.Icon
import desriel.kiki.ojekku.presentation.theme.Label
import desriel.kiki.ojekku.presentation.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    trailingIcon: (@Composable () -> Unit)? = null,
    label: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Black,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = value,
            singleLine = true,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Black,
                unfocusedBorderColor = Border,
                focusedBorderColor = Primary,
                placeholderColor = Label,
                unfocusedTrailingIconColor = Icon,
                focusedTrailingIconColor = Primary
            ),
            shape = RoundedCornerShape(15.dp),
            placeholder = {
                Text(
                    text = placeholder, style = MaterialTheme.typography.bodyMedium
                )
            },
            trailingIcon = {
                trailingIcon?.invoke()
            },
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
        )
    }
}

@Composable
fun PlainTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit
) {
    BaseTextField(
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        keyboardOptions = keyboardOptions,
        value = value,
        onValueChange = onValueChange
    )
}

@Composable
fun TrailingTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    BaseTextField(
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        onValueChange = onValueChange,
        value = value,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    var shouldShowPassword by remember {
        mutableStateOf(false)
    }

    BaseTextField(
        label = label,
        placeholder = placeholder,
        onValueChange = onValueChange,
        modifier = modifier,
        value = value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    shouldShowPassword = !shouldShowPassword
                },
                imageVector = if (shouldShowPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                contentDescription = stringResource(R.string.hide_password)
            )
        },
        visualTransformation = if (shouldShowPassword) VisualTransformation.None else PasswordVisualTransformation()
    )
}