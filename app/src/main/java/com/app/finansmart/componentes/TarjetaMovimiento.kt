package com.app.finansmart.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.finansmart.R
import com.app.finansmart.viewmodels.LoginViewModel
import com.app.finansmart.viewmodels.MovimientosViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.text.DecimalFormat

@Composable
fun TarjetaMovimiento(
    titulo: String,
    saldo: Float,
    valor: String,
    idDocumento:String ,
    rutaImagen: String,
    idDocumentoUsuario:String,
    fecha: String,
    tipoMovimiento: String,
    movimientosViewModel: MovimientosViewModel,
    loginViewModel: LoginViewModel,
    onclickFoto: () -> Unit
) {
    LaunchedEffect(Unit) {
        movimientosViewModel.traerNotas()
    }
    var mostrarAlerta by remember {
        mutableStateOf(false)
    }
    var mostrarAlerta2 by remember {
        mutableStateOf(false)
    }
    val datos by loginViewModel.datos.collectAsState()

    val initialText = "Texto inicial"

    val borrar = SwipeAction(
        icon = rememberVectorPainter(image = Icons.Default.Delete),
        background = Color.Red,
        onSwipe = {
            mostrarAlerta2=true

        }
    )
    SwipeableActionsBox(
        endActions = listOf(borrar),//indica de donde hay que arrastrar
        swipeThreshold = 100.dp//indica que tanto hay que deslizar
    ) {

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
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = fecha,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                            .height(80.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 10.dp,
                                    topEnd = 10.dp,
                                    bottomEnd = 10.dp,
                                    bottomStart = 10.dp
                                )
                            )
                            .background(Color(0xFFFDFDFD))
                            .padding(top = 10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically, // Alinear verticalmente al centro
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(top = 8.5.dp)
                            ) {
                                Text(
                                    text = titulo,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black, fontSize = 25.sp,
                                    modifier = Modifier
                                        .padding(top = 0.dp)
                                        .offset(x = 15.dp, y = -15.dp)
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
                                    modifier = Modifier
                                        .padding(top = 0.dp)
                                        .offset(x = 15.dp, y = -15.dp)
                                )
                                /*
                                Text(
                                    text = fecha,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(top = 4.dp)
                                )*/

                            }

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
                                    .size(70.dp)
                                    //.offset(x = -40.dp, y = -8.dp)
                                    .padding(end = 20.dp)
                            )


                        }
                    }
                }
            }

            if (mostrarAlerta) {
                DialogoEditable(
                    showDialog = mostrarAlerta,
                    onDismissRequest = { mostrarAlerta = false },
                    movimientosViewModel = movimientosViewModel,
                    idDocumento = idDocumento,
                    titulo = titulo,
                    valor = "Valor: $valor"
                )
            }

            if (mostrarAlerta2 == true) {
                AlertaEliminarMovimiento(
                    titulo = "Eliminar Movimiento",
                    mensaje = "Estas Seguro?",
                    textoConfirmacion = "Aceptar",
                    textoRechazo = "Rechazar",
                    alConfirmar = {
                        val valorFloat =
                            valor.replace(",", ".").toFloatOrNull() ?: 0f

                        movimientosViewModel.EliminarData(idDocumento, rutaImagen ) {
                            loginViewModel.actualizarSaldoalElimar(
                                idUsuario = idDocumentoUsuario,
                                valor = valorFloat,
                                tipo = tipoMovimiento,
                                saldoActual = saldo
                            ) {
                            }
                        }
                        mostrarAlerta2=false
                    }) {
                    mostrarAlerta2=false

                }
            }
        }
    }


}