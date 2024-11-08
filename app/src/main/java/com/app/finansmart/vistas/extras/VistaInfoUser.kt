package com.app.finansmart.vistas.extras

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.finansmart.R
import com.app.finansmart.componentes.BarraNavegacion
import com.app.finansmart.componentes.DialogoEditable
import com.app.finansmart.componentes.SettingsItem
import com.app.finansmart.componentes.SettingsSection
import com.app.finansmart.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VistaInfoUser(navController: NavController, loginViewModel: LoginViewModel) {
    LaunchedEffect(Unit) {
        loginViewModel.TraerUsuario()
    }

    val datos by loginViewModel.datos.collectAsState()

    var userName by remember { mutableStateOf(datos[0].username) }
    var userEmail by remember { mutableStateOf(datos[0].email) }
    var userSaldo by remember { mutableStateOf(datos[0].saldo) }

    var showDialog by remember { mutableStateOf(false) }
    var nuevoUserName by remember { mutableStateOf(userName) }


    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Información de Usuario",
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF18181A), // Color de fondo
                    titleContentColor = Color.White // Color del texto del título
                )
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF18181A))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logooriginal),
                    contentDescription = "",
                    modifier = Modifier.size(250.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp)
                    .padding(26.dp)
            ) {
                SettingsSection(title = "Username") {
                    SettingsItem(userName) {
                        showDialog = false

                    }
                }
                SettingsSection(title = "Saldo") {
                    SettingsItem("$ $userSaldo") {

                    }
                }
                SettingsSection(title = "E-mail") {
                    SettingsItem(userEmail) {

                    }
                }

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(170.dp))
                //FinanOutlinedButton(onClick = { navController.popBackStack() }, text = "Guardar")

            }
            BarraNavegacion(navController = navController)
        }


    }
}

