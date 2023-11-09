package desriel.kiki.ojekku.presentation.screen.profile

import android.preference.Preference
import android.preference.PreferenceScreen
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import desriel.kiki.core.data.source.local.datastore.OjekkuDataStore
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.presentation.component.Buttons
import desriel.kiki.ojekku.presentation.component.CircularIconButton
import desriel.kiki.ojekku.presentation.component.IconText
import desriel.kiki.ojekku.presentation.component.LanguageSelectionDialog
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    languageViewModel: LanguageViewModel,
    onLogoutClick: () -> Unit,
) {
    val userNameState by remember { profileViewModel.getUserName() }
    val fullNameState by remember { profileViewModel.getFullName() }

    var isLanguageSelectionDialogVisible by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircularIconButton(
                onClick = { /*TODO*/ },
                icon = R.drawable.ic_person,
                title = "",
                modifier = Modifier.size(78.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = fullNameState,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = userNameState,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Card(
            modifier = Modifier.padding(top = 8.dp),
            border = BorderStroke(width = 0.4.dp, color = Color.LightGray)
        ) {
            IconText(imageVector = Icons.Filled.AccountCircle, text = stringResource(R.string.Account), onClick = {})
            Divider(modifier = Modifier.fillMaxWidth())
            IconText(imageVector = Icons.Filled.Payment, text = stringResource(R.string.payment_method), onClick = {})
            Divider(modifier = Modifier.fillMaxWidth())

            IconText(imageVector = Icons.Filled.History, text = stringResource(R.string.order_history), onClick = {})

        }
        Card(
            modifier = Modifier.padding(top = 16.dp),
            border = BorderStroke(width = 0.4.dp, color = Color.LightGray)
        ) {
            IconText(imageVector = Icons.Filled.Settings, text = stringResource(R.string.Setting), onClick = {})
            Divider(modifier = Modifier.fillMaxWidth())
            IconText(imageVector = Icons.Filled.Language, text = stringResource(R.string.language), onClick = {
                isLanguageSelectionDialogVisible = true
            })
            Divider(modifier = Modifier.fillMaxWidth())
            IconText(imageVector = Icons.AutoMirrored.Filled.Help, text = stringResource(R.string.help), onClick = {})
            Divider(modifier = Modifier.fillMaxWidth())
            IconText(imageVector = Icons.Filled.MoreHoriz, text = stringResource(R.string.about_application), onClick = {})
        }
        val context = LocalContext.current

        if (isLanguageSelectionDialogVisible) {
            LanguageSelectionDialog(
                onLanguageSelected = { languageCode ->
                    // Panggil metode setLanguage di dalam view model
                    languageViewModel.setLanguage(languageCode)
                    LocaleManager.setLocale(context, languageCode) // Mengatur bahasa aplikasi
                    isLanguageSelectionDialogVisible = false
                },
                dismissDialog = {
                    isLanguageSelectionDialogVisible = false
                }
            )
        }

        Buttons(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(), buttonText = stringResource(R.string.logout),
            onClick = {
                profileViewModel.logout()
                onLogoutClick()
            },
            buttonTextStyle = MaterialTheme.typography.titleMedium
        )
    }
}
