package desriel.kiki.ojekku.presentation.screen.permission

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.presentation.component.Buttons

@Composable
fun PermissionDialog(
    onLaunchPermission: (() -> Unit) -> Unit,
    onPermissionGranted : () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Text(
            text = stringResource(id = R.string.location_permission_message),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
        Buttons(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            buttonText = stringResource(id = R.string.allow_permission),
            onClick = {
                onLaunchPermission.invoke(onPermissionGranted)
            }
        )

    }
}
