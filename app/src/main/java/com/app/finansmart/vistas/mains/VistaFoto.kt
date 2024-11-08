package com.app.finansmart.vistas.mains

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.app.finansmart.viewmodels.MovimientosViewModel

@Composable
fun VistaFoto(movimientosViewModel: MovimientosViewModel,idDocumento:String){
    LaunchedEffect(Unit) {
        movimientosViewModel.TraerNotaPorId(idDocumento)

    }
    Column() {
        var estado = movimientosViewModel.estado
        if (estado.rutaImagen.isNotEmpty()){
            Image(painter = rememberAsyncImagePainter(model = estado.rutaImagen), contentDescription = "", modifier = Modifier.fillMaxSize())
        }

    }
}