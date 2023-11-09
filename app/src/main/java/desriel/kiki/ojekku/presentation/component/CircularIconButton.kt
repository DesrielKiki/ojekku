package desriel.kiki.ojekku.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.presentation.theme.Label
import desriel.kiki.ojekku.presentation.theme.Primary
import java.nio.file.WatchEvent

@Composable
fun CircularIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String
) {
    Column {
        Box(
            modifier = modifier
                .size(64.dp) // Ubah ukuran sesuai kebutuhan Anda
                .background(Primary, shape = CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon), // Ganti dengan ikon yang Anda inginkan
                contentDescription = "ride",
                tint = Color.White,
                modifier = Modifier.size(48.dp) // Ukuran ikon di sini
            )
        }
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(64.dp)
                .padding(top = 6.dp)
        )

    }
}
