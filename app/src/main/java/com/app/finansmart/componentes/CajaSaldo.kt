package com.app.finansmart.componentes


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.finansmart.R
import com.app.finansmart.viewmodels.LoginViewModel
import com.app.finansmart.viewmodels.MovimientosViewModel
import java.text.DecimalFormat


@Composable
fun CajaSaldo(
    loginViewModel: LoginViewModel,
    navController: NavController,
    movimientosViewModel: MovimientosViewModel
) {
    LaunchedEffect(Unit) {
        loginViewModel.TraerUsuario()
    }

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 55.dp)
    ){

        Box(
            modifier = Modifier
                //.offset(x = 40.dp, y = 50.dp)
                .size(320.dp, 170.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp,
                        bottomEnd = 40.dp,
                        bottomStart = 40.dp
                    )
                )
                .background(Color(0xFF40413F))
                .padding(16.dp)
        ) {
            val datos by loginViewModel.datos.collectAsState()
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .offset(x = -110.dp, y = -75.dp)
                    .fillMaxSize()

            ) {
                Image(
                    painter = painterResource(id = R.drawable.logooriginal),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)
                )
            }
            Column {
                Text(
                    text = if (datos.isNotEmpty()) {
                        "Hola ${datos[0].username} !"
                    } else {
                        ""
                    },
                    color = Color.White,
                    modifier = Modifier.offset(x = 70.dp, y = 1.dp),
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = "Bienvenido!",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.offset(x = 70.dp, y = 1.dp)
                )
                Text(
                    text = "Balance Total:",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.offset(y = 20.dp)
                )
                val saldo = if (datos.isNotEmpty()) datos[0].saldo else 0
                val formatter = DecimalFormat("#,##0.0")
                Text(
                    text = "$ ${formatter.format(saldo)}",
                    color = Color.White,
                    modifier = Modifier
                        .offset(x = 10.dp, y = 25.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Light,
                    maxLines = 1, // Limita el texto a una sola línea
                    overflow = TextOverflow.Ellipsis // Agrega "..." al final si el texto es demasiado largo
                )

            }
        }
    }

}