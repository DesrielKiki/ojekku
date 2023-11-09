package desriel.kiki.ojekku.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.presentation.theme.Primary

@Composable
fun OrderButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.icon__arrow_right_,
    title: String = "",
    distance : String = "",
    tariff: String = "",
    elevation: Dp = 15.dp,
    shape: Shape = RoundedCornerShape(32.dp),
    backgroundColor: Color = Color.White,
    color: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
        containerColor = Primary
    ),

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation)
            .background(backgroundColor),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(R.string.distance) + distance, modifier = Modifier
                .padding(horizontal = 28.dp)
                .padding(bottom = 4.dp))
            Button(
                modifier = modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 17.dp, top = 12.dp),
                onClick = onClick,
                shape = shape,
                colors = color
            ) {
                Text(
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    color = Color.White,
                    modifier = Modifier.padding(end = 8.dp),
                    text = tariff, style = MaterialTheme.typography.labelLarge
                )
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Leading Icon",
                    tint = Color.Unspecified
                )

            }
        }


    }
}
