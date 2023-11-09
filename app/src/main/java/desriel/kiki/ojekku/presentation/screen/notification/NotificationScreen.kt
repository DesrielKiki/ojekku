package desriel.kiki.ojekku.presentation.screen.notification

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import desriel.kiki.ojekku.R

@Composable
fun NotificationScreen() {
    Text(text = stringResource(R.string.this_is_notification_screen), modifier = Modifier.fillMaxSize())
}