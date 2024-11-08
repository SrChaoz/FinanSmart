package com.app.finansmart.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.finansmart.viewmodels.MovimientosViewModel

@Composable
fun DialogoEditable(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    movimientosViewModel: MovimientosViewModel,
    idDocumento: String,
    titulo: String,
    valor: String
) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var estado = movimientosViewModel.estado

    LaunchedEffect(Unit) {
        movimientosViewModel.TraerNotaPorId(idDocumento)
        estado = movimientosViewModel.estado

    }
    println("la ruta imagen es ${estado.rutaImagen}")
    if (showDialog) {


        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(titulo) },
            text = {

                        if (estado.rutaImagen.isNotEmpty()) {
                            Column {
                            Text(text = valor)
                            Image(
                                painter = rememberAsyncImagePainter(model = estado.rutaImagen),
                                contentDescription = "",
                                modifier = Modifier
                                    .wrapContentSize()
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .graphicsLayer(
                                        scaleX = scale,
                                        scaleY = scale,
                                        translationX = offsetX,
                                        translationY = offsetY
                                    )
                                    .pointerInput(Unit) {
                                        detectTransformGestures { _, pan, zoom, _ ->
                                            scale *= zoom
                                            offsetX += pan.x
                                            offsetY += pan.y
                                        }
                                    }
                            )
                        }
                        }


            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismissRequest()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8BD15B))
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}