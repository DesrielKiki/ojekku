package desriel.kiki.ojekku.presentation.screen.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.local.room.entity.HistoryEntity
import desriel.kiki.ojekku.R
import desriel.kiki.ojekku.presentation.component.CircularIconButton
import desriel.kiki.ojekku.presentation.component.TextHeader
import desriel.kiki.ojekku.presentation.screen.login.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onRideButtonClicked: () -> Unit,
    onCarButtonClicked: () -> Unit,
    historyItemViewModel: HistoryItemViewModel,
    onClickHistory: () -> Unit,
    loginViewModel: LoginViewModel
) {

    val usernameState by remember { loginViewModel.getUserName() }
    Log.d("home screen", "usernamestate = $usernameState")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row {
            TextHeader(
                headerText = "Selamat Datang, $usernameState",
                supportText = "Silahkan pilih layanan yang ingin anda gunakan"
            )

        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            CircularIconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = onRideButtonClicked,
                icon = R.drawable.ic_motor,
                title = "Ride"
            )
            CircularIconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = onCarButtonClicked,
                icon = R.drawable.ic_car,
                title = "Car"
            )
            CircularIconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { /*TODO*/ },
                icon = R.drawable.ic_food,
                title = "Food"
            )
            CircularIconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { /*TODO*/ },
                icon = R.drawable.ic_market,
                title = "Market"
            )
            CircularIconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { /*TODO*/ },
                icon = R.drawable.ic_electric,
                title = "Electric"
            )
            CircularIconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { /*TODO*/ },
                icon = R.drawable.ic_other,
                title = "Other"
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        )
        Row(modifier = Modifier
            .clickable { onClickHistory() }
            .align(alignment = CenterHorizontally)) {

            Text(text = "Riwayat Pesanan", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right_black), // Ganti dengan ikon yang Anda inginkan
                contentDescription = "ride",
                tint = Color.Black,
                modifier = Modifier.size(24.dp) // Ukuran ikon di sini
            )
        }
        HistoryItemGrid(historyItemViewModel)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        )



    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryItemGrid(viewModel: HistoryItemViewModel) {
    val historyData by viewModel.getUserHistoryForCurrentUser().collectAsState(initial = Resource.Loading)

    when (historyData) {
        is Resource.Success -> {
            val historyEntity = (historyData as Resource.Success<HistoryEntity>).data
            Log.d("home screen", "history items = $historyEntity")

            if (historyEntity != null) { // Periksa apakah historyEntity tidak null
                LazyRow {
                    items(listOf(historyEntity)) { item ->
                        ItemHistoryGrid(item)
                    }
                }
            }
        }
        is Resource.Error -> {
            // Tampilkan pesan kesalahan jika diperlukan
        }
        is Resource.Loading -> {
            // Tampilkan indikator loading jika diperlukan
        }
        else -> {}
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemHistoryGrid(item: HistoryEntity) {
    Column(
        modifier = Modifier
            .width(172.dp)
            .padding(horizontal = 4.dp, vertical = 12.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF56ab2f), Color(0xFF00AA12))
                ),
                shape = RoundedCornerShape(12.dp) // Mengatur sudut lengkung di sini
            )


    ) {
        Box(modifier = Modifier.size(width = 124.dp, height = 84.dp)) {
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
                contentDescription = "ride",
                tint = Color.White,
                modifier = Modifier
                    .size(84.dp)
                    .align(Alignment.Center) // Ukuran ikon di sini
            )
        }
        Text(
            text = "Ride To ${item.destinationLocation}",
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = item.orderTime,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(start = 8.dp, end = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White) // Ubah warna teks di sini
        )
        Text(
            text = item.tariff, modifier = Modifier
                .padding(top = 4.dp, bottom = 8.dp)
                .padding(horizontal = 8.dp)
        )
    }

}



