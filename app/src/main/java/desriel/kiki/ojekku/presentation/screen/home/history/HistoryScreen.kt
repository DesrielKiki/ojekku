package desriel.kiki.ojekku.presentation.screen.home.history

import android.content.res.Resources.Theme
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.maps.model.Circle
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.presentation.screen.home.HistoryItemViewModel
import desriel.kiki.ojekku.presentation.screen.home.HistoryUiState
import desriel.kiki.ojekku.presentation.screen.home.HomeViewModel
import desriel.kiki.ojekku.presentation.theme.Label
import desriel.kiki.ojekku.presentation.theme.Primary

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    viewModel: HistoryItemViewModel,
    onClose: () -> Unit

) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(top = 8.dp)) {
            IconButton(modifier = Modifier.size(48.dp), onClick = onClose) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Close Pick Location"
                )

            }
            Text(
                text = "Riwayat Pemesanan",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )


        }
        HistoryItemList(viewModel = viewModel)

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryItemList(viewModel: HistoryItemViewModel) {
    val historyItems by viewModel.historyItems.collectAsState()

    when (historyItems) {
        is HistoryUiState.ShowHistory -> {
            val context = LocalContext.current

            val items = (historyItems as HistoryUiState.ShowHistory).historyItems
            LazyColumn {
                items(items) { item ->
                    ItemHistoryList(item)
                }
            }
        }

        is HistoryUiState.Error -> {
            val message = (historyItems as HistoryUiState.Error).message
            val context = LocalContext.current

        }

        is HistoryUiState.Loading -> {
            val context = LocalContext.current


        }

        else -> {}
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemHistoryList(item: HistoryEntity) {
    Row(
        modifier = Modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Order Logo (Di pojok kiri)
        Box(
            modifier = Modifier
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = when (item.orderType) {
                        "Ride" -> R.drawable.ic_motor
                        "Car" -> R.drawable.ic_car
                        "Food" -> R.drawable.ic_food
                        "Market" -> R.drawable.ic_market
                        "Electric" -> R.drawable.ic_electric
                        else -> R.drawable.ic_other
                    }
                ),
                contentDescription = when (item.orderType) {
                    "Ride" -> "Ride"
                    "Car" -> "Car"
                    "Food" -> "Food"
                    "Market" -> "Market"
                    "Electric" -> "Electric"
                    else -> "Other"
                },
                tint = Primary,
                modifier = Modifier
                    .size(84.dp)
                    .align(Alignment.Center) // Ukuran ikon di sini
            )
        }

        // Order Detail (Di antara order logo dan order tariff)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Ride to ${item.destinationLocation}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = item.orderTime,
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.labelMedium,
                color = Label
            )

        }

        // Order Tariff (Di pojok kanan)
        Text(
            text = item.tariff,
            fontSize = 16.sp
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ListHistoryPrev() {
    ItemHistoryList(
        item = HistoryEntity(
            0L,
            "",
            "",
            "Ride",
            "",
            "jalan klipang raya rt 05 / 07 no 77 kota semarang ",
            "",
            "Rp.14.700,-"
        )
    )
}
