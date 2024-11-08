package com.app.finansmart.vistas.mains

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.finansmart.viewmodels.LoginViewModel
import com.app.finansmart.viewmodels.MovimientosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaEditar(navController: NavController, movimientosViewModel: MovimientosViewModel, loginViewModel: LoginViewModel, idDocumento: String,) {
    //para ejecutar función sin tener que dar clic a nada
    print("el id documento es: "+idDocumento)
    LaunchedEffect(Unit) {
        loginViewModel.TraerSaldoPorId(idDocumento)
    }
    val estado = loginViewModel.estado
    // Obtener el estado actual del saldo como texto desde el ViewModel
    val saldoTexto = loginViewModel.saldoTexto
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Agregar Saldo") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")

                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (idDocumento.isNotEmpty()) {
                            loginViewModel.EditarSaldo(idDocumento) {
                                navController.popBackStack()
                            }
                        } else {
                            // Manejo de error
                            println("El idUsuario está vacío o es nulo.")
                        }
                    }) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "")
                    }
                }
            )
        }
    ) { pad ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {
            /*
            OutlinedTextField(
                value = saldoTexto,
                onValueChange = { nuevoTexto ->
                    loginViewModel.OnValue(nuevoTexto, "saldo")
                },
                label = { Text(text = "Saldo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            )*/

            OutlinedTextField(
                value = estado.saldo.toString(),
                onValueChange = {
                    val nuevoSaldo = it.toFloatOrNull() ?: 0f
                    loginViewModel.OnValue(nuevoSaldo, "saldo")
                },
                label = { Text(text = "Saldo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            )
        }
    }
}


