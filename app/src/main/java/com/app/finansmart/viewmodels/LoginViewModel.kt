package com.app.finansmart.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.finansmart.Modelo.Usuario
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore
    var estado by mutableStateOf(Usuario())
        private set
    var mostrarAlerta by mutableStateOf(false)
    var nombreUsuario by mutableStateOf("")  // Variable para almacenar el nombre de usuario
    private val _datos =
        MutableStateFlow<List<Usuario>>(emptyList())//para traer los datos de firebase  y se gaurda en una lista(fun traer notas)
    var saldoTexto by mutableStateOf("0.0")
        private set

    //StateFlow para ctualizar automatico lo que se trae
    public val datos: StateFlow<List<Usuario>> = _datos

    fun login(email: String, password: String, alAcceder: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { tarea ->
                    if (tarea.isSuccessful) {
                        alAcceder()
                    } else {
                        println("Error, usuario o contraseña incorrecta")
                        mostrarAlerta = true
                    }
                }
            } catch (e: Exception) {
                println("Error $e")
            }
        }
    }

    fun cerrarAlerta() {
        mostrarAlerta = false
    }

    fun CrearUsuario(email: String, usuario: String, password: String, alAcceder: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { tarea ->
                        if (tarea.isSuccessful) {
                            GuardarUsuario(usuario)
                            alAcceder()
                        } else {
                            println("Error, al crear Usuario")
                            mostrarAlerta = true
                        }
                    }
            } catch (e: Exception) {
                println("Error $e")
            }
        }
    }

    fun GuardarUsuario(username: String) {
        var id = auth.currentUser?.uid
        var email = auth.currentUser?.email
        nombreUsuario = username // Actualiza la variable cuando se guarda el usuario
        viewModelScope.launch {
            var usuario = Usuario(
                idUsuario = id.toString(),
                email = email.toString(),
                username = username,
            )
            FirebaseFirestore.getInstance().collection("Usuario")
                .add(usuario)
                .addOnSuccessListener {
                    println("Guardo Usuario")
                }
                .addOnFailureListener {
                    println("Error al Guadar")
                }
        }
    }

    fun TraerUsuario() {
        val email = auth.currentUser?.email
        firestore.collection("Usuario")
            .whereEqualTo("email", email.toString())
            .addSnapshotListener { usuario, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val documentos = mutableListOf<Usuario>()
                if (usuario != null) {
                    for (nota in usuario) {
                        val documento =
                            nota.toObject(Usuario::class.java).copy(idDocumento = nota.id)
                        documentos.add(documento)
                    }
                }
                println("estos son mis documentos: $documentos")
                _datos.value = documentos
            }
    }

    fun EditarUsername(idUsuario: String, nuevoUsername: String, alEditar: () -> Unit) {
        viewModelScope.launch {
            try {
                val datosUsuario = hashMapOf(
                    "username" to nuevoUsername
                )

                firestore.collection("Usuario")
                    .document(idUsuario)
                    .update(datosUsuario as Map<String, Any>)
                    .addOnSuccessListener {
                        alEditar()
                    }
                    .addOnFailureListener { e ->
                        println("Error al actualizar el username: ${e.message}")
                    }

            } catch (e: Exception) {
                println("Error al editar el username: ${e.message}")
            }
        }
    }



    fun EditarSaldo(idUsuario: String, alEditar: () -> Unit) {
        viewModelScope.launch {
            try {
                val saldo = hashMapOf(
                    "saldo" to estado.saldo,

                    )
                firestore.collection("Usuario")
                    .document(idUsuario)
                    .update(saldo as Map<String, Any>)
                    .addOnSuccessListener {
                        alEditar()
                    }

            } catch (e: Exception) {
                println("Errooor")
            }
        }

    }

    //funcion que actualiza el slado al eliminar una nota
    fun actualizarSaldoalElimar(
        idUsuario: String,
        valor: Float,
        tipo: String,
        saldoActual: Float,
        alEditar: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val nuevoSaldo = if (tipo == "gasto") {
                    val nuevoSaldoTemporal = saldoActual + valor
                    if (nuevoSaldoTemporal < 0) 0f else nuevoSaldoTemporal
                } else {
                    saldoActual - valor
                }
                val saldoMap = hashMapOf(
                    "saldo" to nuevoSaldo
                )
                firestore.collection("Usuario")
                    .document(idUsuario)
                    .update(saldoMap as Map<String, Any>)
                    .addOnSuccessListener {
                        estado = estado.copy(saldo = nuevoSaldo)
                        alEditar()
                    }
                    .addOnFailureListener { e ->
                        println("Error al actualizar saldo: ${e.message}")
                    }

            } catch (e: Exception) {
                println("Error en actualizarSaldo2: ${e.message}")
            }
        }
    }

    fun TraerSaldoPorId(idUsuario: String) {
        firestore.collection("Usuario")
            .document(idUsuario)
            .addSnapshotListener { documento, error ->
                if (documento != null && documento.exists()) {
                    val usuario = documento.toObject(Usuario::class.java)
                    estado = estado.copy(
                        saldo = usuario?.saldo ?: 0.0f
                    )
                } else if (error != null) {
                    println("Error al traer el saldo: ${error.message}")
                }
            }
    }

    fun ActualizarSaldo(
        idUsuario: String,
        valor: Float,
        tipo: String,
        saldo: Float,
        onComplete: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val nuevoSaldo = if (tipo == "movimiento") {
                    saldo + valor
                } else {
                    if (saldo - valor < 0) {
                        onComplete(false) // Operación fallida, saldo insuficiente
                        return@launch
                    }
                    saldo - valor
                }

                val saldoMap = hashMapOf(
                    "saldo" to nuevoSaldo
                )

                firestore.collection("Usuario")
                    .document(idUsuario)
                    .update(saldoMap as Map<String, Any>)
                    .addOnSuccessListener {
                        estado = estado.copy(saldo = nuevoSaldo)
                        onComplete(true) // Operación exitosa
                    }
                    .addOnFailureListener { e ->
                        println("Error al actualizar saldo: ${e.message}")
                        onComplete(false) // Operación fallida
                    }

            } catch (e: Exception) {
                println("Error en ActualizarSaldo: ${e.message}")
                onComplete(false) // Operación fallida
            }
        }
    }



    fun OnValue(valor: Float, campo: String) {
        when (campo) {
            "saldo" -> estado = estado.copy(saldo = valor)
        }

    }




}