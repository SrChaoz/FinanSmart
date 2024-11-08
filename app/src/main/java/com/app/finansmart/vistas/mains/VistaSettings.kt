package com.app.finansmart.vistas.mains

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.finansmart.viewmodels.MovimientosViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.app.finansmart.componentes.Alerta
import com.app.finansmart.componentes.AlertaEliminarMovimiento
import com.app.finansmart.componentes.BarraNavegacion
import com.app.finansmart.componentes.SettingsItem
import com.app.finansmart.componentes.SettingsSection
import com.app.finansmart.viewmodels.LoginViewModel

@Composable
fun VistaSettings(
    movimientosViewModel: MovimientosViewModel,
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val version = "1.0"
    var mostrarAlerta by remember {
        mutableStateOf(false)
    }
    var mostrarAlertaRestablecer by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1D1D)) // Fondo gris claro
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Configuraciones",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SettingsSection(title = "Aplicacion") {
            SettingsItem("Contactanos") {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://atom.bio/danibree135")
                }
                context.startActivity(intent)
            }
            SettingsItem("Créditos") {
                navController.navigate("creditos")
            }
            /* SettingsItem("Tutorial") {
                //Poner una vista que tenga imagenes con un  tutrial guiado de la app
            }*/

            SettingsItem("Version de la App") {
                mostrarAlerta = true
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        SettingsSection(title = "Cuenta") {
            SettingsItem("Cerrar Sesion") {
                movimientosViewModel.cerrarSesion()
                navController.navigate("login")
            }


        }


    }
    if (mostrarAlerta) {
        Alerta(
            titulo = "Version de la App",
            mensaje = "Version: $version",
            textoConfirmacion = "Aceptar",
            alConfirmar = { mostrarAlerta = false }) {

        }

    }


    BarraNavegacion(navController = navController)
}


