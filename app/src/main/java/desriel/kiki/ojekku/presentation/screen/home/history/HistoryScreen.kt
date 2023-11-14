package desriel.kiki.ojekku.presentation.screen.home.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.presentation.screen.home.HistoryViewModel
import desriel.kiki.ojekku.presentation.theme.Label
import desriel.kiki.ojekku.presentation.theme.Primary

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
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
fun HistoryItemList(viewModel: HistoryViewModel) {
    val historyData by viewModel.getUserHistoryForCurrentUser()
        .collectAsState(initial = Resource.Loading)

    when (historyData) {
        is Resource.Success -> {
            val historyEntity = (historyData as Resource.Success<List<HistoryEntity>>).data

            LazyColumn {
                items(historyEntity) { item ->
                    ItemHistoryList(item)

                }
            }
        }

        is Resource.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally, // Mengatur konten di tengah horizontal
                    verticalArrangement = Arrangement.Center // Mengatur konten di tengah vertikal
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_activity),
                        contentDescription = "no history"
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center, // Mengatur teks agar terpusat
                        text = "Belum ada history \n yang tersedia"
                    )
                }
            }
        }

        is Resource.Loading -> {
            // Tampilkan indikator loading jika diperlukan
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
            "",
            "Ride",
            "",
            "jalan klipang raya rt 05 / 07 no 77 kota semarang ",
            "",
            "Rp.14.700,-"
        )
    )
}

