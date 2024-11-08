package com.app.finansmart.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.finansmart.R
import com.app.finansmart.viewmodels.MovimientosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponenteMovimientos(
    movimientosViewModel: MovimientosViewModel,
    navController: NavController
) {

    LaunchedEffect(Unit) {
        movimientosViewModel.traerNotas()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        val datos by movimientosViewModel.datos.collectAsState()
        //val ultimosMovimientos = if (datos.size > 3) datos.takeLast(3) else datos
        //trae solo los ultimos 3 movimientos ordenados si necesidad del orderby
        val ultimosMovimientos = datos.sortedByDescending { it.fechaActual }.take(3)

        if (ultimosMovimientos.isEmpty()) {
            // Mostrar mensaje cuando no hay movimientos
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.sinmovimientos), contentDescription = "")

            }
        } else {
            LazyColumn {
                items(ultimosMovimientos) { item ->
                    TarjetaUltimosMovimientos(
                        titulo = item.titulo,
                        valor = item.valor,
                        fecha = item.fecha,
                        tipoMovimiento = item.tipoMovimiento
                    ) {
                        navController.navigate("editarnota/${item.idDocumento}")
                    }
                }
            }
        }
    }
}

