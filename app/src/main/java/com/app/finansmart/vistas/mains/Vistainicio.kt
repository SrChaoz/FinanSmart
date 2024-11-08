package com.app.finansmart.vistas.mains

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.finansmart.componentes.BarraNavegacion
import com.app.finansmart.componentes.CajaSaldo
import com.app.finansmart.componentes.ComponenteMovimientos
import com.app.finansmart.viewmodels.LoginViewModel
import com.app.finansmart.viewmodels.MovimientosViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Vistainicio(
    loginViewModel: LoginViewModel,
    navController: NavController,
    movimientosViewModel: MovimientosViewModel
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF18181A))

    ) {


        CajaSaldo(
            loginViewModel = loginViewModel,
            navController = navController,
            movimientosViewModel = movimientosViewModel
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 170.dp)

        ) {

            Text(
                text = "Ultimos Movimientos",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp, // Ajuste del tamaño del texto
                fontStyle = FontStyle.Italic,
            )

            Box(
                modifier = Modifier
                    // offset(x = 0.dp, y = 320.dp)
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomEnd = 10.dp,
                            bottomStart = 10.dp
                        )
                    )
                    .padding(start = 10.dp, end = 10.dp)
                    .background(Color(0x03609B26))

            ) {

                ComponenteMovimientos(
                    movimientosViewModel = movimientosViewModel,
                    navController = navController
                )
            }
        }

        BarraNavegacion(navController = navController)


    }


}



