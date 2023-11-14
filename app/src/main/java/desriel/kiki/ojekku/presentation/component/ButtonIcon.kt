package desriel.kiki.ojekku.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithIconAndImage(
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    // IconButton with an icon
    IconButton(
        onClick = { /* Handle button click here */ },
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null, // Content description for accessibility
            tint = Color.White // Tint color for the icon
        )
    }
}
