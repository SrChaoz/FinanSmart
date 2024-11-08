package com.app.finansmart.vistas.mains

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.finansmart.R

import com.app.finansmart.componentes.BarraNavegacion
import com.app.finansmart.componentes.TarjetaMovimiento
import com.app.finansmart.viewmodels.LoginViewModel
import com.app.finansmart.viewmodels.MovimientosViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaResumen(
    movimientosViewModel: MovimientosViewModel,
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    LaunchedEffect(Unit) {
        movimientosViewModel.traerNotas() //cargar los movimientos al iniciar
    }

    val datos by loginViewModel.datos.collectAsState()
    val idDocumentoUsuario = if (datos.isNotEmpty()) datos[0].idDocumento else ""
    val saldo = datos[0].saldo
    //val idUsuario = datos[0].idUsuario
    val movimientos by movimientosViewModel.datos.collectAsState()
    // me devuelve los movimientos ordenandos sin necesidad de el order by
    val Movimientos = movimientos.sortedByDescending { it.fechaActual }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Resumen de Movimientos")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF18181A), // Color de fondo
                    titleContentColor = Color.White // Color del texto del título
                )
            )
        },

    ) { padding ->
        Box(modifier = Modifier.run {
            fillMaxSize()
                .background(Color(0xFF18181A))
        }) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                top = 75.dp, bottom = 100.dp
            )
                .clip(
                    RoundedCornerShape(
                        topStart = 45.dp,
                        topEnd = 45.dp,
                        bottomEnd = 45.dp,
                        bottomStart = 45.dp
                    )
                )
        ) {
            if (Movimientos.isEmpty()) {
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
                    items(Movimientos) { movimiento ->

                        TarjetaMovimiento(
                            titulo = movimiento.titulo,
                            valor = movimiento.valor,
                            fecha = movimiento.fecha,
                            tipoMovimiento = movimiento.tipoMovimiento,
                            idDocumento = movimiento.idDocumento,
                            rutaImagen = movimiento.rutaImagen,
                            idDocumentoUsuario = idDocumentoUsuario,
                            saldo = saldo,
                            loginViewModel = loginViewModel,
                            movimientosViewModel = movimientosViewModel

                        ){
                            navController.navigate("vistafoto/${movimiento.idDocumento}")

                        }

                    }


                }
            }

        }
        BarraNavegacion(navController = navController)
    }
    }



}




