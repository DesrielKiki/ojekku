package desriel.kiki.ojekku.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import desriel.kiki.ojekku.presentation.theme.Black
import desriel.kiki.ojekku.presentation.theme.Label

@Composable
fun TextHeader(
    modifier: Modifier = Modifier,
    headerText: String,
    supportText: String,
    headerColor: Color = Black,
    supportColor: Color = Label,
    headerStyle: TextStyle = MaterialTheme.typography.titleLarge,
    supportStyle: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Column(modifier = modifier) {
        Text(text = headerText, style = headerStyle, color = headerColor)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = supportText, style = supportStyle, color = supportColor)
    }
}
