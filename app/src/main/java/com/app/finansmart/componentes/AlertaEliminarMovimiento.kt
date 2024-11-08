package com.app.finansmart.componentes

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AlertaEliminarMovimiento(
    titulo: String,
    mensaje: String,
    textoConfirmacion: String,
    textoRechazo: String,
    alConfirmar: () -> Unit,
    alRechazar: () -> Unit
) {
    AlertDialog(onDismissRequest = {
        alRechazar()

    }, dismissButton = {
        Button(onClick = { alRechazar() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text(text = textoRechazo)
        }
    }, confirmButton = {
        Button(onClick = { alConfirmar() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8BD15B))
            ) {
            Text(text = textoConfirmacion)
        }
    }, title = { Text(text = titulo) },
        text = { Text(text = mensaje) },
        containerColor = Color(0xFFF5F5DC)
        )
}