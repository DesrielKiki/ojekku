package desriel.kiki.ojekku.presentation.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.presentation.component.CircularIconButton

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onLogoutClick: () -> Unit,
    ) {
    val userNameState by remember { profileViewModel.getUserName() }
    val fullNameState by remember { profileViewModel.getFullName() }

    val backgroundSize = 140.dp
    Column(modifier = Modifier.padding(vertical = 16.dp ,  horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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

            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "logout",
                tint = Color.Black,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        onLogoutClick()
                        profileViewModel.logout()
                    }
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        )

    }
}
