package com.app.finansmart.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.finansmart.Modelo.Movimiento
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class MovimientosViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore
    private val _datos =
        MutableStateFlow<List<Movimiento>>(emptyList())//para traer los datos de firebase  y se gaurda en una lista(fun traer notas)

    //StateFlow para ctualizar automatico lo que se trae
    public val datos: StateFlow<List<Movimiento>> = _datos
    var estado by mutableStateOf(Movimiento())

    private val storageRef = FirebaseStorage.getInstance().reference


    fun cerrarSesion() {
        auth.signOut()
    }

    fun formatearFecha(): String {
        val fechaActual: Date = Calendar.getInstance().time
        val resultado = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return resultado.format(fechaActual)
    }

    fun fechaActual(): Date {
        val fechaActual: Date = Calendar.getInstance().time
        return fechaActual
    }

    suspend fun CargarImagen(imagen: Uri): String {
        return try {
            var imageRef = storageRef.child("Imagenes/${UUID.randomUUID()}")
            var tarea = imageRef.putFile(imagen).await()
            var descarga = tarea.metadata?.reference?.downloadUrl?.await()
            descarga.toString()

        } catch (e: Exception) {
            "Error: $e"
        }

    }

    fun GuardarMovimiento(
        titulo: String,
        valor: String,
        tipoMovimiento: String,
        imagen: Uri,
        alguardar: () -> Unit
    ) {

        val email = auth.currentUser?.email
        viewModelScope.launch {
            val rutaImagen = CargarImagen(imagen)
            try {
                val movimiento = Movimiento(
                    titulo = titulo,
                    valor = valor,
                    fecha = formatearFecha(),
                    fechaActual = fechaActual(),
                    email = email.toString(),
                    tipoMovimiento = tipoMovimiento,
                    rutaImagen = rutaImagen
                )
                firestore.collection("Movimiento")
                    .add(movimiento)
                    .addOnSuccessListener {
                        alguardar()
                    }
                    .addOnFailureListener { e ->
                        println("Error al guardar movimiento: $e")
                    }
            } catch (e: Exception) {
                println("Error $e")
            }
        }
    }

    fun traerNotas() {
        val email = auth.currentUser?.email
        firestore.collection("Movimiento")
            //EL ORDER BY PROVOCA QUE NO SE ACTUALIZE AUTOMATICAMENTE DESPUES DE BORRAR
            //.orderBy("fechaActual", Query.Direction.DESCENDING) // Ordenar por fechaActual ascendente
            .whereEqualTo("email", email.toString())
            .addSnapshotListener { movimientos, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val documentos = mutableListOf<Movimiento>()
                if (movimientos != null) {
                    for (movimiento in movimientos) {
                        val documento = movimiento.toObject(Movimiento::class.java)
                            .copy(idDocumento = movimiento.id)
                        documentos.add(documento)
                    }
                }
                _datos.value = documentos
            }
    }

    fun TraerNotaPorId(idDocumento: String) {
        firestore.collection("Movimiento")
            .document(idDocumento)
            .addSnapshotListener { documento, error ->
                if (documento != null) {
                    val movimiento = documento.toObject(Movimiento::class.java)
                    estado = estado.copy(
                        titulo = movimiento?.titulo ?: "",
                        valor = movimiento?.valor ?: "",
                        rutaImagen = movimiento?.rutaImagen ?: ""
                    )


                }

            }
    }


    fun OnValue(valor: String, campo: String) {
        when (campo) {
            "titulo" -> estado = estado.copy(titulo = valor)
            "valor" -> estado = estado.copy(valor = valor)
        }

    }

    /*
    fun EditarNota(idDocumento: String, titulo: String, valor: String, tipoMovimiento: String, loginViewModel: LoginViewModel, alEditar: () -> Unit) {
        viewModelScope.launch {
            try {
                val nota = hashMapOf(
                    "titulo" to titulo,
                    "valor" to valor,
                    // Otros campos que necesites actualizar
                )

                firestore.collection("Movimiento")
                    .document(idDocumento)
                    .update(nota as Map<String, Any>)
                    .addOnSuccessListener {
                        // Llamar a la función de actualizar saldo después de editar el movimiento
                        val valorFloat = valor.toFloatOrNull() ?: 0f
                        val idUsuario = loginViewModel.estado.idUsuario ?: ""
                        if (idUsuario.isNotEmpty()) {
                            loginViewModel.ActualizarSaldo(idUsuario, valorFloat, tipoMovimiento) {
                                alEditar()
                            }
                        } else {
                            println("Error: idUsuario está vacío o es nulo")
                        }
                    }
                    .addOnFailureListener { e ->
                        println("Error al editar movimiento: $e")
                    }
            } catch (e: Exception) {
                println("Error $e")
            }
        }
    }*/
    fun EditarNota(idDocumento: String, alEditar: () -> Unit) {
        viewModelScope.launch {
            try {
                val nota = hashMapOf(
                    "titulo" to estado.titulo,
                    "valor" to estado.valor,

                    )
                firestore.collection("Movimiento")
                    .document(idDocumento)
                    .update(nota as Map<String, Any>)
                    .addOnSuccessListener {
                        alEditar()
                    }

            } catch (e: Exception) {
                println("Errooor")
            }
        }

    }

    fun EliminarImagen(imagenUrl: String?) {
        if (imagenUrl.isNullOrEmpty()) {
            println("No hay imagen para eliminar.")
            return
        }

        val lastPathSegment = imagenUrl.toUri().lastPathSegment
        if (lastPathSegment.isNullOrEmpty()) {
            println("Error: el segmento de la ruta de la imagen no puede ser nulo o vacío")
            return
        }

        val imageRef = storageRef.child(lastPathSegment)
        try {
            imageRef.delete().addOnSuccessListener {
                println("Imagen eliminada exitosamente")
            }.addOnFailureListener { e ->
                println("Error al eliminar la imagen: $e")
            }
        } catch (e: Exception) {
            println("Error al intentar eliminar la imagen: $e")
        }
    }

    /* fun EliminarImagen(imagenUrl: String) {
         val imageRef = storageRef.child(imagenUrl.toUri().lastPathSegment ?: "")
         try {
             imageRef.delete()
         } catch (e: Exception) {
             println("Erros el Eliminar $e")
         }

     }*/

    fun EliminarData(idDocumento: String, imagenUrl: String?, alEliminar: () -> Unit) {
        viewModelScope.launch {
            try {
                if (!imagenUrl.isNullOrEmpty()) {
                    EliminarImagen(imagenUrl)
                }
                firestore.collection("Movimiento")
                    .document(idDocumento)
                    .delete()
                    .addOnSuccessListener {
                        alEliminar()
                        println("Documento eliminado exitosamente")
                    }
                    .addOnFailureListener { e ->
                        println("Error al eliminar el documento: $e")
                    }
            } catch (e: Exception) {
                println("Error: $e")
            }
        }
    }

    /*fun EliminarData(idDocumento: String, imagenUrl: String, alEliminar: () -> Unit) {
        viewModelScope.launch {
            EliminarImagen(imagenUrl)
            try {
                firestore.collection("Movimiento")
                    .document(idDocumento)
                    .delete()
                    .addOnSuccessListener { alEliminar() }


            } catch (e: Exception) {
                "Error: $e"
            }
        }

    }*/



}