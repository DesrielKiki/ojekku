package desriel.kiki.ojekku.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import desriel.kiki.ojekku.R

@Composable
fun LanguageSelectionDialog(
    onLanguageSelected: (String) -> Unit,
    dismissDialog: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { dismissDialog() }
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.select_language), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))

                // List of language options
                LanguageOption("Indonesia", "id", selectedLanguage) {
                    selectedLanguage = it
                }

                LanguageOption("English", "en", selectedLanguage) {
                    selectedLanguage = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onLanguageSelected(selectedLanguage)
                            dismissDialog()
                        }
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Composable
fun LanguageOption(
    languageName: String,
    languageCode: String,
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    val isSelected = selectedLanguage == languageCode
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
    val circleIcon = if (!isSelected) Icons.Outlined.Circle else Icons.Filled.Circle
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onLanguageSelected(languageCode)
            }

            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically // Menengahkan secara vertikal
    ) {
        Icon(
            modifier = Modifier.padding(start = 8.dp),
            imageVector = circleIcon,
            contentDescription = "selection"
        )
        Text(
            text = languageName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = textColor,
        )
    }
}

