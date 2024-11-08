package com.app.finansmart.componentes

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun Alerta(
    titulo: String,
    mensaje: String,
    textoConfirmacion: String,
    alConfirmar: () -> Unit,
    alRechazar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { alRechazar() },
        confirmButton = {
            Button(
                onClick = { alConfirmar() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = textoConfirmacion)
            }
        },
        title = { Text(text = titulo) },
        text = { Text(text = mensaje) },
        containerColor = Color(0xFFF5F5DC)
    )
}
