package com.app.finansmart.Modelo

import java.util.Calendar
import java.util.Date


data class Movimiento(
    val titulo: String="",
    val valor: String = "",
    val fecha: String="",
    val fechaActual:Date = Calendar.getInstance().time,
    val email: String="",
    val tipoMovimiento:String="",
    val idDocumento: String="",
    val rutaImagen:String = "",

) {

}