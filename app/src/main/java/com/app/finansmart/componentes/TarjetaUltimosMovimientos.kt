package com.app.finansmart.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.finansmart.R
import java.text.DecimalFormat


//mostrar una tarjeta de las notas en la vista inicio
@Composable
fun TarjetaUltimosMovimientos(titulo: String, valor: String, fecha: String, tipoMovimiento: String, onclick: () -> Unit) {

    var mostrarAlerta by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(5.dp)
            .clickable { mostrarAlerta = true }
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
               // modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp
                            )
                        )
                        .background(Color(0xFFFDFDFD))
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)

                        ) {
                            Text(
                                text = titulo,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,

                            )
                            val formatter = DecimalFormat("#,##0.0")
                            val formattedValue = try {
                                // Intenta convertir el valor a un número
                                val number = valor.toDoubleOrNull() ?: 0.0
                                formatter.format(number)
                            } catch (e: NumberFormatException) {
                                "Error" // Manejo del error en caso de que la conversión falle
                            }
                            Text(
                                text = "$formattedValue $",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Text(
                                text = fecha,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                //modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        // Seleccionar el icono según el tipo de movimiento
                        val icono = if (tipoMovimiento == "movimiento") {
                            R.drawable.ingresovector
                        } else {
                            R.drawable.gastovector
                        }
                        Icon(
                            painter = painterResource(id = icono),
                            contentDescription = "",
                            tint = if (tipoMovimiento == "movimiento") Color.Green else Color.Red,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(start = 8.dp)
                        )
                    }
                }
            }
        }
        if (mostrarAlerta == true) {
            Alerta(
                titulo = titulo,
                mensaje = " Valor: $$valor",
                textoConfirmacion = "Aceptar",
                alConfirmar = { mostrarAlerta = false }) {

            }

        }
    }

}